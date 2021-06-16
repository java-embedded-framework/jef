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

package ru.iothub.jef.mcu.core.boards.sunxi;

// https://linux-sunxi.org/GPIO
/**
 * (position of letter in alphabet - 1) * 32 + pin number
 * E.g for PH18 this would be ( 8 - 1) * 32 + 18 = 224 + 18 = 242 (since 'h' is the 8th letter).
 */
public class BallToPin {
    public static int convert(char c, int number) {
        int multiplier;
        switch (c) {
            case 'A':
            case 'a':
                multiplier = 0;
                break;
            case 'B':
            case 'b':
                multiplier = 1;
                break;
            case 'C':
            case 'c':
                multiplier = 2;
                break;
            case 'D':
            case 'd':
                multiplier = 3;
                break;
            case 'E':
            case 'e':
                multiplier = 4;
                break;
            case 'F':
            case 'f':
                multiplier = 5;
                break;
            case 'G':
            case 'g':
                multiplier = 6;
                break;
            case 'H':
            case 'h':
                multiplier = 7;
                break;
            case 'I':
            case 'i':
                multiplier = 8;
                break;
            case 'J':
            case 'j':
                multiplier = 9;
                break;
            case 'K':
            case 'k':
                multiplier = 10;
                break;
            case 'L':
            case 'l':
                multiplier = 11;
                break;
            default:
                throw new RuntimeException("Invalid pin letter '"+c+"'");
        }
        return multiplier * 32 + number;
    }
}
