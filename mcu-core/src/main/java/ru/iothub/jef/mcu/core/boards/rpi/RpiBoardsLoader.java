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

import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static ru.iothub.jef.mcu.core.boards.rpi.RpiBoardInfo.*;

public class RpiBoardsLoader implements BoardLoader {
    private final static String CPU_INFO_PATH = "/proc/cpuinfo";
    private final static String REV_KEY = "Revision";

    private final static Map<String, RpiBoardInfo> mapping = new HashMap<String, RpiBoardInfo>() {
        {
            put(A_PLUS_1_1.getCode(), A_PLUS_1_1);
            put(B_PLUS_1_2.getCode(), B_PLUS_1_2);
            put(ZERO_1_2.getCode(), ZERO_1_2);
            put(ZERO_1_3.getCode(), ZERO_1_3);
            put(ZERO_W.getCode(), ZERO_W);
            put(THREE_A_PLUS_1_0.getCode(), THREE_A_PLUS_1_0);
            put(ZERO_1_2E.getCode(), ZERO_1_2E);
            put(ZERO_1_3E.getCode(), ZERO_1_3E);
            put(CM_1_1.getCode(), CM_1_1);
            put(TWO_B_1_0.getCode(), TWO_B_1_0);
            put(TWO_B_1_1.getCode(), TWO_B_1_1);
            put(THREE_B_1_2.getCode(), THREE_B_1_2);
            put(CM_3_1_0.getCode(), CM_3_1_0);
            put(THREE_B_PLUS_1_3.getCode(), THREE_B_PLUS_1_3);
            put(TWO_B_1_2.getCode(), TWO_B_1_2);
            put(TWO_B_1_1E.getCode(), TWO_B_1_1E);
            put(TWO_B_1_2E.getCode(), TWO_B_1_2E);
            put(THREE_B_1_2E.getCode(), THREE_B_1_2E);
            put(CM_3_1_0E.getCode(), CM_3_1_0E);
            put(THREE_B_1_2S.getCode(), THREE_B_1_2S);
            put(THREE_B_1_2ST.getCode(), THREE_B_1_2ST);
            put(THREE_B_1_3E.getCode(), THREE_B_1_3E);
            put(CM_3_1_0_PLUS.getCode(), CM_3_1_0_PLUS);
            put(FOUR_B_1_1_1G.getCode(), FOUR_B_1_1_1G);
            put(FOUR_B_1_1_2G.getCode(), FOUR_B_1_1_2G);
            put(FOUR_B_1_2_2G.getCode(), FOUR_B_1_2_2G);
            put(FOUR_B_1_4_2G.getCode(), FOUR_B_1_4_2G);
            put(FOUR_B_1_1_4G.getCode(), FOUR_B_1_1_4G);
            put(FOUR_B_1_2_4G.getCode(), FOUR_B_1_2_4G);
            put(FOUR_B_1_4_4G.getCode(), FOUR_B_1_4_4G);
            put(FOUR_B_1_4_8G.getCode(), FOUR_B_1_4_8G);
            put(PI_400_4G.getCode(), PI_400_4G);
        }
    };

    private final Properties props;

    public RpiBoardsLoader() throws IOException {
        props = new Properties();
        try (InputStreamReader is = new InputStreamReader(new FileInputStream(CPU_INFO_PATH))) {
            props.load(is);
        }
    }

    @Override
    public boolean accept() {
        String rev = props.getProperty(REV_KEY);
        RpiBoardInfo info = mapping.get(rev);
        return rev != null && info != null && info.getProvider() != null;
    }

    @Override
    public Board create() throws IOException {
        String rev = props.getProperty(REV_KEY);
        RpiBoardInfo info = mapping.get(rev);
        Class<? extends RaspberryPiAbstractBoard> provider = info.getProvider();
        try {
            RaspberryPiAbstractBoard board = provider.getDeclaredConstructor().newInstance();
            board.setBoardInfo(info);
            return board;
        } catch (Exception e) {
            throw new IOException("board provider not implemented for " + info.getModel());
        }
    }
}
