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

package ru.iothub.jef.linux.core.types;

public class SmbusIoctlData {
    private byte readWrite;
    private long command;
    private int size;
    private SmbusData data;

    public SmbusIoctlData(byte readWrite, long command, int size, SmbusData data) {
        this.readWrite = readWrite;
        this.command = command;
        this.size = size;
        this.data = data;
    }

    public byte getReadWrite() {
        return readWrite;
    }

    public void setReadWrite(byte readWrite) {
        this.readWrite = readWrite;
    }

    public long getCommand() {
        return command;
    }

    public void setCommand(long command) {
        this.command = command;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SmbusData getData() {
        return data;
    }

    public void setData(SmbusData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SmbusIoctlData{" +
                "readWrite=" + readWrite +
                ", command=" + command +
                ", size=" + size +
                ", data=" + data +
                '}';
    }
}
