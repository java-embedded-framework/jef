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

/**
 * The environmental pressure is subject to many short-term changes, caused e.g.
 * by slamming of a door or window, or wind blowing into the sensor. To suppress
 * these disturbances in the output data without causing additional interface traffic
 * and processor work load, the BMP280 features an internal IIR filter.
 * It effectively reduces the bandwidth of the output signals6.
 * The output of a next measurement step is filter using the following formula
 * described in section 3.3.3 in datasheet
 */
public enum IrrFilter {
    /**
     * Filter coefficient - off
     */
    COEFF_OFF(0x00),
    /**
     * Filter coefficient - x2
     */
    COEFF_2(0x01),
    /**
     * Filter coefficient - x4
     */
    COEFF_4(0x02),
    /**
     * Filter coefficient - x8
     */
    COEFF_8(0x03),
    /**
     * Filter coefficient - x6
     */
    COEFF_16(0x04);

    private final int value;

    /**
     * Allocate enum value
     * @param value value
     */
    IrrFilter(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link IrrFilter}
     * @param i integer value
     * @return power mode value
     */
    public static IrrFilter fromInteger(int i) {
        for(IrrFilter obj : IrrFilter.values()) {
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
        return "IrrFilter{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
