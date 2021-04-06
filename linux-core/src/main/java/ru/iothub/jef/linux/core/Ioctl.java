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

package ru.iothub.jef.linux.core;

import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.types.IntReference;
import ru.iothub.jef.linux.core.types.LongReference;
import ru.iothub.jef.linux.core.types.SmbusIoctlData;
import ru.iothub.jef.linux.core.types.SpiIocTransfer;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Ioctl implements NativeSupport {

    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    public static int TIOCMGET = 0x5415;
    public static int TIOCMSET = 0x5418;
    public static final long FIONREAD = 0x541B;
    private static Ioctl instance;
    private static long SPI_IOC_RD_MODE;
    private static long SPI_IOC_WR_MODE;
    private static long SPI_IOC_RD_BITS_PER_WORD;
    private static long SPI_IOC_WR_BITS_PER_WORD;
    private static long SPI_IOC_RD_MAX_SPEED_HZ;
    private static long SPI_IOC_WR_MAX_SPEED_HZ;
    private static long SPI_IOC_RD_MODE32;
    private static long SPI_IOC_WR_MODE32;

    public static Ioctl getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Fcntl.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Ioctl.class);
                    initVariables();
                    initialized.set(true);
                }
            }
        }
        return instance;
    }

    private static long _IOC_NRMASK() {
        return ((1L << instance.IOC_NRBITS()) - 1);
    }

    private static long _IOC_TYPEMASK() {
        return ((1L << instance.IOC_TYPEBITS()) - 1);
    }

    private static long _IOC_SIZEMASK() {
        return ((1L << instance.IOC_SIZEBITS()) - 1);
    }

    private static long _IOC_DIRMASK() {
        return ((1L << instance.IOC_DIRBITS()) - 1);
    }

    private static long _IOC_TYPESHIFT() {
        return instance.IOC_NRSHIFT() + instance.IOC_NRBITS();
    }

    private static long _IOC_SIZESHIFT() {
        return _IOC_TYPESHIFT() + instance.IOC_TYPEBITS();
    }

    private static long _IOC_DIRSHIFT() {
        return _IOC_SIZESHIFT() + instance.IOC_SIZEBITS();
    }

    private static long _IO(long type, long nr) {
        return _IOC(instance.IOC_NONE(), (type), (nr), 0);
    }

    private static long _IOR(long type, long nr, long size) {
        return _IOC(instance.IOC_READ(), type, nr, size);
    }

    private static long _IOW(long type, long nr, long size) {
        return _IOC(instance.IOC_WRITE(), type, nr, size);
    }

    private static long _IOWR(long type, long nr, long size) {
        return _IOC(instance.IOC_READ() | instance.IOC_WRITE(), type, nr, size);
    }

    private static long _IOC(long dir, long type, long nr, long size) {
        return (((dir) << _IOC_DIRSHIFT()) |
                ((type) << _IOC_TYPESHIFT()) |
                ((nr) << instance.IOC_NRSHIFT()) |
                ((size) << _IOC_SIZESHIFT()));
    }

    private static void initVariables() {
        SPI_IOC_RD_MODE = _IOR(instance.GET_SPI_IOC_MAGIC(), 1, 1);
        SPI_IOC_WR_MODE = _IOW(instance.GET_SPI_IOC_MAGIC(), 1, 1);
        SPI_IOC_RD_BITS_PER_WORD = _IOR(instance.GET_SPI_IOC_MAGIC(), 3, 1);
        SPI_IOC_WR_BITS_PER_WORD = _IOW(instance.GET_SPI_IOC_MAGIC(), 3, 1);
        SPI_IOC_RD_MAX_SPEED_HZ = _IOR(instance.GET_SPI_IOC_MAGIC(), 4, 4);
        SPI_IOC_WR_MAX_SPEED_HZ = _IOW(instance.GET_SPI_IOC_MAGIC(), 4, 4);
        SPI_IOC_RD_MODE32 = _IOR(instance.GET_SPI_IOC_MAGIC(), 5, 4);
        SPI_IOC_WR_MODE32 = _IOW(instance.GET_SPI_IOC_MAGIC(), 5, 4);
    }

    public abstract int ioctl(FileHandle fd, long command, long arg) throws NativeIOException;

    public abstract int ioctl(FileHandle fd, long command, LongReference arg) throws NativeIOException;

    public abstract int ioctl(FileHandle fd, long command, IntReference arg) throws NativeIOException;

    public abstract int ioctl(FileHandle fd, long command, SmbusIoctlData ptr) throws NativeIOException;

    public abstract int ioctl(FileHandle fd, SpiIocTransfer ptr) throws NativeIOException;

    public long getSpiIocRdMode() {
        return SPI_IOC_RD_MODE;
    }

    public long getSpiIocWrMode() {
        return SPI_IOC_WR_MODE;
    }

    public long getSpiIocRdBitsPerWord() {
        return SPI_IOC_RD_BITS_PER_WORD;
    }

    public long getSpiIocWrBitsPerWord() {
        return SPI_IOC_WR_BITS_PER_WORD;
    }

    public long getSpiIocRdMaxSpeedHz() {
        return SPI_IOC_RD_MAX_SPEED_HZ;
    }

    public long getSpiIocWrMaxSpeedHz() {
        return SPI_IOC_WR_MAX_SPEED_HZ;
    }

    public long getSpiIocRdMode32() {
        return SPI_IOC_RD_MODE32;
    }

    public long getSpiIocWrMode32() {
        return SPI_IOC_WR_MODE32;
    }

    protected abstract long GET_SPI_IOC_MAGIC();

    protected abstract long IOC_NRBITS();

    protected abstract long IOC_TYPEBITS();

    protected abstract long IOC_SIZEBITS();

    protected abstract long IOC_DIRBITS();

    protected abstract long IOC_NRSHIFT();

    protected abstract long IOC_NONE();

    protected abstract long IOC_READ();

    protected abstract long IOC_WRITE();

    public long SPI_IOC_MESSAGE(int N) {
        return _IOW(instance.GET_SPI_IOC_MAGIC(), 0, SPI_MSGSIZE(N));
    }

    protected abstract int SPI_MSGSIZE(int N);
}
