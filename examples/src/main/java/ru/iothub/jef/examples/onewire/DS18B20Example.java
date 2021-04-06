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

package ru.iothub.jef.examples.onewire;

import ru.iothub.jef.devices.library.maximintegrated.DS18B20;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;

public class DS18B20Example implements Example {
    private DS18B20 sensor;

    @Override
    public String getName() {
        return "DS18B20 Temperature Sensor";
    }

    @Override
    public void init() throws Exception {
        //TODO add find in path
        sensor = new DS18B20("/sys/bus/w1/devices/28-01203882217a/w1_slave");
        System.out.println("Please connect DS18B20 to GPIO Pin 4 (Board Pin - 7) and press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        for (int i = 0; i < 10; i++) {
            double c = sensor.getTemperatureCelsius();
            System.out.printf("temperature: %.2f °C\n", c);
            Thread.sleep(1000);
        }
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Please connect DS18B20 to GPIO4 (Board Pin #7) and press any <enter>");
    }
}
