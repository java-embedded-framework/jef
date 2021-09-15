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

package ru.iothub.jef.devices.library.rakwireless.rak3172;

public class JoinLoRaWANParameters {
    private JoinMode mode; // param 1
    private JoinConfig config; // param 2
    private byte attemptInterval = 7; // param 3 - 7 - 255 seconds (8 is default)
    private byte numberOfAttempts = 0; // 0 - 255 (0 is default)

    public JoinLoRaWANParameters(String obj) {
        String[] split = obj.split(":");

        mode = Integer.parseInt(split[0]) == 0 ? JoinMode.STOP_JOINING : JoinMode.JOIN_NETWORK;
        config = Integer.parseInt(split[1]) == 0 ? JoinConfig.NO_AUTO_JOIN: JoinConfig.AUTO_JOIN_ON_POWER_UP;
        attemptInterval = Byte.parseByte(split[2]);
        numberOfAttempts = Byte.parseByte(split[3]);
    }

    public JoinMode getMode() {
        return mode;
    }

    public void setMode(JoinMode mode) {
        this.mode = mode;
    }

    public JoinConfig getConfig() {
        return config;
    }

    public void setConfig(JoinConfig config) {
        this.config = config;
    }

    public byte getAttemptInterval() {
        return attemptInterval;
    }

    public void setAttemptInterval(byte attemptInterval) {
        if (attemptInterval < 7) {
            attemptInterval = 7;
        }

        this.attemptInterval = attemptInterval;
    }

    public byte getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(byte numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public String asString() {
        return mode.value + ":" + config.value + ":" + attemptInterval + ":" + numberOfAttempts;
    }

    public enum JoinMode {
        STOP_JOINING(0),
        JOIN_NETWORK(1);

        final int value;

        JoinMode(int value) {
            this.value = value;
        }
    }

    public enum JoinConfig {
        NO_AUTO_JOIN(0),
        AUTO_JOIN_ON_POWER_UP(1);

        final int value;

        JoinConfig(int value) {
            this.value = value;
        }
    }
}
