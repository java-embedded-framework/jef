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

public enum IOFlags {
    O_APPEND(1024),
    O_ASYNC(8192),
    O_CLOEXEC(524288),
    O_CREAT(64),
    O_DIRECT(16384),
    O_DIRECTORY(65536),
    O_DSYNC(4096),
    O_EXCL(128),
    O_LARGEFILE(0),
    O_NOATIME(262144),
    O_NONBLOCK(2048),
    O_NOCTTY(256),
    O_NOFOLLOW(131072),
    O_PATH(2097152),
    O_RDONLY(0),
    O_RDWR(2),
    O_SYNC(1052672),
    O_TMPFILE(4259840),
    O_TRUNC(512),
    O_WRONLY(1);

    final int value;

    IOFlags(int value) {
        this.value = value;
    }

}