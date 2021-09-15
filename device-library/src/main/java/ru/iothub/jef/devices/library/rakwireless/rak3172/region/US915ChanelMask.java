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

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import java.util.Objects;

public enum US915ChanelMask implements ChannelMask<US915ChanelMask> {
    ALL("0000", "ALL"),
    SUB_BAND_1("0001", "0-7"),
    SUB_BAND_2("0002", "8-15"),
    SUB_BAND_3("0004", "16-23"),
    SUB_BAND_4("0008", "24-31"),
    SUB_BAND_5("0010", "32-39"),
    SUB_BAND_6("0020", "40-47"),
    SUB_BAND_7("0040", "48-55"),
    SUB_BAND_8("0080", "56-63");

    final String hex;
    final String chanels;

    US915ChanelMask(String hex, String chanels) {
        this.hex = hex;
        this.chanels = chanels;
    }

    public static US915ChanelMask fromString(String value) {
        Objects.requireNonNull(value);

        for(US915ChanelMask obj : US915ChanelMask.values()) {
            if(obj.hex.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + value);
    }

    @Override
    public String getHex() {
        return null;
    }

    @Override
    public String getChanels() {
        return null;
    }
}
