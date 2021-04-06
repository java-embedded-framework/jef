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

package ru.iothub.jef.linux.spi;

import ru.iothub.jef.linux.core.io.DynamicByteBuffer;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * The {@link SpiInputParams} class provides wrapper for input parameters for transmitting to {@link SpiBus}.
 * Practically it's wrapper to {@link java.nio.ByteBuffer} with auto extension feature
 */
@SuppressWarnings("unused")
public class SpiInputParams {
    private final DynamicByteBuffer byteBuffer;

    private SpiInputParams(int capacity) {
        byteBuffer = new DynamicByteBuffer(capacity, 1.5F);
    }

    private SpiInputParams(ByteBuffer buffer) {
        this(buffer.capacity());
        put(buffer);
    }

    /**
     * Allocates new instance of {@link SpiInputParams} with required capacity
     *
     * @param capacity initial capacity
     * @return parameters buffer
     */
    public static SpiInputParams allocate(int capacity) {
        return new SpiInputParams(capacity);
    }

    /**
     * Allocates new instance of {@link SpiInputParams} from initial {@link ByteBuffer}
     *
     * @param buffer input buffer
     * @return parameters buffer
     */
    public static SpiInputParams allocate(ByteBuffer buffer) {
        return new SpiInputParams(buffer);
    }

    /**
     * Relative <i>put</i> method&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes the given byte into this buffer at the current
     * position, and then increments the position. </p>
     *
     * @param b The byte to be written
     * @return This buffer
     */
    public SpiInputParams put(byte b) {
        byteBuffer.put(b);
        return this;
    }

    /**
     * Relative bulk <i>put</i> method&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> This method transfers the entire content of the given source
     * byte array into this buffer.  An invocation of this method of the
     * form {@code dst.put(a)} behaves in exactly the same way as the
     * invocation
     *
     * <pre>
     *     dst.put(a, 0, a.length) </pre>
     *
     * @param src The source array
     * @return This buffer
     */
    public SpiInputParams put(byte[] src) {
        byteBuffer.put(src);
        return this;
    }

    /**
     * Relative bulk <i>put</i> method&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> This method transfers the bytes remaining in the given source
     * buffer into this buffer.  If there are more bytes remaining in the
     * source buffer than in this buffer, that is, if
     * {@code src.remaining()}&nbsp;{@code >}&nbsp;{@code remaining()},
     * then no bytes are transferred and a {@link
     * BufferOverflowException} is thrown.
     *
     * <p> Otherwise, this method copies
     * <i>n</i>&nbsp;=&nbsp;{@code src.remaining()} bytes from the given
     * buffer into this buffer, starting at each buffer's current position.
     * The positions of both buffers are then incremented by <i>n</i>.
     *
     * <p> In other words, an invocation of this method of the form
     * {@code dst.put(src)} has exactly the same effect as the loop
     *
     * <pre>
     *     while (src.hasRemaining())
     *         dst.put(src.get()); </pre>
     * <p>
     * except that it first checks that there is sufficient space in this
     * buffer and it is potentially much more efficient.
     *
     * @param src The source buffer from which bytes are to be read;
     *            must not be this buffer
     * @return This buffer
     * @throws BufferOverflowException  If there is insufficient space in this buffer
     *                                  for the remaining bytes in the source buffer
     * @throws IllegalArgumentException If the source buffer is this buffer
     */
    public SpiInputParams put(ByteBuffer src) {
        byteBuffer.put(src);
        return this;
    }

    /**
     * Relative <i>put</i> method for writing a char
     * value&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes two bytes containing the given char value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by two.  </p>
     *
     * @param value The char value to be written
     * @return This buffer
     */
    public SpiInputParams putChar(char value) {
        byteBuffer.putChar(value);
        return this;
    }

    /**
     * Relative <i>put</i> method for writing a double
     * value&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes eight bytes containing the given double value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The double value to be written
     * @return This buffer
     */
    public SpiInputParams putDouble(double value) {
        byteBuffer.putDouble(value);
        return this;
    }

    /**
     * Relative <i>put</i> method for writing a float
     * value&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes four bytes containing the given float value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The float value to be written
     * @return This buffer
     */
    public SpiInputParams putFloat(float value) {
        byteBuffer.putFloat(value);
        return this;
    }

    /**
     * Relative <i>put</i> method for writing an int
     * value&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes four bytes containing the given int value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by four.  </p>
     *
     * @param value The int value to be written
     * @return This buffer
     */
    public SpiInputParams putInt(int value) {
        byteBuffer.putInt(value);
        return this;
    }

    /**
     * Relative <i>put</i> method for writing a long
     * value&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes eight bytes containing the given long value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by eight.  </p>
     *
     * @param value The long value to be written
     * @return This buffer
     */
    public SpiInputParams putLong(long value) {
        byteBuffer.putLong(value);
        return this;
    }

    /**
     * Relative <i>put</i> method for writing a short
     * value&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> Writes two bytes containing the given short value, in the
     * current byte order, into this buffer at the current position, and then
     * increments the position by two.  </p>
     *
     * @param value The short value to be written
     * @return This buffer
     */
    public SpiInputParams putShort(short value) {
        byteBuffer.putShort(value);
        return this;
    }

    /**
     * Return ready to use buffer without empty capacity
     *
     * @return final byte buffer
     */
    ByteBuffer getFinal() {
        byteBuffer.compact();
        return byteBuffer.getByteBuffer();
    }
}
