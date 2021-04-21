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

import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.io.FileHandle;

import java.util.*;

import static ru.iothub.jef.linux.core.IOFlags.O_CLOEXEC;
import static ru.iothub.jef.linux.core.IOFlags.O_RDONLY;

@SuppressWarnings("unused")
public class PinSet {
    private final static Map<String, PinSet> pinsets = new HashMap<>();
    private final Fcntl fcntl = Fcntl.getInstance();

    private final FileHandle handle;
    private final int hash;
    private final String name;
    private final String label;
    private List<Pin> pins;

    private PinSet(FileHandle handle, int[] lines) throws NativeIOException {
        this.handle = handle;
        GpioChipInfo info = new GpioChipInfo();
        Ioctl.getInstance().ioctl(handle, Ioctl.getGpioGetChipInfoIoctl(), info);
        this.name = info.getName();
        this.label = info.getLabel();
        this.hash = Arrays.hashCode(lines);
        loadPins(lines);
    }

    private PinSet(String path, int[] pinout) throws NativeIOException {
        this(Fcntl.getInstance().open(path, EnumSet.of(O_RDONLY, O_CLOEXEC)), pinout);
    }

    public static synchronized PinSet getInstance(String path, int[] pinout) throws NativeIOException {
        PinSet result = pinsets.get(path);
        if(result != null) {
            if(Arrays.hashCode(pinout) == result.getHash()) {
                return result;
            } else {
                result.free();
            }
        }
        result = new PinSet(path, pinout);
        pinsets.put(path, result);
        return result;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    private void free() throws NativeIOException {
        fcntl.close(handle);
    }

    private int getHash() {
        return hash;
    }


    private void loadPins(int[] lines) throws NativeIOException {
        pins = new ArrayList<>(lines.length);
        for (int id : lines) {
            Pin pin = new Pin(handle, id);
            pins.add(pin);
        }
    }

    public Pin getPin(int index) {
        return pins.get(index);
    }
}
