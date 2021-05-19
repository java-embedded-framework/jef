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

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import ru.iothub.jef.linux.core.LinuxUtils;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Sys;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.types.*;
import ru.iothub.jef.linux.gpio.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.linux.core.LinuxUtils.checkIOResult;
import static ru.iothub.jef.linux.core.SmBusConstants.I2C_SMBUS;
import static ru.iothub.jef.linux.core.SmBusConstants.I2C_SMBUS_WRITE;
import static ru.iothub.jef.linux.core.util.StringUtils.dump;

public class IoctlJna extends Ioctl {
    private static final Logger log = Logger.getLogger(IoctlJna.class.getName());

    private final static long SPI_IOC_MAGIC = 'k';
    private final static long _IOC_NRBITS = 8;
    private final static long _IOC_TYPEBITS = 8;
    private final static long _IOC_SIZEBITS = 14;
    private final static long _IOC_DIRBITS = 2;
    private final static long _IOC_NRSHIFT = 0;
    private final static long _IOC_NONE = 0;
    private final static long _IOC_READ = 2;
    private final static long _IOC_WRITE = 1;
    private final static int GPIOHANDLES_MAX = 64;

    private static int _SPI_IOC_TRANSFER_SIZE = -1;

    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public int ioctl(FileHandle fd, long command, long arg) throws NativeIOException {
        log.log(
                Level.FINEST,
                String.format(
                        "ioctl by long fd is '%d' command: '%8h' arg: '0x%8h'",
                        fd.getHandle(), command, arg
                )
        );
        int result = Delegate.ioctl(fd.getHandle(), new NativeLong(command, true), arg);
        //checkIOResult("ioctl:long", result);
        return result;
    }

    @Override
    public int ioctl(FileHandle fd, long command, LongReference arg) throws NativeIOException {
        log.log(
                Level.FINEST,
                String.format(
                        "ioctl by long reference fd is '%d' command is '%8h' arg is '0x%8h'",
                        fd.getHandle(), command, arg.getValue()
                )
        );

        LongByReference ref = new LongByReference();
        int ioctl = Delegate.ioctl(fd.getHandle(), new NativeLong(command, true), ref.getPointer());
        long refValue = ref.getValue();
        arg.setValue(refValue);

        log.log(
                Level.FINEST,
                String.format(
                        "ioctl by long reference fd is '%d' command is '%8h' arg is '0x%8h' results '%d'",
                        fd.getHandle(), command, arg.getValue(), refValue
                )
        );

        return ioctl;
    }

    @Override
    public int ioctl(FileHandle fd, long command, IntReference arg) throws NativeIOException {
        log.log(
                Level.FINEST,
                String.format(
                        "ioctl by int reference fd is '%d' command is '%8h' arg is '0x%8h'",
                        fd.getHandle(), command, arg.getValue()
                )
        );

        IntByReference ref = new IntByReference();
        int ioctl = Delegate.ioctl(fd.getHandle(), new NativeLong(command, true), ref.getPointer());
        log.log(Level.FINEST, () -> String.format("ioctl result is '%s'", ioctl));
        checkIOResult("ioctl:IntReference", ioctl);
        int refValue = ref.getValue();

        arg.setValue(refValue);

        log.log(
                Level.FINEST,
                String.format(
                        "ioctl by int reference fd is '%d' command is '%8h' arg is '0x%8h' results '%d'",
                        fd.getHandle(), command, arg.getValue(), refValue
                )
        );

        return ioctl;
    }

    @Override
    public int ioctl(FileHandle fd, long command, SmbusIoctlData ptr) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("ioctl.smbus fd is '%d' data is '%s'", fd.getHandle(), ptr));

        IoctlData data = new IoctlData();
        int offsetRw = data.offset("rw");
        int offsetCommand = data.offset("command");
        int offsetSize = data.offset("size");
        int offsetData = data.offset("data");
        int size = data.size();

        Memory ioctlData = new Memory(size);
        Memory i2cSmbusData = new Memory(I2CSmbusData.SIZE);

        ioctlData.clear();
        i2cSmbusData.clear();

        ByteBuffer block = ptr.getData().getBlock();

        log.log(Level.FINEST, "ioctl.smbus input block: ");
        log.log(Level.FINEST, ()-> dump(block));

        if (ptr.getReadWrite() == I2C_SMBUS_WRITE) {
            i2cSmbusData.write(0, block.array(), 0, block.limit());
        }

        ioctlData.setByte(offsetRw, ptr.getReadWrite());
        ioctlData.setByte(offsetCommand, (byte) command);
        ioctlData.setInt(offsetSize, ptr.getSize());
        ioctlData.setPointer(offsetData, i2cSmbusData);
        int result = Delegate.ioctl(fd.getHandle(), new NativeLong(I2C_SMBUS, true), ioctlData);

        checkIOResult("ioctl:sm_bus", result);

        byte[] byteArray = i2cSmbusData.getByteArray(0, I2CSmbusData.SIZE);

        block.put(byteArray, 0, byteArray.length);

        log.log(Level.FINEST, "ioctl.smbus output block: ");
        log.log(Level.FINEST, ()-> dump(block));

        return result;
    }

    @Override
    public int ioctl(FileHandle fd, SpiIocTransfer ptr) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("ioctl.spi fd is '%d' data is '%s'", fd.getHandle(), ptr));

        ByteBuffer txBuffer = ptr.getTxBuffer();
        ByteBuffer rxBuffer = ptr.getRxBuffer();

        int txSize = txBuffer.capacity();
        int rxSize = rxBuffer.capacity();
        int txRxSize = txSize + rxSize;

        Memory txRxMemory = new Memory(txRxSize);
        log.log(Level.FINEST, () -> String.format("dump input array \n%s", dump(txBuffer)));

        txRxMemory.write(0, LinuxUtils.toBytes(txBuffer), 0, txBuffer.capacity());

        log.log(Level.FINEST, () -> String.format("pinned array \n%s", dump(txRxMemory.getByteBuffer(0, txRxSize))));

        SpiIOCTransfer spi = new SpiIOCTransfer(txRxMemory, ptr);

        long ioc_message = SPI_IOC_MESSAGE(1);
        log.log(Level.FINEST, () -> String.format("ioc_message is '%s'", ioc_message));

        int result = Delegate.ioctl(fd.getHandle(), new NativeLong(ioc_message, true), spi);
        log.log(Level.FINEST, () -> String.format("ioctl result is '%s'", result));

        checkIOResult("ioctl:SPI", result);

        rxBuffer.put(
                txRxMemory.getByteArray(txSize, rxSize)
        ).position(0);

        log.log(Level.FINEST, () -> String.format("dump output array \n%s", dump(rxBuffer)));

        return result;
    }

    @Override
    public int ioctl(FileHandle handle, long command, GpioChipInfo info) throws NativeIOException {
        gpiochip_info struct = new gpiochip_info();
        int result = Delegate.ioctl(handle.getHandle(), new NativeLong(command, true), struct);
        info.setName(struct.getName());
        info.setLabel(struct.getLabel());
        info.setLines(struct.getLines());
        checkIOResult("ioctl:gpio_chip_info", result);
        return result;
    }

    @Override
    public int ioctl(FileHandle handle, long command, GpioLineInfo line) throws NativeIOException {
        gpioline_info struct = new gpioline_info(line.getOffset());
        int result = Delegate.ioctl(handle.getHandle(), new NativeLong(command, true), struct);

        checkIOResult("ioctl:gpio_line_info", result);

        line.setFlags(struct.getFlags());
        line.setName(struct.getName().trim());
        line.setConsumer(struct.getConsumer().trim());

        return result;
    }

    @Override
    public int ioctl(FileHandle handle, long command, GpioHandleRequest request) throws NativeIOException {
        gpiohandle_request struct = new gpiohandle_request(request);

        int result = Delegate.ioctl(handle.getHandle(), new NativeLong(command, true), struct);
        checkIOResult("ioctl:gpio_handle_request", result);
        request.setFd(struct.fd);

        return result;
    }

    @Override
    public int ioctl(int fd, long command, GpioHandleData handleData) throws NativeIOException {
        Objects.requireNonNull(handleData);

        gpiohandle_data data = new gpiohandle_data();

        byte[] from = handleData.getValues();
        byte[] to = data.values;
        int size = Math.min(from.length, to.length);
        System.arraycopy(from, 0, to, 0, size);

        int result = Delegate.ioctl(fd, new NativeLong(command), data);
        checkIOResult("ioctl:byte_array_ref", result);
        System.arraycopy(to, 0, from, 0, size);
        return result;
    }

    @Structure.FieldOrder("values")
    public static class gpiohandle_data extends Structure {
        public byte[] values = new byte[GPIOHANDLES_MAX];
    }

    @Structure.FieldOrder({"lineoffsets", "flags", "default_values", "consumer_label", "lines", "fd"/*, "buf"*/})
    public static class gpiohandle_request extends Structure {
        public int[] lineoffsets = new int[GPIOHANDLES_MAX];
        public int flags;
        public byte[] default_values = new byte[GPIOHANDLES_MAX];
        public byte[] consumer_label = new byte[32];
        public int lines;
        public int fd;
        //public byte[] buf = new byte[0xFF];

        public gpiohandle_request(GpioHandleRequest request) {
            int[] ints = request.getLineOffsets();
            if(ints != null) {
                int length = Math.min(ints.length, lineoffsets.length);
                System.arraycopy(ints, 0, lineoffsets, 0, length);
            }
            this.flags = request.getFlags();
            this.lines = request.getLines();

            byte[] label = request.getConsumerLabel();
            if(label != null) {
                int length = Math.min(label.length, consumer_label.length);
                System.arraycopy(label, 0, consumer_label,0, length);
            }

            byte[] defs = request.getDefaultValues();
            if(defs != null) {
                int length = Math.min(defs.length, default_values.length);
                System.arraycopy(defs, 0, default_values, 0, length);
            }
        }
    }

    @Structure.FieldOrder({"line_offset", "flags", "name", "consumer"})
    public static class gpioline_info extends Structure {
            public int line_offset;
            public int flags;
            public byte[] name = new byte[32];
            public byte[] consumer = new byte[32];

        public gpioline_info(int offset) {
            this.line_offset = offset;
        }

        public int getFlags() {
            return flags;
        }

        public String getName() {
            return new String(name);
        }

        public String getConsumer() {
            return new String(consumer);
        }
    }

    @Structure.FieldOrder({"name", "label", "lines"})
    public static class gpiochip_info extends Structure {
            public byte[] name = new byte[32];
            public byte[] label = new byte[32];
            public int lines;

            public String getName() {
                return new String(name);
            }

            public String getLabel() {
                return new String(label);
            }

        public int getLines() {
            return lines;
        }
    }

    @Structure.FieldOrder({"txBuff", "rxBuff", "len", "speedHz", "delayMicros", "bitsPerWord", "csChange", "txNBits", "rxNBits", "pad"})
    public static class SpiIOCTransfer extends Structure {
        public NativeLong txBuff;
        public NativeLong rxBuff;
        public int len;
        public int speedHz;
        public short delayMicros;
        public byte bitsPerWord;
        public byte csChange;
        public byte txNBits;
        public byte rxNBits;
        public short pad;

        SpiIOCTransfer() {
        }

        public SpiIOCTransfer(Memory txRxMemory, SpiIocTransfer ptr) {
            this(
                    txRxMemory,
                    ptr.getLength(),
                    ptr.getSpeed(),
                    ptr.getDelay(),
                    ptr.getBitsPerWord()
            );
        }

        public SpiIOCTransfer(Memory txRxMemory, int length, int speed, short delay, byte bitsPerWord) {
            NativeLong txRxPointer = new NativeLong(Pointer.nativeValue(txRxMemory));
            this.txBuff = txRxPointer;
            this.rxBuff = txRxPointer;
            this.len = length;
            this.speedHz = speed;
            this.delayMicros = delay;
            this.bitsPerWord = bitsPerWord;
        }
    }

    public static class I2CSmbusData extends Union {
        public final static int I2C_SMBUS_BLOCK_MAX = 32;
        public final static int SIZE = I2C_SMBUS_BLOCK_MAX + 2;

        public byte byte$;
        public short word;

        /*
        block[0] is used for length and one more for user-space compatibility
         */
        public byte[] block = new byte[SIZE];

    }

    @Structure.FieldOrder({"rw", "command", "size", "data"})
    public static class IoctlData extends Structure {
        public byte rw;
        public byte command;
        public int size;
        public Pointer data;

        public int offset(String name) {
            return fieldOffset(name);
        }
    }

    @Override
    protected long GET_SPI_IOC_MAGIC() {
        return SPI_IOC_MAGIC;
    }

    @Override
    protected long IOC_NRBITS() {
        return _IOC_NRBITS;
    }

    @Override
    protected long IOC_TYPEBITS() {
        return _IOC_TYPEBITS;
    }

    @Override
    protected long IOC_SIZEBITS() {
        return _IOC_SIZEBITS;
    }

    @Override
    protected long IOC_DIRBITS() {
        return _IOC_DIRBITS;
    }

    @Override
    protected long IOC_NRSHIFT() {
        return _IOC_NRSHIFT;
    }

    @Override
    protected long IOC_NONE() {
        return _IOC_NONE;
    }

    @Override
    protected long IOC_READ() {
        return _IOC_READ;
    }

    @Override
    protected long IOC_WRITE() {
        return _IOC_WRITE;
    }

    @Override
    protected int SPI_MSGSIZE(int N) {
        return ((((N) * (SPI_IOC_TRANSFER_SIZE())) < (1 << IOC_SIZEBITS()))
                ? ((N) * (SPI_IOC_TRANSFER_SIZE())) : 0);
    }

    private static int SPI_IOC_TRANSFER_SIZE() {
        if(_SPI_IOC_TRANSFER_SIZE == -1) {
            _SPI_IOC_TRANSFER_SIZE = new SpiIOCTransfer().size();
        }
        return _SPI_IOC_TRANSFER_SIZE;
    }

    static class Delegate {
        public static native int ioctl(int fd, NativeLong request, Pointer arg);

        public static native int ioctl(int fd, NativeLong request, Structure arg);

        public static native int ioctl(int fd, NativeLong request, long arg);

        static {
            Native.register("c");
        }
    }
}
