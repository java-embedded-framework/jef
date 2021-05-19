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

package ru.iothub.jef.linux.gpio;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class GpioManager {
    private final static Map<String, GpioPin> pinsets = new HashMap<>();

    public static GpioPin getPin(String path, int number) throws IOException {
        synchronized (GpioManager.class) {
            GpioPin pin;
            String key = getKey(path, number);
            if ((pin = getCachedKey(key)) != null) {
                return pin;
            }
            pin = new GpioPin(key, path, number);
            pinsets.put(key, pin);
            return pin;
        }
        //return getPin(path, number, null);
    }

    public static boolean isUsed(String path, int number) {
        synchronized (GpioManager.class) {
            return getCachedKey(getKey(path, number)) != null;
        }
    }

    /*private static GpioPin getPin(String path, int number, GpioPin.Direction direction) throws IOException {
        synchronized (GpioManager.class) {
            GpioPin pin;
            String key = getKey(path, number);
            if (direction != null && (pin = getCachedKey(key)) != null) {
                if (pin.getDirection() != direction) {
                    pin.setDirection(direction);
                }
                return pin;
            }
            pin = direction == null ?
                    new GpioPin(key, path, number) :
                    new GpioPin(key, path, number, direction);
            pinsets.put(key, pin);
            return pin;
        }
    }*/

    private static String getKey(String path, int number) {
        return path + "-" + number;
    }

    private static GpioPin getCachedKey(String cacheKey) {
        return pinsets.get(cacheKey);
    }

    static void closePin(String key) {
        synchronized (GpioManager.class) {
            pinsets.remove(key);
        }
    }
}
