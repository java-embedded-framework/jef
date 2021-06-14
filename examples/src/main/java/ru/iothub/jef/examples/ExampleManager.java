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

package ru.iothub.jef.examples;

import ru.iothub.jef.examples.bluetooth.SimpleScanExample;
import ru.iothub.jef.examples.gpio.GpioPinFunctions;
import ru.iothub.jef.examples.gpio.GpioReadAll;
import ru.iothub.jef.examples.i2c.BCM280Example;
import ru.iothub.jef.examples.i2c.I2CScanner;
import ru.iothub.jef.examples.misc.Blink;
import ru.iothub.jef.examples.onewire.DS18B20Example;
import ru.iothub.jef.examples.serial.SerialExample;
import ru.iothub.jef.examples.spi.W25xFlashExample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Static holder for examples
 */
class ExampleManager {
    private final static List<ExampleGroup> groups = Collections.unmodifiableList(new ArrayList<ExampleGroup>() {
        {
            add(new ExampleGroup("General") {
                {
                    add(new ShowBoardInfoExample());
                    add(new GpioPinFunctions());
                    add(new GpioReadAll());

                }
            });

            add(new ExampleGroup("GPIO") {
                {
                    add(new Blink());
                }
            });

            add(new ExampleGroup("I2C") {
                {
                    add(new I2CScanner());
                    add(new BCM280Example());
                }
            });

            add(new ExampleGroup("SPI") {
                {
                    add(new W25xFlashExample());
                }
            });

            add(new ExampleGroup("1-Wire") {
                {
                    add(new DS18B20Example());
                }
            });

            add(new ExampleGroup("Serial") {
                {
                    add(new SerialExample());
                }
            });

            /*add(new ExampleGroup("Bluetooth") {
                {
                    add(new SimpleScanExample());
                }
            });*/
        }
    });

    static List<ExampleGroup> getExamples() {
        return groups;
    }

    private static Example getExample(int index) {
        int x = 0;
        for (ExampleGroup group : groups) {
            for (Example example : group.getItems()) {
                if (x == index) {
                    return example;
                }
                x++;
            }

        }
        return null;
    }

    static void execute(int index) throws Exception {
        Example example = getExample(index);
        if (example != null) {
            example.init();
            example.showIntro();
            example.execute();
        }
    }
}
