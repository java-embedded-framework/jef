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

package ru.iothub.jef.linux.gpio;

import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.io.FileHandle;

import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class Pin {
    private final FileHandle handle;
    private final byte id;
    private final Ioctl ioctl;
    private final String name;
    private final String consumer;
    private final int flags;
    private int fd;
    private boolean locked;
    private Mode mode;

    public Pin(FileHandle handle, int id) throws NativeIOException {
        this.ioctl = Ioctl.getInstance();

        GpioLineInfo line = getGpioLineInfo(handle, id);
        GpioHandleRequest request = getGpioHandleRequest(handle, id,
                GpioHandleRequest.Flags.GPIOHANDLE_REQUEST_OUTPUT.value);

        this.handle = handle;
        this.id = (byte) id;
        this.locked = (request == null);
        this.fd = locked ? -1 : request.getFd();
        this.name = line.getName();
        this.consumer = line.getConsumer();
        this.flags = line.getFlags();
    }

    private static GpioLineInfo getGpioLineInfo(FileHandle handle, int id) throws NativeIOException {
        GpioLineInfo line = new GpioLineInfo();
        line.setOffset(id);
        Ioctl ioctl = Ioctl.getInstance();
        ioctl.ioctl(handle, Ioctl.getGpioGetLineInfoIoctl(), line);
        //System.out.println("line = " + line);
        return line;
    }

    private static GpioHandleRequest getGpioHandleRequest(FileHandle handle, int id, int mode) {
        Ioctl ioctl = Ioctl.getInstance();

        GpioHandleRequest request = new GpioHandleRequest();
        request.setLinesOffset(new int[]{id});

        request.setFlags(mode);
        request.setConsumerLabel("jef-gpio-manager");
        request.setLines(1);
        try {
            ioctl.ioctl(handle, ioctl.getGpioGetLineHandleIoctl(), request);
            return request;
        } catch (NativeIOException e) {
            //System.out.println("result[" + id + "] = " + e.getMessage());
            return null;
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public State getState() throws IOException {
        checkLocked();

        GpioHandleData data = new GpioHandleData(new byte[1]);
        ioctl.ioctl(fd, ioctl.getGpioHandleGetLineValuesIoctl(), data);
        byte value = data.getValues()[0];
        return value == 0 ? State.LOW : State.HIGH;
    }

    public void setState(State state) throws IOException {
        Objects.requireNonNull(state);

        checkLocked();
        byte value = (byte) (state == State.HIGH ? 1 : 0);
        GpioHandleData data = new GpioHandleData(new byte[]{value});
        ioctl.ioctl(fd, ioctl.getGpioHandleSetLineValuesIoctl(), data);
    }

    private void checkLocked() throws IOException {
        if (isLocked()) {
            throw new IOException("Pin " + id + " is locked");
        }
    }

    public byte getPinNumber() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        if (!isLocked()) {
            int m = (mode == Mode.INPUT) ?
                    GpioHandleRequest.Flags.GPIOHANDLE_REQUEST_INPUT.value :
                    GpioHandleRequest.Flags.GPIOHANDLE_REQUEST_OUTPUT.value;
            if ((flags & m) > 0) {
                GpioHandleRequest request = getGpioHandleRequest(handle, id, m);
                this.locked = (request == null);
                this.fd = locked ? -1 : request.getFd();
                this.mode = mode;
            }
        }
    }

    public enum State {
        LOW(0),
        HIGH(1),
        LOCKED(2);

        int value;

        State(int value) {
            this.value = value;
        }

        /*public static State fromInteger(int i) {
            for (State obj : State.values()) {
                if (obj.value == i) {
                    return obj;
                }
            }
            throw new IllegalArgumentException("Illegal value: " + i);
        }*/
    }

    /*public enum Flags {
        GPIOLINE_FLAG_KERNEL(1),
        GPIOLINE_FLAG_IS_OUT(1 << 1),
        GPIOLINE_FLAG_ACTIVE_LOW(1 << 2),
        GPIOLINE_FLAG_OPEN_DRAIN(1 << 3),
        GPIOLINE_FLAG_OPEN_SOURCE(1 << 4);
        int value;

        Flags(int value) {
            this.value = value;
        }

        public static EnumSet<Flags> valueOf(int mask) {
            EnumSet<Flags> flags = EnumSet.noneOf(Flags.class);

            for (Flags flag : flags) {
                if ((mask & flag.value) != 0) {
                    flags.add(flag);
                }
            }

            return flags;
        }
    }*/

    public enum Mode {
        INPUT, OUTPUT
    }
}
