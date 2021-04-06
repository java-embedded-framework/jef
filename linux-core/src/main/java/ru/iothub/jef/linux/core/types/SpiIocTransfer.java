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

import java.nio.ByteBuffer;

public class SpiIocTransfer {
    private final ByteBuffer txBuffer;
    private final ByteBuffer rxBuffer;

    private final int length;
    private final int speed;
    private final short delay;
    private final byte bitsPerWord;
    private final byte csChange;
    private final byte pad;

    public SpiIocTransfer(ByteBuffer txBuffer, ByteBuffer rxBuffer, int length, int speed, short delay, byte bitsPerWord) {
        this(txBuffer, rxBuffer, length, speed, delay, bitsPerWord, (byte) -1, (byte) -1);
    }

    public SpiIocTransfer(ByteBuffer txBuffer, ByteBuffer rxBuffer, int length, int speed, short delay, byte bitsPerWord, byte csChange, byte pad) {
        this.txBuffer = txBuffer;
        this.rxBuffer = rxBuffer;
        this.length = length;
        this.speed = speed;
        this.delay = delay;
        this.bitsPerWord = bitsPerWord;
        this.csChange = csChange;
        this.pad = pad;
    }

    public ByteBuffer getTxBuffer() {
        return txBuffer;
    }

    public ByteBuffer getRxBuffer() {
        return rxBuffer;
    }

    public int getLength() {
        return length;
    }

    public int getSpeed() {
        return speed;
    }

    public short getDelay() {
        return delay;
    }

    public byte getBitsPerWord() {
        return bitsPerWord;
    }

    public byte getCsChange() {
        return csChange;
    }

    public byte getPad() {
        return pad;
    }
}
