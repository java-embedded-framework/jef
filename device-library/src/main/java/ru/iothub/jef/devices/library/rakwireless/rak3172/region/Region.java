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

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;

import java.io.IOException;

public abstract class Region<T extends Region<T>> {
    protected final RAK3172 rak;

    public Region(RAK3172 rak) {
        this.rak = rak;
    }

    public abstract int getCode();

    public void apply(RAK3172 rak) throws IOException {
        Rak3172ATCommands commands = rak.getCommands();
        commands.AT_BAND().set(getCode());
    }

    public static Region<?> create(RAK3172 rak) throws IOException {
        Rak3172ATCommands commands = rak.getCommands();
        int code = Integer.parseInt(commands.AT_BAND().get());

        Region<?> result;

        switch (code) {
            case EU433.CODE:
                result = new EU433(rak);
                break;
            case CN470.CODE:
                result = new CN470(rak);
                break;
            case RU864.CODE:
                result = new RU864(rak);
                break;
            case IN865.CODE:
                result = new IN865(rak);
                break;
            case EU868.CODE:
                result = new IN865(rak);
                break;
            case US915.CODE:
                result = new US915(rak);
                break;
            case AU915.CODE:
                result = new AU915(rak);
                break;
            case KR920.CODE:
                result = new KR920(rak);
                break;
            case AS923.CODE:
                result = new AS923(rak);
                break;
            default:
                throw new IOException("Unknown device region: " + code);
        }
        result.read(rak);
        return result;
    }

    protected void read(RAK3172 rak) throws IOException {

    }
}
