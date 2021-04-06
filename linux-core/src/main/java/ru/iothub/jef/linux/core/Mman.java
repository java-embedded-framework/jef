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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;

// sudo adduser pi kmem
// https://unix.stackexchange.com/questions/475800/non-root-read-access-to-dev-mem-by-kmem-group-members-fails
// sudo setcap cap_sys_rawio=ep /usr/lib/jvm/graalvm-ce-java11-20.2.0/bin/java
public abstract class Mman implements NativeSupport {
    public enum MemoryProtection {
        PROT_READ(0x1), /* Page can be read. */
        PROT_WRITE(0x2), /* Page can be written. */
        PROT_READ_WRITE(0x1 | 0x2),
        PROT_EXEC(0x4), /* Page can be executed. */
        PROT_NONE(0x0); /* Page can not be accessed. */

        int value;

        MemoryProtection(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum MemoryFlag {
        MAP_SHARED(0x1), /* Share changes. */
        MAP_PRIVATE(0x2); /* Changes are private. */

        int value;

        MemoryFlag(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Mman instance = null;

    public abstract ByteBuffer mmap(
            FileHandle handle,
            MemoryProtection protection,
            MemoryFlag flags,
            long offset,
            int size) throws IOException;

    public abstract ByteBuffer mmap(
            FileHandle handle,
            EnumSet<MemoryProtection> protection,
            MemoryFlag flags,
            long offset,
            int size) throws IOException;

    protected int memoryProtectionFlag(EnumSet<MemoryProtection> protection) {
        int result = 0;
        for(MemoryProtection p : protection) {
            result = result | p.getValue();
        }
        return result;
    }

    /*
    extern void *mmap (void *__addr, size_t __len, int __prot,
                   int __flags, int __fd, __off_t __offset) __THROW;

    extern void *mmap64 (void *__addr, size_t __len, int __prot,
                     int __flags, int __fd, __off64_t __offset) __THROW;

    extern int munmap (void *__addr, size_t __len) __THROW;

extern int mprotect (void *__addr, size_t __len, int __prot) __THROW;

extern int msync (void *__addr, size_t __len, int __flags);

extern int madvise (void *__addr, size_t __len, int __advice) __THROW;

extern int posix_madvise (void *__addr, size_t __len, int __advice) __THROW;

extern int mlock (const void *__addr, size_t __len) __THROW;

extern int munlock (const void *__addr, size_t __len) __THROW;

extern int mlockall (int __flags) __THROW;

extern int munlockall (void) __THROW;

extern int mincore (void *__start, size_t __len, unsigned char *__vec)

extern void *mremap (void *__addr, size_t __old_len, size_t __new_len,
                     int __flags, ...) __THROW;

extern int remap_file_pages (void *__start, size_t __size, int __prot,
                             size_t __pgoff, int __flags) __THROW;

extern int shm_open (const char *__name, int __oflag, mode_t __mode);


extern int shm_unlink (const char *__name);

    */

    public static Mman getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Mman.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Mman.class);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }
}
