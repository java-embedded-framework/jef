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

public class GpioHandleRequest {
    public enum Flags {
        GPIOHANDLE_REQUEST_INPUT(1),
        GPIOHANDLE_REQUEST_OUTPUT(1 << 1),
        GPIOHANDLE_REQUEST_ACTIVE_LOW(1 << 2),
        GPIOHANDLE_REQUEST_OPEN_DRAIN(1 << 3),
        GPIOHANDLE_REQUEST_OPEN_SOURCE(1 << 4)
        ;
        int value;

        Flags(int value) {
            this.value = value;
        }
    }

    private int[] lineoffsets;
    private int flags;
    private byte[] default_values;
    private byte[] consumer_label;
    private int lines;
    private int fd;

    public int getLines() {
        return lines;
    }

    public int getFd() {
        return fd;
    }

    public int getFlags() {
        return flags;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setLinesOffset(int[] offset) {
        this.lineoffsets = offset;
    }

    public int[] getLineOffsets() {
        return lineoffsets;
    }

    public void setFd(int fd) {
        this.fd = fd;
    }

    public void setConsumerLabel(String jef) {
        consumer_label = jef.getBytes();
    }

    public byte[] getConsumerLabel() {
        return consumer_label;
    }

    public byte[] getDefaultValues() {
        return default_values;
    }

    public void setDefaultValues(byte[] default_values) {
        this.default_values = default_values;
    }
}
