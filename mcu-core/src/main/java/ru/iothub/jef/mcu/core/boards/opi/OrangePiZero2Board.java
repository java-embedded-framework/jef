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

// https://linux-sunxi.org/Xunlong_Orange_Pi_Zero2
public class OrangePiZero2Board extends OrangePiAbstractBoard {
    public OrangePiZero2Board() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "PH5", getBoardPin('H', 5)), // I2C3-SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "PH4", getBoardPin('H', 4)), // I2C3-SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "PC9", getBoardPin('C', 9)),
                new BoardPin(8, "PH2", getBoardPin('H', 2)), // UART5-TX
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "PH3", getBoardPin('H', 3)),  // UART5-RX
                new BoardPin(11, "PC6", getBoardPin('C', 6)),
                new BoardPin(12, "PC11", getBoardPin('C', 11)),
                new BoardPin(13, "PC5", getBoardPin('C', 5)),
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "PC8", getBoardPin('C', 8)),
                new BoardPin(16, "PC15", getBoardPin('C', 15)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "PC14", getBoardPin('C', 14)),
                new BoardPin(19, "PH7", getBoardPin('H', 7)),  // SPI1-MOSI
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "PH8", getBoardPin('H', 8)),  // SPI1-MISO
                new BoardPin(22, "PC7", getBoardPin('C', 7)),
                new BoardPin(23, "PH6", getBoardPin('H', 6)), // SPI1-SCK
                new BoardPin(24, "PH9", getBoardPin('H', 9)), // SPI1-CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "PC10", getBoardPin('C', 10)),
        };

        return Collections.unmodifiableList(Arrays.asList(pins2x13));
    }
}
