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

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ru.iothub.jef.linux.core.Errno;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Mman;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;

public class MmanJna extends Mman {
    public MmanJna() {
    }

    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public ByteBuffer mmap(FileHandle handle, MemoryProtection protection, MemoryFlag flags, long offset, int size) throws IOException {
        return mmap(handle, EnumSet.of(protection), flags, offset, size);
    }

    @Override
    public ByteBuffer mmap(FileHandle handle, EnumSet<MemoryProtection> protection, MemoryFlag flags, long offset, int size) throws IOException {
        Pointer address = new Pointer(0);

        Pointer result = Delegate.mmap(
                address,
                new NativeLong(size),
                memoryProtectionFlag(protection),
                flags.getValue(),
                handle.getHandle(),
                new NativeLong(offset)
        );

        if (Pointer.nativeValue(result) == -1) {
            Errno err = Errno.getInstance();
            System.out.println(err.strerror());
            throw new IOException("mmap failed: " + err.strerror());
        }

        return result.getByteBuffer(0, size);
    }

    static class Delegate {

        public static native Pointer mmap(Pointer addr,
                                          NativeLong len,
                                          int prot,
                                          int flags,
                                          int fd,
                                          NativeLong off);
        static {
            Native.register("c");
        }
    }


}
