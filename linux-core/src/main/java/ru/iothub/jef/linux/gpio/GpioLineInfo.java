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

import java.util.EnumSet;

@SuppressWarnings("unused")
public class GpioLineInfo {
    private int offset;
    private int flags;
    private String name;
    private String consumer;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "GpioLineInfo{" +
                "offset=" + offset +
                ", flags=" + flags +
                ", name='" + name + '\'' +
                ", consumer='" + consumer + '\'' +
                '}';
    }

    public enum Flags {
        GPIOLINE_FLAG_KERNEL(1), // 1
        GPIOLINE_FLAG_IS_OUT(1 << 1),// 2
        GPIOLINE_FLAG_ACTIVE_LOW(1 << 2),// 4
        GPIOLINE_FLAG_OPEN_DRAIN(1 << 3),// 8
        GPIOLINE_FLAG_OPEN_SOURCE(1 << 4), // 16
        GPIOLINE_FLAG_BIAS_PULL_UP(1 << 5), // 32
        GPIOLINE_FLAG_BIAS_PULL_DOWN(1 << 6), // 64
        GPIOLINE_FLAG_BIAS_DISABLE(1 << 7) // 128
        ;
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

        public int getValue() {
            return value;
        }
    }
}
