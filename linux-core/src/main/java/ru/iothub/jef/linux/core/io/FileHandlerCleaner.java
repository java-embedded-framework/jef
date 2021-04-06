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

package ru.iothub.jef.linux.core.io;

import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Fcntl;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FileHandlerCleaner {
    private final static ReferenceQueue<FileHandle> QUEUE = new ReferenceQueue<>();
    private final static Map<Integer, ReferenceWithCleanup> PHANTOMS = Collections.synchronizedMap(new HashMap<>());

    private static Thread CLEANUP_THREAD = null;

    public static void main(String[] args) throws Exception {
        System.out.println("Start mook");

        Thread.sleep(3000);
        FileHandle handler = new FileHandle(1);
        register(handler);

        System.out.println("Sleep with GC 1");
        Thread.sleep(3000);
        System.gc();

        System.out.println("Sleep with GC 2");
        Thread.sleep(3000);
        System.gc();

        handler = null;
        System.out.println("Clear references");
        Thread.sleep(3000);

        for (int i = 0; i < 5; i++) {
            System.out.println("Sleep " + i);
            Thread.sleep(3000);
            System.out.println("GC " + i);
            System.gc();
        }
        //System.out.println("cleanable = " + cleanable);

        System.out.println("End");
    }

    private static void checkThreadState() {
        if(CLEANUP_THREAD==null) {
            CLEANUP_THREAD = new Thread(() -> {
                for (; ; ) {
                    try {
                        ReferenceWithCleanup ref = (ReferenceWithCleanup) QUEUE.remove();
                        ref.clean();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            CLEANUP_THREAD.setPriority(Thread.MIN_PRIORITY);
            CLEANUP_THREAD.setDaemon(true);
            CLEANUP_THREAD.start();
        }
    }

    public static synchronized void register(FileHandle handle) {
        //System.out.println("Register cleaner for " + handle.getHandle());
        checkThreadState();
        FileCleaner cleaner = new FileCleaner(handle);
        ReferenceWithCleanup cleanable = new ReferenceWithCleanup(handle, cleaner);
        PHANTOMS.put(handle.getHandle(), cleanable);
    }

    private static class FileCleaner {
        private final int handle;

        public FileCleaner(FileHandle handle) {
            this.handle = handle.getHandle();
        }

        public void clean() {
            //System.out.println("Cleaner.cleanup: " + handle);
            try {
                Fcntl.getInstance().close(handle);
            } catch (NativeIOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ReferenceWithCleanup extends PhantomReference<FileHandle> {
        FileCleaner cleanable;

        ReferenceWithCleanup(FileHandle handle, FileCleaner cleanable) {
            super(handle, QUEUE);
            this.cleanable = cleanable;
        }

        public void clean() {
            //System.out.println("CLEAN ReferenceWithCleanup");
            cleanable.clean();
            PHANTOMS.remove(cleanable.handle);
        }
    }
}
