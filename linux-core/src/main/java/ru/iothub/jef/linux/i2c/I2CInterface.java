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
import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.io.FileHandle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.linux.core.LinuxUtils.toBytes;
import static ru.iothub.jef.linux.core.util.StringUtils.dump;

/**
 * I2CInterface represent auto-selecting functionality for the I2C device and provides
 * ability to read/write operation over I2C bus or {@link SMBus} interface
 */
public class I2CInterface {
    private static final Logger log = Logger.getLogger(I2CInterface.class.getName());

    private final I2CBus bus;
    private final SMBus smBus;
    private final FileHandle fd;
    private final int address;

    /**
     * Allocate new instance of I2C Interface
     * @param bus parent {@link I2CBus}
     * @param fd {@link FileHandle} to used {@link I2CBus}
     * @param address address of device
     */
    I2CInterface(I2CBus bus, FileHandle fd, int address) {
        log.log(
                Level.FINEST,
                () -> String.format(
                        "create I2C Interface for bus '%s' file id '%d' and address '%d'",
                        bus.getPath(), fd.getHandle(), address
                )
        );

        this.bus = bus;
        this.fd = fd;
        this.address = address;
        this.smBus = new SMBus(this);
    }

    /**
     * Returns {@link SMBus} interface for current I2C device
     * @return SMBus interface
     */
    public SMBus getSmBus() {
        return smBus;
    }

    /**
     * Reads data from I2C device to buffer capacity
     * @param buf allocated buffer
     * @throws NativeIOException if I2C bus reject command
     */
    public void read(ByteBuffer buf) throws NativeIOException {
        read(buf, buf.capacity());
    }

    /**
     * Reads data from I2C device to buffer capacity
     * @param buf allocated buffer
     * @param length number of bytes to read
     * @throws NativeIOException if I2C bus reject command
     */
    public void read(ByteBuffer buf, int length) throws NativeIOException {
        log.log(
                Level.FINEST,
                () -> String.format(
                        "reading '%d' bytes from bus '%s'",
                        length, bus.getPath()
                )
        );
        synchronized (synchLock()) {
            synchSelect();
            Fcntl.getInstance().read(getFD(), toBytes(buf), length);
        }
    }

    /**
     * Writes data from buffer to selected I2C device
     * @param buf buffer with data
     * @throws NativeIOException if I2C bus reject command
     */
    public void write(ByteBuffer buf) throws NativeIOException {
        write(buf, buf.capacity());
    }

    /**
     * Writes data from buffer to selected I2C device
     * @param buf buffer with data
     * @param length amount of data to write
     * @throws NativeIOException if I2C bus reject command
     */
    public void write(ByteBuffer buf, int length) throws NativeIOException {
        log.log(
                Level.FINEST,
                () -> String.format(
                        "write '%d' bytes to bus '%s'",
                        length, bus.getPath()
                )
        );
        log.log(Level.FINEST, dump(buf));

        synchronized (synchLock()) {
            synchSelect();
            Fcntl.getInstance().write(getFD(), toBytes(buf), length);
        }
    }

    /**
     * Lock decorator for selected device synchronization
     * @return lock object
     */
    Object synchLock() {
        return bus;
    }

    /**
     * Selecting current device in I2C bus if it's changed
     * @throws NativeIOException if I2C bus reject command
     */
    void synchSelect() throws NativeIOException {
        bus.selectSlave(address, false);
    }

    /**
     * Returns {@link FileHandle} to I2C bus
     * @return file handle to current I2C bus
     */
    FileHandle getFD() {
        return fd;
    }

    /**
     * Returns path to I2C device
     * @return path to I2C device
     */
    String getPath() {
        return bus.getPath();
    }

    /**
     * Returns parent {@link I2CBus} for current interface
     * @return parent I2C bus
     */
    public I2CBus getI2CBus() {
        return bus;
    }

    /**
     * Returns I2C address for current interface device
     * @return i2c address of device
     */
    public int getAddress() {
        return address;
    }
}
