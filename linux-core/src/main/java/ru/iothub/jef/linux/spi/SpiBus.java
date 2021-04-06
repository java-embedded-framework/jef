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

package ru.iothub.jef.linux.spi;

import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.types.IntReference;
import ru.iothub.jef.linux.core.types.SpiIocTransfer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.linux.core.util.StringUtils.dump;

/**
 * The {@link SpiBus} class provides methods for transmitting and receiving data to/from an SPI slave device.
 * On an SPI bus, data is transferred between the SPI master device and an SPI slave device in full duplex. That is, data is transmitted by the SPI master to the SPI slave device at the same time data is received from the SPI slave device by the SPI master.
 *
 */
@SuppressWarnings("unused")
public class SpiBus {
    private static final Logger log = Logger.getLogger(SpiBus.class.getName());

    private final String bus;
    private final FileHandle fd;
    private final int clockFrequency;
    private final SpiMode clockMode;
    private final int wordLength;
    private final int bitOrdering;


    /**
     * Allocates new instance of {@link SpiBus} based on interface ID
     * @param busNumber number of available bus (0, 1, 2, ...)
     * @param clockFrequency working frequency
     * @param clockMode clock mode. see {@link SpiMode}
     * @param wordLength length of word. Typically it's 8-bits.
     * @param bitOrdering bit ordering. 0 - {@link java.nio.ByteOrder#BIG_ENDIAN} or 1 - {@link java.nio.ByteOrder#LITTLE_ENDIAN}
     * @throws NativeIOException if got error from Linux subsystem during initialization
     */
    public SpiBus(int busNumber, int clockFrequency, SpiMode clockMode, int wordLength, int bitOrdering) throws NativeIOException {
        this("/dev/spidev0." + busNumber, clockFrequency, clockMode, wordLength, bitOrdering);
    }

    /**
     * Allocates new instance of {@link SpiBus} based on interface name
     * @param bus path to bus in Linux file system i.e '/dev/spidev0.x'
     * @param clockFrequency working frequency
     * @param clockMode clock mode. see {@link SpiMode}
     * @param wordLength length of word. Typically it's 8-bits.
     * @param bitOrdering bit ordering. 0 - {@link java.nio.ByteOrder#BIG_ENDIAN} or 1 - {@link java.nio.ByteOrder#LITTLE_ENDIAN}
     * @throws NativeIOException if got error from Linux subsystem during initialization
     */
    public SpiBus(String bus, int clockFrequency, SpiMode clockMode, int wordLength, int bitOrdering) throws NativeIOException {
        log.log(Level.INFO,
                () -> String.format(
                        "Create SPI bus with Bus: '%s' Clock Frequency: '%d' Spi Mode: '%s' Word Length: '%d' Bit Ordering: '%d'",
                        bus, clockFrequency, clockMode, wordLength, bitOrdering)
        );

        this.bus = bus;
        this.clockFrequency = clockFrequency;
        this.clockMode = clockMode;
        this.wordLength = wordLength;
        this.bitOrdering = bitOrdering;

        fd = initSPIHandler(bus);
    }

    /**
     * Path to bus in Linux
     * @return path to bus in Linux file system
     */
    public String getBus() {
        return bus;
    }

    /**
     * {@link FileHandle of bus in Linux file system}
     * @return handler of bus in Linux FS
     */
    public FileHandle getFd() {
        return fd;
    }

    /**
     * Bus clock frequency
     * @return clock frequency
     */
    public int getClockFrequency() {
        return clockFrequency;
    }

    /**
     * Bus clock mode {@link SpiMode}
     * @return clock mode
     */
    public SpiMode getClockMode() {
        return clockMode;
    }

    /**
     * SPI Bus word length
     * @return word length
     */
    public int getWordLength() {
        return wordLength;
    }

    /**
     * SPI bus bit ordering. 0 - {@link java.nio.ByteOrder#BIG_ENDIAN} or 1 - {@link java.nio.ByteOrder#LITTLE_ENDIAN}
     * @return bus bit ordering
     */
    public int getBitOrdering() {
        return bitOrdering;
    }

    private FileHandle initSPIHandler(String bus) throws NativeIOException {
        final FileHandle fd;

        log.log(Level.INFO, () -> String.format("Open SPI Bus '%s'", bus));
        fd = Fcntl.getInstance().open(bus, EnumSet.of(IOFlags.O_RDWR));
        Ioctl console = Ioctl.getInstance();

        IntReference variable = new IntReference(-1);

        log.log(Level.INFO, () -> "Read current clock value");
        console.ioctl(fd, console.getSpiIocRdMode(), variable);
        log.log(Level.INFO, () -> "current clock is " + variable.getValue());

        if(variable.getValue() != clockMode.value) {
            log.log(Level.INFO, () -> "Setup clock variable");
            console.ioctl(fd, console.getSpiIocWrMode(), new IntReference(clockMode.value));
        } else {
            log.log(Level.INFO, () -> "Current clock is the same");
        }

        log.log(Level.INFO, () -> "Read current bits per word");
        console.ioctl(fd, console.getSpiIocRdBitsPerWord(), variable);
        log.log(Level.INFO, () -> "current bits per word is " + variable.getValue());

        if(variable.getValue() != wordLength) {
            log.log(Level.INFO, () -> "Setup bits per word");
            console.ioctl(fd, console.getSpiIocWrBitsPerWord(), new IntReference(wordLength));
        } else {
            log.log(Level.INFO, () -> "Current bits per word is the same");
        }

        log.log(Level.INFO, () -> "Read current max speed");
        console.ioctl(fd, console.getSpiIocRdMaxSpeedHz(), variable);
        log.log(Level.INFO, () -> "current max speed is " + variable.getValue());

        if(variable.getValue() != clockFrequency) {
            log.log(Level.INFO, () -> "Setup max speed");
            console.ioctl(fd, console.getSpiIocWrMaxSpeedHz(), new IntReference(clockFrequency));
        } else {
            log.log(Level.INFO, () -> "Current max speed is the same");
        }


        log.log(Level.INFO, () -> String.format("SPI bus '%s' setup finished", bus));
        return fd;
    }

    /**
     * Read data from SPI bus based on requested parameters
     * @param inputParams request with input parameters. See {@link SpiInputParams}
     * @return buffer with response from SPI bus
     * @throws NativeIOException if SPI bus discard request
     */
    public int readByteData(SpiInputParams inputParams) throws IOException {
        log.log(Level.FINEST, () -> "read byte data");
        return readWriteData(inputParams.getFinal(), 1).get();
    }

    /**
     * Write data to SPI bus
     * @param inputParams request with input parameters. See {@link SpiInputParams}
     * @throws NativeIOException  if SPI bus discard request
     */
    public void writeByteData(SpiInputParams inputParams) throws NativeIOException {
        log.log(Level.FINEST, () -> "write byte data");
        readWriteData(inputParams.getFinal(), 0);
    }

    /**
     * Read array from SPI bus
     * @param inputParams request with input parameters. See {@link SpiInputParams}
     * @param outputSize expected size of response
     * @return buffer with response from SPI bus
     * @throws NativeIOException if SPI bus discard request
     */
    public ByteBuffer readArray(SpiInputParams inputParams, int outputSize) throws NativeIOException {
        log.log(Level.FINEST, () -> "read array");
        return readWriteData(
                inputParams.getFinal(),
                outputSize
        );
    }

    private ByteBuffer readWriteData(ByteBuffer input, int outputSize) throws NativeIOException {
        input.position(0);

        ByteBuffer output = ByteBuffer
                .allocateDirect(outputSize);

        log.log(Level.FINEST, () -> dump(input));

        SpiIocTransfer data = new SpiIocTransfer(
                input,
                output,
                input.capacity() + outputSize,
                clockFrequency,
                (short) 0,
                (byte) wordLength
        );

        Ioctl.getInstance().ioctl(fd, data);

        log.log(Level.FINEST, () -> dump(output));

        return output.asReadOnlyBuffer();
    }
}
