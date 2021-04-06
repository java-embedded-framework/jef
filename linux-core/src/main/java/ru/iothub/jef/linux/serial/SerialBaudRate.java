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

package ru.iothub.jef.linux.serial;

public enum SerialBaudRate {
    B0(0),         /* hang up */
    B50(1),
    B75(2),
    B110(3),
    B134(4),
    B150(5),
    B200(6),
    B300(7),
    B600(8),
    B1200(9),
    B1800(10),
    B2400(11),
    B4800(12),
    B9600(13),
    B19200(14),
    B38400(15),
    B57600(4097),
    B115200(4098),
    B230400(4099),
    B460800(4100),
    B500000(4101),
    B576000(4102),
    B921600(4103),
    B1000000(4104),
    B1152000(4105),
    B1500000(4106),
    B2000000(4107),
    B2500000(4108),
    B3000000(4109),
    B3500000(4110),
    B4000000(4111);

    final int value;

    SerialBaudRate(int value) {
        this.value = value;
    }
}
