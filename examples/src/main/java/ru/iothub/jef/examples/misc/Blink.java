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

package ru.iothub.jef.examples.misc;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.gpio.GpioManager;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.mcu.core.boards.rpi.RaspberryPi4B;

public class Blink implements Example {
    public static final int PIN_NUMBER = 40;
    public static final int DELAY_TIME_MS = 1000;

    private RaspberryPi4B board;

    @Override
    public String getName() {
        return "Blink LED in PIN " + PIN_NUMBER;
    }

    @Override
    public void init() throws Exception {
        board = new RaspberryPi4B();
        System.out.println("Please connect LED to PIN-" + PIN_NUMBER + " press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        String path = "/dev/gpiochip0";
        int pinNumber = 21;
        GpioPin pin = GpioManager.getPin(path, pinNumber);
        pin.setDirection(GpioPin.Direction.OUTPUT);
        //PinCtrl.pinMode(path, pin, PinCtrl.Mode.OUTPUT);

        boolean active = true;

        for (int i = 0; i < 10; i++) {
            System.out.println("Blinking... " + (active ? "ON" : "OFF"));
            pin.write(active ? GpioPin.State.HIGH : GpioPin.State.LOW);
            /*PinCtrl.digitalWrite(path, pin,
                    active ? PinCtrl.State.HIGH : PinCtrl.State.LOW
            );*/


            //PinCtrl.State state = PinCtrl.digitalRead(path, pin);
            //GpioPin.State state = pin.read();
            //System.out.println("read state = " + state);

            active = !active;
            Thread.sleep(DELAY_TIME_MS);
        }
        //PinCtrl.pinMode(path, pin, PinCtrl.Mode.INPUT);

        /*BoardPin pin = board.getPin(PIN_NUMBER);

        Pin.State oldState = pin.digitalRead();


        pin.pinMode(Pin.Mode.OUTPUT);
        boolean active = false;

        for (int i = 0; i < 10; i++) {
            System.out.println("Blinking... " + (active ? "ON" : "OFF"));
            pin.digitalWrite(
                    active ? Pin.State.HIGH : Pin.State.LOW
            );
            active = !active;
            Thread.sleep(DELAY_TIME_MS);
        }
        pin.digitalWrite(oldState);
        */
        System.out.println("Please press <enter> to return to menu");

        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Blinking LED 10 times in RPI Pin " + PIN_NUMBER);
    }
}
