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

import ru.iothub.jef.linux.spi.SpiInputParams;

/**
 * Wrapper for register commands sets based on specification
 * Please see datasheet for details
 */
class W25XCommandsBuffers {
    public static SpiInputParams B_CMD_READ_UNIQUE_ID = SpiInputParams
            .allocate(5)
            .put(W25XCommands.CMD_READ_UNIQUE_ID.value)
            .putInt(0);

    public static SpiInputParams B_CMD_JEDEC_ID = SpiInputParams.
            allocate(1)
            .put(W25XCommands.CMD_JEDEC_ID.value);

    public static SpiInputParams B_CMD_READ_STATUS_R1 = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_READ_STATUS_R1.value);

    public static SpiInputParams B_CMD_POWER_DOWN = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_POWER_DOWN.value);

    public static SpiInputParams B_CMD_RELEASE_PDOWN_ID = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_RELEASE_PDOWN_ID.value);

    public static SpiInputParams B_CMD_WRIRE_ENABLE = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_WRIRE_ENABLE.value);

    public static SpiInputParams B_CMD_WRITE_DISABLE = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_WRITE_DISABLE.value);

    public static SpiInputParams B_CMD_CHIP_ERASE = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_CHIP_ERASE.value);

    public static SpiInputParams B_CMD_BLOCK_ERASE64KB = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_BLOCK_ERASE64KB.value);
}
