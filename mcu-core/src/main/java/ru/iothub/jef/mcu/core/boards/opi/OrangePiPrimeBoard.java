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

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// http://linux-sunxi.org/Xunlong_Orange_Pi_Prime
public class OrangePiPrimeBoard extends OrangePiAbstractBoard {
    public OrangePiPrimeBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x20 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "PA12", getBoardPin('A', 12)), // TWI0_SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "PA11", getBoardPin('A', 11)), // TWI0_SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "PA6", getBoardPin('A', 6)),
                new BoardPin(8, "PA13", getBoardPin('A', 13)), // SPI1_CS
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "PA14 ", getBoardPin('A', 14)), // SPI1_CLK

                new BoardPin(11, "PA1", getBoardPin('A', 1)),
                new BoardPin(12, "PD14", getBoardPin('D', 14)),
                new BoardPin(13, "PA0", getBoardPin('A', 0)),
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "PA3", getBoardPin('A', 3)),
                new BoardPin(16, "PC4", getBoardPin('C', 4)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "PC7", getBoardPin('C', 7)),
                new BoardPin(19, "PC0", getBoardPin('C', 0)), // SPI0_MOSI
                new BoardPin(20, "Ground", null),

                new BoardPin(21, "PC1", getBoardPin('C', 1)), // SPI0_MISO
                new BoardPin(22, "PA2", getBoardPin('A', 2)), // PA2
                new BoardPin(23, "PC2", getBoardPin('C', 2)), // SPI0_CLK
                new BoardPin(24, "PC3", getBoardPin('C', 3)), // SPI0_CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "PA21", getBoardPin('A', 21)),
                new BoardPin(27, "PA19", getBoardPin('A', 19)),
                new BoardPin(28, "PA18", getBoardPin('A', 18)),
                new BoardPin(29, "PA7", getBoardPin('A', 7)),
                new BoardPin(30, "Ground",null),

                new BoardPin(31, "PA8", getBoardPin('A', 8)),
                new BoardPin(32, "PG8", getBoardPin('G', 8)),
                new BoardPin(33, "PA9", getBoardPin('A', 9)),
                new BoardPin(34, "Ground", null),
                new BoardPin(35, "PA10", getBoardPin('A', 10)),
                new BoardPin(36, "PG9", getBoardPin('G', 9)),
                new BoardPin(37, "PA20", getBoardPin('A', 20)),
                new BoardPin(38, "PG6", getBoardPin('G', 6)),
                new BoardPin(39, "Ground", null),
                new BoardPin(40, "PG7", getBoardPin('G', 7)),

        };
        return Collections.unmodifiableList(Arrays.asList(pins2x20));
    }
}
