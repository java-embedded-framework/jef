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

import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OrangePiBoardsLoader implements BoardLoader {
    private final static String BOARD_NAME_KEY = "BOARD_NAME";
    private final static String BOARD_FAMILY_KEY = "BOARDFAMILY";
    private final static String ARMBIAN_RELEASE = "/etc/armbian-release";


    private final static Map<String, OrangePiBoardInfo> mapping = new HashMap<String, OrangePiBoardInfo>() {
        {
            putValue(OrangePiBoardInfo.ORANGE_PI);
            putValue(OrangePiBoardInfo.ORANGE_PI_R1);
            putValue(OrangePiBoardInfo.ORANGE_PI_R1_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_RK3399);
            putValue(OrangePiBoardInfo.ORANGE_PI_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_3);
            putValue(OrangePiBoardInfo.ORANGE_PI_4);
            putValue(OrangePiBoardInfo.ORANGE_PI_LITE);
            putValue(OrangePiBoardInfo.ORANGE_PI_LITE_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_MINI);
            putValue(OrangePiBoardInfo.ORANGE_PI_ONE);
            putValue(OrangePiBoardInfo.ORANGE_PI_ONE_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_PC);
            putValue(OrangePiBoardInfo.ORANGE_PI_PC_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_PC_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_PLUS_2E);
            putValue(OrangePiBoardInfo.ORANGE_PI_PRIME);
            putValue(OrangePiBoardInfo.ORANGE_PI_WIN);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_PLUS_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_PLUS_2A);
        }

        private void putValue(OrangePiBoardInfo orangePi) {
            put(key(orangePi.getName(), orangePi.getFamily()), orangePi);
        }
    };

    private final Properties props;

    public OrangePiBoardsLoader() throws IOException {
        props = new Properties();
        try (InputStreamReader is = new InputStreamReader(new FileInputStream(ARMBIAN_RELEASE))) {
            props.load(is);
        }
    }

    @Override
    public boolean accept() {
        String key = key(props.getProperty(BOARD_NAME_KEY), props.getProperty(BOARD_FAMILY_KEY));
        return mapping.containsKey(key);
    }

    @Override
    public Board create() throws IOException {
        String key = key(props.getProperty(BOARD_NAME_KEY), props.getProperty(BOARD_FAMILY_KEY));
        OrangePiBoardInfo info = mapping.get(key);
        Class<? extends OrangePiAbstractBoard> provider = info.getProvider();
        try {
            OrangePiAbstractBoard board = provider.getDeclaredConstructor().newInstance();
            board.setBoardInfo(info);
            return board;
        } catch (Exception e) {
            throw new IOException("board provider not implemented for " + info.getName());
        }
    }

    private static String key(String name, String family) {
        return filter(name) + "-" + filter(family);
    }

    private static String filter(String s) {
        if(s.startsWith("\"")) {
            s = s.substring(1);
        }
        if(s.endsWith("\"")) {
            s = s.substring(0, s.length()-1);
        }
        return s;
    }
}
