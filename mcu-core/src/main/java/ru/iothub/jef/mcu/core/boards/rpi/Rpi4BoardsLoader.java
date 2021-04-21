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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// https://elinux.org/RPi_HardwareHistory
public class Rpi4BoardsLoader implements BoardLoader {
    private final static String HW_KEY = "Hardware";
    private final static String HW_VALUE = "BCM2835";
    private final static String REV_KEY = "Revision";

    private final static List<String> REV_VALUES = new ArrayList<>() {
        {
            add("a03111");
            add("b03111");
            add("b03112");
            add("b03114");
            add("c03111");
            add("c03112");
            add("c03114");
            add("d03114");
        }
    };

    @Override
    public boolean acceptCpuInfo(Properties props) {
        String hw = props.getProperty(HW_KEY);
        if (!HW_VALUE.equals(hw)) {
            return false;
        }
        String rev = props.getProperty(REV_KEY);
        return rev != null && REV_VALUES.contains(rev);
    }

    @Override
    public Board create() throws IOException {
        return new RaspberryPi4B();
    }
}
