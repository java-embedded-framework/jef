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

// http://linux-sunxi.org/Xunlong_Orange_Pi_One_Plus
// http://linux-sunxi.org/Xunlong_Orange_Pi_Lite_2
public class OrangePiLight2Board extends OrangePiAbstractBoard {
    public OrangePiLight2Board() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "PH6", getBoardPin('H', 6)), // TWI1-SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "PH5", getBoardPin('H', 5)), // 	TWI1-SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "PH4", getBoardPin('H', 4)),
                new BoardPin(8, "PD21", getBoardPin('D', 21)), // UART2-RTS
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "PD22", getBoardPin('D', 22)),  // UART2-CTS

                new BoardPin(11, "PD24", getBoardPin('D', 24)), // UART3-RX
                new BoardPin(12, "PC9", getBoardPin('C', 9)),
                new BoardPin(13, "PD23", getBoardPin('D', 23)), // UART3-TX
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "PD26", getBoardPin('D', 26)), // UART3-CTS
                new BoardPin(16, "PC8", getBoardPin('C', 8)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "PC7", getBoardPin('C', 7)),
                new BoardPin(19, "PC2", getBoardPin('C', 2)),  // SPI0-MOSI
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "PC3", getBoardPin('C', 3)),  // SPI0-MISO
                new BoardPin(22, "PD25", getBoardPin('D', 25)), // UART3-RTS
                new BoardPin(23, "PC0", getBoardPin('C', 0)), // SPI0-SCK
                new BoardPin(24, "PC5", getBoardPin('C', 5)), // SPI0-CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "PH3", getBoardPin('H', 3)), // SPI1-CS
        };

        return Collections.unmodifiableList(Arrays.asList(pins2x13));
    }
}
