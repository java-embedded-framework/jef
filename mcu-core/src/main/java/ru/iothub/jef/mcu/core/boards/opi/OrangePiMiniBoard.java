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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// http://linux-sunxi.org/Xunlong_Orange_Pi_Mini
class OrangePiMiniBoard extends OrangePiBoard {
    public OrangePiMiniBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        // Mini board have the same 26 pins from OPi + 27-40
        BoardPin[] pins = {
                new BoardPin(27, "PB5", getBoardPin('B', 8)), // I2S_MCLK
                new BoardPin(28, "PI12", getBoardPin('I', 12)),
                new BoardPin(29, "PB6", getBoardPin('B', 6)), // I2S_BCLK
                new BoardPin(30, "Ground", null),
                new BoardPin(31, "PB7", getBoardPin('B', 7)), // I2S_LRCK
                new BoardPin(32, "PI20", getBoardPin('I', 20)), // UART7_TX
                new BoardPin(33, "PB8", getBoardPin('B', 8)), // I2S_DO0
                new BoardPin(34, "Ground", null),
                new BoardPin(35, "PB12", getBoardPin('B', 12)), // I2S_DI
                new BoardPin(36, "PI21", getBoardPin('I', 21)), // UART7_RX
                new BoardPin(37, "PB13", getBoardPin('B', 13)), // SDPIF_D0
                new BoardPin(38, "PH3", getBoardPin('H', 3)),
                new BoardPin(39, "Ground", null),
                new BoardPin(40, "PH5", getBoardPin('H', 5)),
        };
        ArrayList<BoardPin> result = new ArrayList<>();
        result.addAll(super.initGPIO());
        result.addAll(Arrays.asList(pins));
        return result;
    }
}
