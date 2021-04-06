/*
 * Copyright (c) 2021, IOT-Hub.RU and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is dual-licensed: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License Version 3 as
 * published by the Free Software Foundation. For the terms of this
 * license, see <http://www.gnu.org/licenses/>.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License Version 3 for more details (a copy is included in the LICENSE
 * file that accompanied this code).
 *
 * You should have received a copy of the GNU Affero General Public License
 * version 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact support@iot-hub.ru or visit www.iot-hub.ru if you need
 * additional information or have any questions.
 *
 * You can be released from the requirements of the license by purchasing
 * a Java Embedded Framework Commercial License. Buying such a license is
 * mandatory as soon as you develop commercial activities involving the
 * Java Embedded Framework software without disclosing the source code of
 * your own applications.
 *
 * Please contact sales@iot-hub.ru if you have any question.
 */

package ru.iothub.jef.devices.library.winbond.w25x;

import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.linux.spi.SpiInputParams;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.devices.library.winbond.w25x.W25XCommands.*;
import static ru.iothub.jef.devices.library.winbond.w25x.W25XCommandsBuffers.*;

/**
 * Provides access to Winbond WS25x flash chipsets.
 * Datasheets available in Winbond website for example for one of types
 * @see <a href="https://www.winbond.com/resource-files/w25q32jv%20revh%2001072019%20plus.pdf">here</a>
 */
@SuppressWarnings("unused")
public class W25xFlash {
    private static final Logger log = Logger.getLogger(W25xFlash.class.getName());

    private final static int WINBOND_MANAFACTURER_ID = 0xEF;

    private static final int SR1_BUSY_MASK = 0x01;
    private static final int SR1_WEN_MASK = 0x02;

    private final SpiBus bus;
    private final W25xFlashSizeInfo capacityInfo;
    private int manufacturerID;
    private String chipID;
    private int memoryType;
    private int capacity;

    /**
     * Allocates new instance of W25x flash chip
     *
     * @param bus instance of {@link SpiBus}
     * @throws NativeIOException if initialization failed
     */
    public W25xFlash(SpiBus bus) throws NativeIOException {
        log.log(Level.FINER, () -> String.format("Create W25xFlash for bus '%s'", bus.getBus()));
        this.bus = bus;

        readJedecId(bus);

        capacityInfo = W25xFlashSizeInfo.findByKey(capacity);
        if (capacityInfo == null) {
            throw new NativeIOException("Size for " + Integer.toHexString(capacity) + " not defined");
        }
        log.log(Level.FINER, () -> String.format("capacityInfo '%s' ", capacityInfo));

        readChipId(bus);
    }

    private static byte[] addressToBytes(int addressFrom) {
        byte[] params = new byte[3];
        params[0] = (byte) ((addressFrom >> 16) & 0xFF);
        params[1] = (byte) ((addressFrom >> 8) & 0xFF);
        params[2] = (byte) ((addressFrom) & 0xFF);
        return params;
    }

    private static SpiInputParams toAddressedCommand(byte command, int addressFrom) {
        return toAddressedCommand(command, addressFrom, 0);
    }

    private static SpiInputParams toAddressedCommand(byte command, int addressFrom, int additionalBytes) {
        return SpiInputParams.allocate(1 + 3 + additionalBytes)
                .put(command)
                .put((byte) ((addressFrom >> 16) & 0xFF))
                .put((byte) ((addressFrom >> 8) & 0xFF))
                .put((byte) ((addressFrom) & 0xFF));
    }

    /**
     * Returns manafacturer id. For Winbond chipsets it's 0xEF
     *
     * @return manafacturer id
     */
    public int getManufacturerID() {
        return manufacturerID;
    }

    /**
     * Gets chipset memory type
     *
     * @return memory type
     */
    public int getMemoryType() {
        return memoryType;
    }

    /**
     * Gets chipset capacity
     *
     * @return capacity value
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets chipset ID
     *
     * @return chipset ID
     */
    public String getChipID() {
        return chipID;
    }

    /**
     * Checks if chipset is busy (some operations in progress like erase all data).
     *
     * @return {@code true} if chipset is busy or {@code false} otherwise
     * @throws IOException if SPI bus not allow this operation
     */
    public boolean isBusy() throws IOException {
        return (bus.readByteData(B_CMD_READ_STATUS_R1) & SR1_BUSY_MASK) > 0;
    }

    /**
     * Moves chip to power down mode. Please see datasheet for details
     *
     * @throws IOException if SPI bus not allow this operation
     */
    public void powerDown() throws IOException {
        log.log(Level.FINER, () -> String.format("Power Down for %h", chipID));
        bus.writeByteData(B_CMD_POWER_DOWN);
    }

    /**
     * Moves chip to power up mode. Please see datasheet for details
     *
     * @throws IOException if SPI bus not allow this operation
     */
    public void powerUp() throws IOException {
        log.log(Level.FINER, () -> String.format("Power Up for '%h'", chipID));
        bus.writeByteData(B_CMD_RELEASE_PDOWN_ID);
    }

    /**
     * Enable write operations on chip
     *
     * @throws IOException if SPI bus not allow this operation
     */
    public void writeEnable() throws IOException {
        log.log(Level.FINER, () -> String.format("Write Enable for '%h'", chipID));
        bus.writeByteData(B_CMD_WRIRE_ENABLE);
    }

    /**
     * Disable write operations on chip
     *
     * @throws IOException if SPI bus not allow this operation
     */
    public void writeDisable() throws IOException {
        log.log(Level.FINER, () -> String.format("Write Disable for '%h'", chipID));
        bus.writeByteData(B_CMD_WRITE_DISABLE);
    }

    /**
     * Read buffer from chip.
     *
     * @param addressFrom from address
     * @param amount      about of bytes to read
     * @return {@link ByteBuffer} with read data
     * @throws IOException if SPI bus not allow this operation
     */
    public ByteBuffer read(int addressFrom, int amount) throws IOException {
        log.log(Level.FINER,
                () -> String.format("read from address '%d' amount of bytes '%d' for %h",
                        addressFrom, amount, chipID
                )
        );

        checkAddress(addressFrom);
        SpiInputParams in = toAddressedCommand(CMD_READ_DATA.value, addressFrom);
        return bus.readArray(in, amount);
    }

    /**
     * Read buffer from chip.
     * The Fast Read instruction is similar to the Read Data instruction except that it can
     * operate at the highest. Please see datasheet for details.
     * possible frequency
     *
     * @param addressFrom from address
     * @param amount      about of bytes to read
     * @return {@link ByteBuffer} with read data
     * @throws IOException if SPI bus not allow this operation
     */
    public ByteBuffer fastRead(int addressFrom, int amount) throws IOException {
        log.log(Level.FINER,
                () -> String.format("fast read from address '%d' amount of bytes '%d' for %h",
                        addressFrom, amount, chipID
                )
        );

        checkAddress(addressFrom);
        SpiInputParams in = toAddressedCommand(CMD_FAST_READ.value, addressFrom, 1);
        in.put((byte) 0);
        return bus.readArray(in, amount);
    }

    /**
     * Erase sector in chip and wait till operation will be finished
     *
     * @param sectorNumber sector number
     * @throws IOException if SPI bus not allow this operation
     */
    public void eraseSector(int sectorNumber) throws IOException {
        eraseSector(sectorNumber, true);
    }

    /**
     * Erase sector in chip
     *
     * @param sectorNumber sector number
     * @param wait         {@code true} if need to wait till operation will be
     *                     finished or {@code false} otherwise
     * @throws IOException if SPI bus not allow this operation
     */
    public void eraseSector(int sectorNumber, boolean wait) throws IOException {
        log.log(Level.FINER,
                () -> String.format("erase sector '%d' with wait '%b' for %h",
                        sectorNumber, wait, chipID
                )
        );

        checkSectorNumber(sectorNumber);
        writeEnable();
        SpiInputParams in = toAddressedCommand(CMD_SECTOR_ERASE.value, sectorNumber << 12, 0);
        bus.writeByteData(in);
        wait(wait);
    }

    /**
     * Erase block in chip and wait till operation will be finished
     *
     * @param blockNo sector number
     * @throws IOException if SPI bus not allow this operation
     */
    public void erase64Block(int blockNo) throws IOException {
        erase64Block(blockNo, true);
    }

    /**
     * Erase block in chip
     *
     * @param blockNo block number
     * @param wait    {@code true} if need to wait till operation will be
     *                finished or {@code false} otherwise
     * @throws IOException if SPI bus not allow this operation
     */
    public void erase64Block(int blockNo, boolean wait) throws IOException {
        log.log(Level.FINER,
                () -> String.format("erase block '%d' with wait '%b' for %h",
                        blockNo, wait, chipID
                )
        );
        checkBlock(blockNo);
        SpiInputParams in = toAddressedCommand(CMD_BLOCK_ERASE64KB.value, blockNo << 16, 0);
        writeEnable();
        bus.writeByteData(in);
        wait(wait);
    }

    /**
     * Erase ALL data in chip and wait till operation will be finished
     *
     * @throws IOException if SPI bus not allow this operation
     */
    public void eraseAll() throws IOException {
        writeEnable();
        eraseAll(true);
    }

    /**
     * Erase ALL data in chip
     *
     * @param wait {@code true} if need to wait till operation will be
     *             finished or {@code false} otherwise
     * @throws IOException if SPI bus not allow this operation
     */
    public void eraseAll(boolean wait) throws IOException {
        log.log(Level.FINER, () -> String.format("Erase all sectors for '%h'", chipID));
        bus.writeByteData(B_CMD_CHIP_ERASE);
        wait(wait);
    }

    /**
     * Write data to chip and wait till operation will be finished
     *
     * @param sectorNo from sector
     * @param address  from address
     * @param buffer   data
     * @throws IOException if SPI bus not allow this operation
     */
    public void pageWrite(int sectorNo, int address, byte[] buffer) throws IOException {
        pageWrite(sectorNo, address, buffer, true);
    }

    /**
     * Write data to chip
     *
     * @param sectorNo from sector
     * @param address  from address
     * @param buffer   data
     * @param wait     {@code true} if need to wait till operation will be
     *                 finished or {@code false} otherwise
     * @throws IOException if SPI bus not allow this operation
     */
    public void pageWrite(int sectorNo, int address, byte[] buffer, boolean wait) throws IOException {
        log.log(Level.FINER,
                () -> String.format(
                        "page write sectorNo is %d  address is '%d' length is '%d'",
                        sectorNo, address, buffer.length
                )
        );

        checkSectorNumber(sectorNo);
        checkAddress(address);

        if (buffer.length > capacityInfo.getBytesPerPage()) {
            throw new IOException("W25xFlash not support writing more than " + capacityInfo.getBytesPerPage() + " bytes");
        }

        SpiInputParams in = toAddressedCommand(
                CMD_PAGE_PROGRAM.value,
                (sectorNo << 12) + address,
                buffer.length
        );
        wait(wait);
        writeEnable();
        in.put(buffer);
        bus.writeByteData(in);
        wait(wait);
    }

    private void readChipId(SpiBus bus) throws NativeIOException {
        ByteBuffer buf = bus.readArray(B_CMD_READ_UNIQUE_ID, 8);
        chipID = String.format("%2h-%2h-%2h-%2h-%2h-%2h-%2h-%2h", buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF, buf.get() & 0xFF).toUpperCase();
        log.log(Level.FINER, () -> String.format("Winbond flash unique ID: %h", chipID));
    }

    private void readJedecId(SpiBus bus) throws NativeIOException {
        log.log(Level.FINER, () -> "Read JedecID");
        ByteBuffer buf = bus.readArray(B_CMD_JEDEC_ID, 3);
        manufacturerID = buf.get() & 0xFF;
        if (manufacturerID != WINBOND_MANAFACTURER_ID) {
            log.log(Level.WARNING, () -> String.format("Wrong manafacturer ID for Winbond flash %h", manufacturerID));
            throw new NativeIOException("Invalid manafacturer ID: " + manufacturerID + " should be " + WINBOND_MANAFACTURER_ID);
        }
        memoryType = buf.get() & 0xFF;
        capacity = buf.get() & 0xFF;

        log.log(Level.FINER,
                () -> String.format(
                        "Jedec readed. Manafacturer '%h' Memory Type '%h' Capacity: '%d'",
                        manufacturerID, memoryType, capacity
                )
        );
    }

    private void wait(boolean wait) throws IOException {
        while (isBusy() & wait) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {

            }
        }
    }

    private void checkSectorNumber(int sectorNumber) throws IOException {
        int sectors = capacityInfo.getSectors();
        if (sectorNumber >= sectors) {
            throw new IOException("Sector will be in range (0-" + (sectors - 1));
        }
    }

    private void checkAddress(int address) throws IOException {
        // TODO: not implemented
    }

    private void checkBlock(int blockNo) throws IOException {
        // TODO: not implemented
    }
}
