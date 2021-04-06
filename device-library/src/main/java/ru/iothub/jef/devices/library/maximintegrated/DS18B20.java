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

package ru.iothub.jef.devices.library.maximintegrated;

import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.io.FileHandle;

import java.io.IOException;

/**
 * Provides ablility to Maxis Integrated DS18B20 sensor
 * Datasheet available here - https://datasheets.maximintegrated.com/en/ds/DS18B20.pdf
 *
 */
@SuppressWarnings("unused")
public class DS18B20 {
    private final String name;
    private final Fcntl fcntl;
    private final byte[] buffer;

    /**
     * Allocate new instance of DS18B20 sensor
     * @param path path to sensor in Linux file system
     *             like {@code '/sys/bus/w1/devices/28-&#42;/w1_slave'}
     */
    public DS18B20(String path) {
        name = path;
        fcntl = Fcntl.getInstance();
        buffer = new byte[255];
    }

    /**
     * Gets templerature in celsius
     * @return temperature
     * @throws IOException if i2c bus not allow this operation
     */
    public double getTemperatureCelsius() throws IOException {
        synchronized (this) {
            try(FileHandle handle = fcntl.open(name, IOFlags.O_RDONLY)) {
                int length = fcntl.read(handle, buffer, buffer.length);
                String s = new String(buffer, 0, length);

                if (!s.contains("YES")) {
                    throw new IOException("Invalid CRC for '" + name + "'");
                }

                int idx = s.indexOf("t=");
                if (idx == -1) {
                    throw new IOException("Invalid temperature format data for '" + name + "'");
                }

                String substring = s.substring(idx + 2);
                return Double.parseDouble(substring) / 1000;
            }
        }
    }

    /**
     * Gets fahrenheit in celsius
     * @return temperature
     * @throws IOException if i2c bus not allow this operation
     */
    public double getTemperatureFahrenheit() throws IOException {
        return getTemperatureCelsius() * 1.8 + 32;
    }
}
