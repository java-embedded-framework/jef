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

import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.types.LongReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides ability for read/write operations in I2C Bus.
 */
@SuppressWarnings("unused")
public class I2CBus {
    private static final Logger log = Logger.getLogger(I2CBus.class.getName());

    private static final int EBUSY = 16;

    private final static long I2C_RETRIES = 0x0701L;

    private final static long I2C_TIMEOUT = 0x0702L;

    private final static long I2C_SLAVE = 0x0703L;

    private final static long I2C_SLAVE_FORCE = 0x0706L;

    private final static long I2C_TENBIT = 0x0704L;

    private final static long I2C_FUNCS = 0x0705L;

    private final static long I2C_RDWR = 0x0707L;

    private final static int MAX_7BIT_DEVICES = 127;
    private final static int MAX_10BIT_DEVICES = 1023;

    public final static byte I2C_MODE_READ = 2;
    public final static byte I2C_MODE_QUICK = 1;

    /**
     * Represents current I2C device status. Used for {@link #enumerate()}
     * method to identify all available devices in bus
     */
    public enum Status {
        /**
         * Device in address available
         */
        AVAILABLE,
        /**
         * Device in address is busy
         */
        NOT_AVAILABLE,
        /**
         * Device is busy at this moment
         */
        BUSY,
        /**
         * Skipped address because I2C bus not supporting read/write operations for this address
         */
        SKIP,
        /**
         * Device status is unknown
         */
        UNKNOWN
    }

    private final FileHandle fd;
    private final String path;
    private final long func;
    private boolean tenBits;
    private int selectedAddress;
    private int timeout;
    private int retries;

    /**
     * Allocates instance of I2C bus
     * @param bus bus number
     * @throws NativeIOException if I2C bus not available
     */
    public I2CBus(int bus) throws NativeIOException {
        this("/dev/i2c-" + bus);
    }

    /**
     * Allocates instance of I2C bus
     * @param bus path to I2C bus in Linux file system
     * @throws NativeIOException if I2C bus not available
     */
    public I2CBus(String bus) throws NativeIOException {
        this.path = bus;
        log.log(Level.INFO, () -> String.format("Opening I2C bus '%s'", bus));
        fd = Fcntl.getInstance().open(bus, EnumSet.of(IOFlags.O_RDWR));
        log.log(Level.INFO, () -> String.format("Opening I2C bus '%s' success", bus));
        func = loadFunctionality(fd);
        log.log(Level.INFO, () -> String.format("I2C bus '%s' support functionalities '%d'", bus, func));
    }

    /**
     * Allocate instance of {@link I2CInterface} for selected address
     * @param address address of device
     * @return i2c interface for the device
     */
    protected I2CInterface getInterface(int address) {
        return new I2CInterface(this, fd, address);
    }

    /**
     * System method for ioctl manipulations
     * @param fd file handle
     * @param command ioctl command
     * @param arg i2c argument
     * @return ioctl result
     * @throws NativeIOException if ioctl returns error
     */
    protected int ioctl(FileHandle fd, long command, long arg) throws NativeIOException {
        return Ioctl.getInstance().ioctl(fd, command, arg);
    }

    /**
     * System method for ioctl manipulations
     * @param fd file handle
     * @param command ioctl command
     * @param arg i2c argument
     * @return ioctl result
     * @throws NativeIOException if ioctl returns error
     */
    protected int ioctl(FileHandle fd, long command, LongReference arg) throws NativeIOException {
        return Ioctl.getInstance().ioctl(fd, command, arg);
    }

    /**
     * Force interface selection for i2c device. This method similar to {@link #select(int)}
     * and only provides additional functions
     * @param address i2c device address
     * @param force force device selection
     * @param isTenBit is ten bits bus or not
     * @return {@link I2CInterface} for selected address
     * @throws NativeIOException if ioctl returns error
     */
    public I2CInterface select(int address, boolean force, boolean isTenBit) throws NativeIOException {
        log.log(
                Level.FINEST,
                () -> String.format(
                        "selecting I2C interface with address '%d' force '%b' tenbits '%b' for bus '%s'",
                        address, force, isTenBit, path
                )
        );

        setTenBits(isTenBit);
        return getInterface(address);
    }

    /**
     * Checking I2C bus supported functionality
     * @param functionality requested {@link I2CFunctionality} value
     * @return {@code true} if support or {@code false} otherwise
     */
    public boolean support(I2CFunctionality functionality) {
        return (func & functionality.getValue()) != 0;
    }

    /**
     * Gets amount of retries to i2c bus. Returns value only if retries was updated by user or {@code -1} otherwise
     * @return amount of retries
     */
    public int getRetries() {
        return retries;
    }

    /**
     * Set amount of retries to i2c bus
     * @param retries amount of retries
     * @throws NativeIOException if ioctl returns error
     */
    public void setRetries(int retries) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("set reties '%d' for bus '%s'", retries, path));
        ioctl(this.fd, I2C_RETRIES, retries & 0xffffffffL);
        this.retries = retries;
    }

    /**
     * Gets i2c bus timeout. Returns value only if timeout was updated by user or {@code -1} otherwise
     * @return timeout in milliseconds
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets i2c bus timeout
     * @param timeoutMs timeout in milliseconds
     * @throws NativeIOException if ioctl returns error
     */
    public void setTimeout(int timeoutMs) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("set timeout '%d' for bus '%s'", retries, path));
        ioctl(this.fd, I2C_TIMEOUT, (timeoutMs / 10) & 0xffffffL);
        timeout = timeoutMs;
    }

    /**
     /**
     * Selects slave device in I2C bus without force selection
     * @param address slave device address
     * @return instance of {@link I2CInterface}
     * @throws NativeIOException if ioctl returns error
     */
    public I2CInterface select(int address) throws NativeIOException {
        return select(address, false, false);
    }

    /**
     * Selects slave device in I2C bus
     * @param address slave device address
     * @param force force device selection
     * @throws NativeIOException if ioctl returns error
     */
    public void selectSlave(int address, boolean force) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("select slave address '%d' force '%b' for bus '%s'", address, force, path));
        if (selectedAddress != address) {
            ioctl(fd, force ? I2C_SLAVE_FORCE : I2C_SLAVE, address & 0xffffffffL);
            log.log(Level.FINEST, () -> String.format("slave address selected '%d' force '%b' for bus '%s'", address, force, path));
            selectedAddress = address;
        } else {
            log.log(Level.FINEST, () -> String.format("skip selecting slave address. Current address '%d' already selected for bus '%s'", address, path));
        }
    }

    /**
     * Return status of all I2C addresses in I2C bus.
     * @return list of statuses for addresses in bus
     */
    public List<Status> enumerate() {
        List<Status> result = new ArrayList<>(MAX_7BIT_DEVICES);
        SMBus smBus = getInterface(0).getSmBus();

        int cmd;
        for (int i = 0; i <= MAX_7BIT_DEVICES; i++) {

            if ((i >= 0x30 && i <= 0x37) || (i >= 0x50 && i <= 0x5f)) {
                cmd = I2C_MODE_READ;
            } else {
                cmd = I2C_MODE_QUICK;
            }

            if ((cmd == I2C_MODE_READ && !(support(I2CFunctionality.I2C_FUNC_SMBUS_READ_BYTE)))
                    || (cmd == I2C_MODE_QUICK && !(support(I2CFunctionality.I2C_FUNC_SMBUS_QUICK)))) {
                result.add(Status.SKIP);
                continue;
            }

            try {
                ioctl(fd, I2C_SLAVE, i);
            } catch (NativeIOException e) {
                result.add(
                        e.getCode() == EBUSY ? Status.BUSY : Status.UNKNOWN
                );
            }

            try {
                if (cmd == I2C_MODE_READ) {
                    smBus.writeQuick(true);
                } else {
                    smBus.readByte();
                }
                result.add(Status.AVAILABLE);
            } catch (IOException e) {
                result.add(Status.NOT_AVAILABLE);
            }
        }
        return result;
    }

    /**
     * Sets or remove 10 bits address schema to I2C bus
     * @param isTenBit {@code true} if bus 10 bits or {@code false} otherwise
     * @throws NativeIOException if ioctl returns error
     */
    protected void setTenBits(boolean isTenBit) throws NativeIOException {
        log.log(Level.FINEST, ()->String.format("set ten bits '%b' for bus '%s'", isTenBit, path));
        if (isTenBit) {
            if (!tenBits) {
                ioctl(fd, I2C_TENBIT, 1);
            }
        } else {
            if (tenBits) {
                ioctl(fd, I2C_TENBIT, 0);
            }
        }
        tenBits = isTenBit;
    }

    /**
     * Returns file handle to i2c bus
     * @return file handle
     */
    protected FileHandle getFd() {
        return fd;
    }

    /**
     * Returns path to i2c bus in Linux file system
     * @return path to i2c bus
     */
    String getPath() {
        return path;
    }

    private long loadFunctionality(FileHandle fd) throws NativeIOException {
        LongReference ref = new LongReference();
        ioctl(fd, I2C_FUNCS, ref);
        return ref.getValue();
    }
}
