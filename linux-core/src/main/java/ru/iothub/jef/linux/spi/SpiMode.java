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

/**
 * Describes Clock polarity and phase of SPI bus @see <a href="https://en.wikipedia.org/wiki/Serial_Peripheral_Interface#Clock_polarity_and_phase">Wikipedia</a>
 * In addition to setting the clock frequency, the master must also configure the clock polarity and phase with respect to the data. Motorola SPI Block Guide[2] names these two options as CPOL and CPHA (for clock polarity and phase) respectively,
 * a convention most vendors have also adopted.
 */
public enum SpiMode {
    /**
     * Represents CPOL = 0, CPHA = 0 mode
     */
    SPI_MODE_0(0),

    /**
     * Represents CPOL = 0, CPHA = 1 mode
     */
    SPI_MODE_1(1),

    /**
     * Represents CPOL = 1, CPHA = 0 mode
     */
    SPI_MODE_2(2),

    /**
     * Represents CPOL = 1, CPHA = 1 mode
     */
    SPI_MODE_3(3);

    int value;

    SpiMode(int value) {
        this.value = value;
    }
}
