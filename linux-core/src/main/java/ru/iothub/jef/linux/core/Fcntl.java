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

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Fcntl implements NativeSupport {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Fcntl instance = null;

    public FileHandle open(String pathname, IOFlags flag) throws NativeIOException {
        return open(pathname, EnumSet.of(flag));
    }

    public abstract FileHandle open(String pathname, EnumSet<IOFlags> flags) throws NativeIOException;

    public abstract FileHandle open64(String pathname, EnumSet<IOFlags> flags) throws NativeIOException;

    public abstract void close(FileHandle fd) throws NativeIOException;

    public abstract void close(int fd) throws NativeIOException;

    public abstract int read(FileHandle fd, byte[] buffer, int size) throws NativeIOException;

    public abstract void write(FileHandle fd, byte[] buffer, int size) throws NativeIOException;

    protected static int IOFlagsMask(EnumSet<IOFlags> flags) {
        int result = 0;

        for(IOFlags flag : flags) {
            result = result | flag.value;
        }

        return result;
    }

    public static Fcntl getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Fcntl.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Fcntl.class);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }

}
