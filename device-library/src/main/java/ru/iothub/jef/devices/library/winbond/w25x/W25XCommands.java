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

package ru.iothub.jef.devices.library.winbond.w25x;

/**
 * Describes registers values.
 * Please see datasheet for details
 */
@SuppressWarnings("unused")
enum W25XCommands {
    CMD_WRIRE_ENABLE(0x06),
    CMD_WRITE_DISABLE(0x04),
    CMD_READ_STATUS_R1(0x05),
    CMD_READ_STATUS_R2(0x35),
    CMD_WRITE_STATUS_R(0x01),
    CMD_PAGE_PROGRAM(0x02),
    CMD_QUAD_PAGE_PROGRAM(0x32),
    CMD_BLOCK_ERASE64KB(0xd8),
    CMD_BLOCK_ERASE32KB(0x52),
    CMD_SECTOR_ERASE(0x20),
    CMD_CHIP_ERASE(0xC7),
    CMD_ERASE_SUPPEND(0x75),
    CMD_ERASE_RESUME(0x7A),
    CMD_POWER_DOWN(0xB9),
    CMD_HIGH_PERFORM_MODE(0xA3),
    CMD_CNT_READ_MODE_RST(0xFF),
    CMD_RELEASE_PDOWN_ID(0xAB),
    CMD_MANUFACURER_ID(0x90),
    CMD_READ_UNIQUE_ID(0x4B),
    CMD_JEDEC_ID(0x9f),

    CMD_READ_DATA(0x03),
    CMD_FAST_READ(0x0B),
    CMD_READ_DUAL_OUTPUT(0x3B),
    CMD_READ_DUAL_IO(0xBB),
    CMD_READ_QUAD_OUTPUT(0x6B),
    CMD_READ_QUAD_IO(0xEB),
    CMD_WORD_READ(0xE3);

    byte value;

    W25XCommands(int value) {
        this.value = (byte) value;
    }
}
