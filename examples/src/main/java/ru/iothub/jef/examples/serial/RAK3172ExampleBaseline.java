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

package ru.iothub.jef.examples.serial;

import ru.iothub.jef.devices.library.rakwireless.rak3172.ATCommand;
import ru.iothub.jef.devices.library.rakwireless.rak3172.LoRaWANDeviceConfiguration;
import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.serial.SerialPort;

public abstract class RAK3172ExampleBaseline implements Example {
    public abstract SerialPort getSerial() throws NativeIOException;

    @Override
    public void execute() throws Exception {
        SerialPort port = getSerial();
        //SerialPort port = new SerialPort("/dev/ttyUSB0", SerialBaudRate.B9600);

        LoRaWANDeviceConfiguration conf = new LoRaWANDeviceConfiguration()
                .withClassA();

        RAK3172 rak = new RAK3172(port, conf);
        Rak3172ATCommands commands = rak.getCommands();
        run(commands.AT());
        run(commands.AT());
        run(commands.AT());
        run(commands.AT());
        get(commands.AT_SN());
    }

    private void get(ATCommand command) {
        try {
            command.get();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    protected void run(ATCommand command) {
        try {
            command.run();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
