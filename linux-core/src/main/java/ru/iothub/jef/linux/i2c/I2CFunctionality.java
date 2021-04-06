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

package ru.iothub.jef.linux.i2c;

/**
 * https://www.kernel.org/doc/Documentation/i2c/functionality
 * Because not every I2C or SMBus adapter implements everything in the
 * I2C specifications, a client can not trust that everything it needs
 * is implemented when it is given the option to attach to an adapter:
 * the client needs some way to check whether an adapter has the needed
 * functionality.
 */
public enum I2CFunctionality {
    /**
     * Plain i2c-level commands
     * (Pure SMBus adapters typically can not do these)
     */
    I2C_FUNC_I2C(0x00000001),

    /**
     * Handles the 10-bit address extensions
     */
    I2C_FUNC_10BIT_ADDR(0x00000002),

    /**
     * Knows about the I2C_M_IGNORE_NAK,
     * I2C_M_REV_DIR_ADDR and I2C_M_NO_RD_ACK
     * flags (which modify the I2C protocol!)
     */
    I2C_FUNC_PROTOCOL_MANGLING(0x00000004),


    I2C_FUNC_SMBUS_PEC(0x00000008),

    /**
     * Can skip repeated start sequence
     * In kernel versions prior to 3.5 I2C_FUNC_NOSTART was implemented as
     * part of I2C_FUNC_PROTOCOL_MANGLING.
     */
    I2C_FUNC_NOSTART(0x00000010), /* I2C_M_NOSTART */
    I2C_FUNC_SLAVE(0x00000020),
    I2C_FUNC_SMBUS_BLOCK_PROC_CALL(0x00008000), /* SMBus 2.0 */

    /**
     * Handles the SMBus write_quick command
     */
    I2C_FUNC_SMBUS_QUICK(0x00010000),

    /**
     * Handles the SMBus read_byte command
     */
    I2C_FUNC_SMBUS_READ_BYTE(0x00020000),

    /**
     * Handles the SMBus write_byte command
     */
    I2C_FUNC_SMBUS_WRITE_BYTE(0x00040000),

    /**
     * Handles the SMBus read_byte_data command
     */
    I2C_FUNC_SMBUS_READ_BYTE_DATA(0x00080000),

    /**
     * Handles the SMBus write_byte_data command
     */
    I2C_FUNC_SMBUS_WRITE_BYTE_DATA(0x00100000),

    /**
     * Handles the SMBus read_word_data command
     */
    I2C_FUNC_SMBUS_READ_WORD_DATA(0x00200000),

    /**
     * Handles the SMBus write_byte_data command
     */
    I2C_FUNC_SMBUS_WRITE_WORD_DATA(0x00400000),

    /**
     * Handles the SMBus process_call command
     */
    I2C_FUNC_SMBUS_PROC_CALL(0x00800000),

    /**
     * Handles the SMBus read_block_data command
     */
    I2C_FUNC_SMBUS_READ_BLOCK_DATA(0x01000000),

    /**
     * Handles the SMBus write_block_data command
     */
    I2C_FUNC_SMBUS_WRITE_BLOCK_DATA(0x02000000),

    /**
     * Handles the SMBus read_i2c_block_data command
     */
    I2C_FUNC_SMBUS_READ_I2C_BLOCK(0x04000000), /* I2C-like block xfer  */

    /**
     * Handles the SMBus write_i2c_block_data command
     */
    I2C_FUNC_SMBUS_WRITE_I2C_BLOCK(0x08000000), /* w/ 1-byte reg. addr. */
    I2C_FUNC_SMBUS_HOST_NOTIFY(0x10000000),

    /**
     * Handles the SMBus read_byte and write_byte commands
     */
    I2C_FUNC_SMBUS_BYTE(I2C_FUNC_SMBUS_READ_BYTE,
            I2C_FUNC_SMBUS_WRITE_BYTE),

    /**
     * Handles the SMBus read_byte_data and write_byte_data commands
     */
    I2C_FUNC_SMBUS_BYTE_DATA(I2C_FUNC_SMBUS_READ_BYTE_DATA,
            I2C_FUNC_SMBUS_WRITE_BYTE_DATA),

    /**
     * Handles the SMBus read_word_data and write_word_data commands
     */
    I2C_FUNC_SMBUS_WORD_DATA(I2C_FUNC_SMBUS_READ_WORD_DATA,
            I2C_FUNC_SMBUS_WRITE_WORD_DATA),

    /**
     * Handles the SMBus read_block_data and write_block_data commands
     */
    I2C_FUNC_SMBUS_BLOCK_DATA(I2C_FUNC_SMBUS_READ_BLOCK_DATA,
            I2C_FUNC_SMBUS_WRITE_BLOCK_DATA),

    /**
     * Handles the SMBus read_i2c_block_data and write_i2c_block_data commands
     */
    I2C_FUNC_SMBUS_I2C_BLOCK(I2C_FUNC_SMBUS_READ_I2C_BLOCK,
            I2C_FUNC_SMBUS_WRITE_I2C_BLOCK),

    /**
     * Handles all SMBus commands that can be
     * emulated by a real I2C adapter (using
     * the transparent emulation layer)
     */
    I2C_FUNC_SMBUS_EMUL(I2C_FUNC_SMBUS_QUICK,
            I2C_FUNC_SMBUS_BYTE,
            I2C_FUNC_SMBUS_BYTE_DATA,
            I2C_FUNC_SMBUS_WORD_DATA,
            I2C_FUNC_SMBUS_PROC_CALL,
            I2C_FUNC_SMBUS_WRITE_BLOCK_DATA,
            I2C_FUNC_SMBUS_I2C_BLOCK,
            I2C_FUNC_SMBUS_PEC);


    int value;

    I2CFunctionality(int value) {
        this.value = value;
    }

    I2CFunctionality(I2CFunctionality... func) {
        for (I2CFunctionality item : func) {
            value = value | item.value;
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "I2CFunctionality{" +
                "value=" + value +
                '}';
    }
}
