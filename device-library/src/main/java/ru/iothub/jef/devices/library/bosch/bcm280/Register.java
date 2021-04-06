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

package ru.iothub.jef.devices.library.bosch.bcm280;

public enum Register {
    BMP280_REGISTER_CHIPID(0xD0),
    BMP280_REGISTER_SOFTRESET(0xE0),

    BMP280_REGISTER_DIG_T1(0x88),
    BMP280_REGISTER_DIG_T2(0x8A),
    BMP280_REGISTER_DIG_T3(0x8C),

    BMP280_REGISTER_DIG_P1(0x8E),
    BMP280_REGISTER_DIG_P2(0x90),
    BMP280_REGISTER_DIG_P3(0x92),
    BMP280_REGISTER_DIG_P4(0x94),
    BMP280_REGISTER_DIG_P5(0x96),
    BMP280_REGISTER_DIG_P6(0x98),
    BMP280_REGISTER_DIG_P7(0x9A),
    BMP280_REGISTER_DIG_P8(0x9C),
    BMP280_REGISTER_DIG_P9(0x9E),

    BMP280_REGISTER_STATUS(0xF3),
    BMP280_REGISTER_CONTROL(0xF4),
    BMP280_REGISTER_CONFIG(0xF5),

    BMP280_REGISTER_PRESSUREDATA_MSB(0xF7),
    BMP280_REGISTER_PRESSUREDATA_LSB(0xF8),
    BMP280_REGISTER_PRESSUREDATA_XLSB(0xF9),
    BMP280_REGISTER_TEMPDATA_MSB(0xFA),
    BMP280_REGISTER_TEMPDATA_LSB(0xFB),
    BMP280_REGISTER_TEMPDATA_XLSB(0xFC);


    private final int value;

    Register(int reg) {
        this.value = reg;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BMP280Register{" +
                "value=" + value +
                '}';
    }
}
