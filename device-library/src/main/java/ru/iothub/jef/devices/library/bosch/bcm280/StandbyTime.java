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
 * Standup time for sensor
 * Please see datasheet for details
 */
public enum StandbyTime {
    /**
     * 1 ms standby time
     */
    STANDBY_TIME_1_MS(0x00),
    /**
     * 63 ms standby time
     */
    STANDBY_TIME_63_MS(0x01),
    /**
     * 125 ms standby time
     */
    STANDBY_TIME_125_MS(0x02),
    /**
     * 250 ms standby time
     */
    STANDBY_TIME_250_MS(0x03),
    /**
     * 500 ms standby time
     */
    STANDBY_TIME_500_MS(0x04),
    /**
     * 1000 ms standby time
     */
    STANDBY_TIME_1000_MS(0x05),
    /**
     * 2000 ms standby time
     */
    STANDBY_TIME_2000_MS(0x06),
    /**
     * 4000 ms standby time
     */
    STANDBY_TIME_4000_MS(0x07);

    private final int value;

    /**
     * Allocate enum value
     * @param value value
     */
    StandbyTime(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link StandbyTime}
     * @param i integer value
     * @return power mode value
     */
    public static StandbyTime fromInteger(int i) {
        for(StandbyTime obj : StandbyTime.values()) {
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
        return "StandbyTime{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
