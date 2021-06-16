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

// http://linux-sunxi.org/Xunlong_Orange_Pi
class OrangePiBoard extends OrangePiAbstractBoard {
    public OrangePiBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "PB21", getBoardPin('B', 21)), // TWI2-SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "PB20", getBoardPin('B', 20)), // TWI2-SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "PI3", getBoardPin('I', 3)),
                new BoardPin(8, "PH0", getBoardPin('H', 0)), // UART3_TX
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "PH1", getBoardPin('H', 1)), // UART3_RX

                new BoardPin(11, "PI19", getBoardPin('I', 19)), // UART2_RX
                new BoardPin(12, "PH2", getBoardPin('H', 2)),
                new BoardPin(13, "PI18", getBoardPin('I', 18)), // UART2_TX
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "PI17", getBoardPin('I', 17)), // UART2_CTS
                new BoardPin(16, "PH20", getBoardPin('H', 20)), // CAN_TX
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "PH21", getBoardPin('H', 21)), // CAN_RX
                new BoardPin(19, "PI12", getBoardPin('I', 12)), // SPI0_MOSI
                new BoardPin(20, "Ground", null),

                new BoardPin(21, "PI13", getBoardPin('I', 13)), // SPI0_MISO
                new BoardPin(22, "PI16", getBoardPin('I', 16)), // UART2_RTS
                new BoardPin(23, "PI11", getBoardPin('I', 11)), // SPI0_CLK
                new BoardPin(24, "PI10", getBoardPin('I', 10)), // SPI0_CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "PI14", getBoardPin('I', 14)), // SPI0_CS1
        };
        return Collections.unmodifiableList(Arrays.asList(pins2x13));
    }
}
