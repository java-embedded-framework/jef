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

package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.linux.gpio.GpioManager;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class RaspberryPi26Rev1Pins {
    static List<BoardPin> createPins() throws IOException {
        BoardPin[] pins = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "GPIO00", getPin(0)),
                new BoardPin(4, "5V", null),
                new BoardPin(5, "GPIO01", getPin(1)),
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "GPIO04", getPin(4)),
                new BoardPin(8, "GPIO14", getPin(14)),
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "GPI015", getPin(15)),
                new BoardPin(11, "GPIO17", getPin(17)),
                new BoardPin(12, "GPIO18", getPin(18)),
                new BoardPin(13, "GPIO21", getPin(21)),
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "GPIO22", getPin(22)),
                new BoardPin(16, "GPIO23", getPin(23)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "GPIO24", getPin(24)),
                new BoardPin(19, "GPIO10", getPin(10)),
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "GPIO09", getPin(9)),
                new BoardPin(22, "GPIO25", getPin(25)),
                new BoardPin(23, "GPIO11", getPin(11)),
                new BoardPin(24, "GPIO08", getPin(8)),
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "GPIO07", getPin(7)),
        };
        return Collections.unmodifiableList(Arrays.asList(pins));
    }

    private static GpioPin getPin(int number) throws IOException {
        return GpioManager.getPin("/dev/gpiochip0", number);
    }
}
