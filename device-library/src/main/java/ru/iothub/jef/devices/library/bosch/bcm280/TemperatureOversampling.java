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


import static ru.iothub.jef.devices.library.bosch.bcm280.Oversampling.*;

/**
 * The current consumption depends on Output Data Rate(ODR) and oversampling setting.
 * The values given below are normalized to an ODR of 1 Hz.
 * The actual consumption at a given ODR can be calculated by multiplying the consumption in
 * Table 12 in datasheet with the ODR used. The actual ODR is defined either by the frequency
 * at which the user sets forced measurements or by oversampling and tstandby settings in
 * normal mode in Table 14 in datasheet.
 */
public enum TemperatureOversampling {
    SKIP(BMP280_OVERSAMP_SKIPPED),
    ULTRA_LOW_POWER(BMP280_OVERSAMP_1X),
    LOW_POWER(BMP280_OVERSAMP_1X),
    STANDARD(BMP280_OVERSAMP_1X),
    HIGH(BMP280_OVERSAMP_1X),
    ULTRA_HIGH(BMP280_OVERSAMP_2X);

    private final int value;

    /**
     * Allocate enum based on {@link Oversampling} value
     * @param oversampling value
     */
    TemperatureOversampling(Oversampling oversampling) {
        this(oversampling.getValue());
    }

    /**
     * Allocate enum value
     * @param value value
     */
    TemperatureOversampling(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link TemperatureOversampling}
     * @param i integer value
     * @return power mode value
     */
    public static TemperatureOversampling fromInteger(int i) {
        for(TemperatureOversampling obj : TemperatureOversampling.values()) {
            if(obj.value == i) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + i);
    }

    /**
     * Returns {@code int} representation of value
     * @return int value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TemperatureOversampling{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
