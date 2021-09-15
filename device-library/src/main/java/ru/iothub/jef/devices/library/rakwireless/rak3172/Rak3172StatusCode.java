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

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.util.Objects;

public enum Rak3172StatusCode {
    OK(0, "OK", "Command executed correctly without error"),
    AT_ERROR(1, "AT_ERROR", "Generic error or input is not supported"),
    AT_PARAM_ERROR(2, "AT_PARAM_ERROR", "The input parameter of the command is wrong"),
    AT_BUSY_ERROR(3, "AT_BUSY_ERROR", "The network is busy so the command is not completed"),
    AT_TEST_PARAM_OVERFLOW(4, "AT_TEST_PARAM_OVERFLOW", "The parameter is too long"),
    AT_NO_NETWORK_JOINED(5, "AT_NO_NETWORK_JOINED", "Module is not yet joined to a network"),
    AT_RX_ERROR(6, "AT_RX_ERROR", "Error detected during the reception of the command"),
    AT_DUTYCYLE_RESTRICTED(7, "AT_DUTYCYLE_RESTRICTED", "	Duty cycle limited and cannot send data");

    int id;
    String code;
    String description;

    Rak3172StatusCode(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public static Rak3172StatusCode fromString(String s) {
        Objects.requireNonNull(s);

        for(Rak3172StatusCode obj : Rak3172StatusCode.values()) {
            if(obj.code.equals(s)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + s);
    }
}
