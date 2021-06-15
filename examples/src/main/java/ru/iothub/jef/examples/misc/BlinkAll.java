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
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;
import ru.iothub.jef.mcu.core.boards.BoardPin;
import ru.iothub.jef.mcu.core.boards.BoardPinState;

import java.util.List;

public class BlinkAll implements Example {
    private Board board;
    public static final int DELAY_TIME_MS = 1000;

    @Override
    public String getName() {
        return "Blink all pins";
    }

    @Override
    public void init() throws Exception {
        board = BoardManager.getBoard();
        System.out.println("Please press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        List<BoardPin> pins = board.getPins();
        int number = 0;

        for (BoardPin pin : pins) {
            number++;
            if (pin.isDummyPin()) {
                System.out.println("pin['"+number+"'] is not functional pin. Skipped.");
                Thread.sleep(DELAY_TIME_MS);
                continue;
            }

            if(pin.getPinInfo().isUsedByKernel()) {
                System.out.println("pin['"+number+"'] is used by kernel. Skipped.");
                Thread.sleep(DELAY_TIME_MS);
                continue;
            }

            boolean on = false;
            pin.pinMode(GpioPin.Direction.OUTPUT);
            for (int i = 0; i < 10; i++) {
                System.out.println("Blinking pin['"+number+"']... " + (on ? "ON" : "OFF"));
                pin.digitalWrite(on ? BoardPinState.HIGH : BoardPinState.LOW);
                on = !on;
                Thread.sleep(DELAY_TIME_MS);
            }

        }
    }

    @Override
    public void showIntro() {
        System.out.println("Blinking LED 10 times by every pin");
    }
}
