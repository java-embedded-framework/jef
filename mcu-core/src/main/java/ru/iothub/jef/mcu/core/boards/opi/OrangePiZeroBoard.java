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

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.linux.gpio.GpioManager;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.linux.spi.SpiMode;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class OrangePiZeroBoard extends OrangePiAbstractBoard {

    public OrangePiZeroBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "GPIO12", getBoardPin(12)),
                new BoardPin(4, "5V", null),
                new BoardPin(5, "GPIO11", getBoardPin(11)),
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "GPIO6", getBoardPin(6)),
                new BoardPin(8, "GPIO198", getBoardPin(198)),
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "GPIO199", getBoardPin(199)),
                new BoardPin(11, "GPIO1", getBoardPin(1)),
                new BoardPin(12, "GPIO7", getBoardPin(7)),
                new BoardPin(13, "GPIO", getBoardPin(0)),
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "GPIO3", getBoardPin(3)),
                new BoardPin(16, "GPIO19", getBoardPin(19)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "GPIO18", getBoardPin(18)),
                new BoardPin(19, "GPIO15", getBoardPin(15)),
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "GPIO16", getBoardPin(16)),
                new BoardPin(22, "GPIO2", getBoardPin(2)),
                new BoardPin(23, "GPIO14", getBoardPin(14)),
                new BoardPin(24, "GPIO13", getBoardPin(13)),
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "GPIO10", getBoardPin(10)),
        };

        return Collections.unmodifiableList(Arrays.asList(pins2x13));    }

    @Override
    protected List<I2CBus> initI2C() throws IOException {
        final List<I2CBus> i2cs = new ArrayList<>();
        if (new File("/dev/i2c-0").exists()) {
            i2cs.add(
                    new I2CBus("/dev/i2c-0")
            );
        }
        if (new File("/dev/i2c-1").exists()) {
            i2cs.add(
                    new I2CBus("/dev/i2c-1")
            );
        }
        if (new File("/dev/i2c-2").exists()) {
            i2cs.add(
                    new I2CBus("/dev/i2c-2")
            );
        }
        return Collections.unmodifiableList(i2cs);
    }

    @Override
    protected List<SpiBus> initSPI() throws IOException {
        List<SpiBus> ss = new ArrayList<>();
        if (new File("/dev/spidev0.0").exists()) {
            ss.add(
                    new SpiBus("/dev/spidev0.0", 500000, SpiMode.SPI_MODE_3, 8, 0)
            );
        }
        return Collections.unmodifiableList(ss);
    }

    private static GpioPin getBoardPin(int number) throws IOException {
        return GpioManager.getPin("/dev/gpiochip0", number);
    }
}
