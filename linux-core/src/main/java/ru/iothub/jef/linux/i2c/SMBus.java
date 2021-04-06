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

package ru.iothub.jef.linux.i2c;

import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.SmBusConstants;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.types.SmbusData;
import ru.iothub.jef.linux.core.types.SmbusIoctlData;
import ru.iothub.jef.linux.spi.SpiBus;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The System Management Bus (abbreviated to SMBus or SMB) is a single-ended simple two-wire bus for
 * the purpose of lightweight communication.
 */
@SuppressWarnings("unused")
public class SMBus implements SmBusConstants {
    private static final Logger log = Logger.getLogger(SMBus.class.getName());

    private final I2CInterface iface;
    private final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

    /**
     * Allocates new instance of {@link SMBus} based on interface ID
     * @param iface I2C interface. Please see {@link I2CInterface}
     */
    SMBus(I2CInterface iface) {
        this.iface = iface;
    }

    /**
     * Specify packet error checking
     * @param usePEC {@code true} if packet checking enabled or {@code false} otherwise
     * @throws IOException if I2C bus reject command
     */
    public void usePEC(boolean usePEC) throws IOException {
        log.log(
                Level.INFO,
                () -> String.format(
                        "set usePEC to '%b' for bus '%s' and address '%d'",
                        usePEC, iface.getPath(), iface.getAddress()
                )
        );

        synchronized (synchLock()) {
            synchSelect();
            getInterface().getI2CBus().ioctl(fd(), I2C_PEC, usePEC ? 1L : 0L);
        }
    }

    /**
     * This sends or reads a single bit to the device.
     * @param isWrite {@code true} for write operation or {@code false} otherwise
     * @throws IOException if I2C bus reject command
     */
    public void writeQuick(boolean isWrite) throws IOException {
        log.log(
                Level.FINEST,
                () -> String.format(
                        "set writeQuick to '%b' for bus '%s' and address '%d'",
                        isWrite, iface.getPath(), iface.getAddress()
                )
        );

        synchronized (synchLock()) {
            synchSelect();
            buffer.position(0);
            i2cSmbusAccess(isWrite ? I2C_SMBUS_WRITE : I2C_SMBUS_READ, 0, I2C_SMBUS_QUICK, buffer);
        }
    }

    /**
     * This reads a single byte from a device, without specifying a device register. Some devices are so simple that this interface is enough; for others,
     * it is a shorthand if you want to read the same register as in the previous SMBus command.
     * @return {@code byte} from the current selected device
     * @throws IOException if I2C bus reject command
     */
    @SuppressWarnings("UnusedReturnValue")
    public int readByte() throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            buffer.position(0);
            log.log(Level.FINEST, () -> String.format("read byte from bus '%s' and address '%d'", iface.getPath(), iface.getAddress()));
            i2cSmbusAccess(I2C_SMBUS_READ, 0, I2C_SMBUS_BYTE, buffer);
            byte result = buffer.get(0);
            log.log(Level.FINEST, () -> String.format("read byte from bus '%s' and address '%d' returns '%d'", iface.getPath(), iface.getAddress(), result));
            return result;
        }
    }

    /**
     * This reads a single byte from a device, from a designated register.
     * @param command register command
     * @return {@code byte} from the current selected device
     * @throws IOException if I2C bus reject command
     */
    public int readByteData(int command) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            buffer.position(0);
            log.log(Level.FINEST, () -> String.format("read byte command '%d' from bus '%s' and address '%d'", command, iface.getPath(), iface.getAddress()));
            i2cSmbusAccess(I2C_SMBUS_READ, command, I2C_SMBUS_BYTE_DATA, buffer);
            byte result = buffer.get(0);
            log.log(Level.FINEST, () -> String.format("read byte command '%d' from bus '%s' returns '%d' and address '%d'", command, iface.getPath(), iface.getAddress(), result));
            return result;
        }
    }

    /**
     * This operation is very like Read Byte; again, data is read from a device, from a designated
     * register that is specified through the Comm byte.
     * But this time, the data is a complete word (16 bits)
     * @param command register command
     * @return {@code short} from the current selected device
     * @throws IOException if I2C bus reject command
     */
    public int readWordData(int command) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            buffer.position(0);
            log.log(Level.FINEST, () -> String.format("read short command '%d' from bus '%s' and address '%d'", command, iface.getPath(), iface.getAddress()));
            i2cSmbusAccess(I2C_SMBUS_READ, command, I2C_SMBUS_WORD_DATA, buffer);
            byte hi = buffer.get(1);
            byte lo = buffer.get(0);
            short result = (short) (((hi & 0xFF) << 8) | (lo & 0xFF));
            log.log(Level.FINEST, () -> String.format("read short command '%d' from bus '%s' returns '%d' and address '%d'", command, iface.getPath(), iface.getAddress(), result));
            return result;
        }
    }

    /**
     * This command reads a block of bytes from a device,
     * from a designated register that is specified through the Comm byte.
     * @param command register command
     * @return array with readed data
     * @throws IOException if I2C bus reject command
     */
    public ByteBuffer readBlockData(int command) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            buffer.position(0);
            i2cSmbusAccess(I2C_SMBUS_READ, command, I2C_SMBUS_BLOCK_DATA, buffer);
            int length = getUnsignedByte(buffer);
            buffer.position(1);
            byte[] res = new byte[length];
            buffer.get(res, 1, length);
            return ByteBuffer.wrap(res);
        }
    }

    /**
     * This operation is the reverse of Receive Byte:
     * it sends a single byte to a device. See Receive Byte for more information
     * @param b {@code byte} single byte
     * @throws IOException if I2C bus reject command
     */
    public void writeByte(int b) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            log.log(Level.FINEST, () -> String.format("write byte to bus '%s' and address '%d'", iface.getPath(), iface.getAddress()));
            i2cSmbusAccess(I2C_SMBUS_WRITE, b, I2C_SMBUS_BYTE, null);
        }
    }

    /**
     * This writes a single byte to a device, to a designated register.
     * The register is specified through the Comm byte.
     * This is the opposite of the Read Byte operation.
     * @param command register command
     * @param b {@code byte} single byte
     * @throws IOException if I2C bus reject command
     */
    public void writeByteData(int command, int b) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            setUnsignedByte(buffer, b);
            log.log(Level.FINEST, () -> String.format("write byte command '%d' value '%d' from bus '%s' and address '%d'", command, b, iface.getPath(), iface.getAddress()));
            this.i2cSmbusAccess(I2C_SMBUS_WRITE, command, I2C_SMBUS_BYTE_DATA, buffer.position(0));
        }
    }

    /**
     * This is the opposite of the Read Word operation. 16 bits of data are written to a device,
     * to the designated register that is specified through the Comm byte.
     * @param command register command
     * @param word {@code short} single byte
     * @throws IOException if I2C bus reject command
     */
    public void writeWordData(int command, int word) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            setUnsignedShort(buffer, word);
            log.log(Level.FINEST, () -> String.format("write short command '%d' value '%d' from bus '%s' and address '%d'", command, word, iface.getPath(), iface.getAddress()));
            this.i2cSmbusAccess(I2C_SMBUS_WRITE, command, I2C_SMBUS_WORD_DATA, buffer.position(0));
        }
    }

    /**
     * The opposite of the Block Read command, this writes up to 32 bytes to a device,
     * to a designated register that is specified through the Comm byte.
     * The amount of data is specified in the Count byte.
     * @param command register command
     * @param buf input buffer
     * @throws IOException if I2C bus reject command
     */
    public void writeBlockData(int command, ByteBuffer buf) throws IOException {
        synchronized (synchLock()) {
            synchSelect();
            int capacity = buf.capacity();
            buffer.put(0, (byte) capacity);
            buffer.put(buf.array(), 1, capacity);
            this.i2cSmbusAccess(I2C_SMBUS_WRITE, command, I2C_SMBUS_I2C_BLOCK_DATA, buffer);
        }
    }

    /**
     * Execute command in real I2C device
     * @param readWrite read or write operation
     * @param command ioctl command
     * @param size size of data
     * @param data array with data
     * @throws IOException if I2C bus reject command
     */
    protected void i2cSmbusAccess(byte readWrite,
                                  long command,
                                  int size,
                                  ByteBuffer data) throws IOException {
        SmbusData smbusData = new SmbusData();
        smbusData.setBlock(data);
        SmbusIoctlData ioctlData = new SmbusIoctlData(readWrite, command, size, smbusData);
        Ioctl.getInstance().ioctl(fd(), command, ioctlData);
    }

    /**
     * Gets SMBus {@link I2CInterface}
     * @return i2c interface
     */
    protected I2CInterface getInterface() {
        return iface;
    }

    /**
     * Gets SMBus {@link FileHandle}
     * @return file handle
     */
    protected FileHandle fd() {
        return iface.getFD();
    }

    private static int getUnsignedByte(ByteBuffer p) {
        return p.get(0) & 0x000000FF;
    }

    private static int getUnsignedShort(ByteBuffer p) {
        return p.getShort(0) & 0x0000FFFF;
    }

    private static void setUnsignedByte(ByteBuffer p, int b) {
        p.put(0, (byte) b);
    }

    private static void setUnsignedShort(ByteBuffer p, int word) {
        p.putShort(0, (short) word);
    }

    private Object synchLock() {
        return iface.synchLock();
    }

    private void synchSelect() throws NativeIOException {
        iface.synchSelect();
    }
}
