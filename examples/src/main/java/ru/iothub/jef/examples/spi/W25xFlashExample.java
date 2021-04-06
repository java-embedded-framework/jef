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

package ru.iothub.jef.examples.spi;

import ru.iothub.jef.devices.library.winbond.w25x.W25xFlash;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.linux.spi.SpiMode;

import java.nio.ByteBuffer;

import static ru.iothub.jef.linux.core.LinuxUtils.dump;

public class W25xFlashExample implements Example {
    @Override
    public String getName() {
        return "Winbound W25x Flash Example";
    }

    @Override
    public void init() throws Exception {
        System.out.println("Please connect W25x to SPI-1 and press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        SpiBus bus0 = new SpiBus(0, 500000, SpiMode.SPI_MODE_3, 8, 0);
        W25xFlash flash = new W25xFlash(bus0);

        int manufacturerID = flash.getManufacturerID();
        int memoryType = flash.getMemoryType();
        int capacity = flash.getCapacity();
        String chipID = flash.getChipID();
        boolean busy = flash.isBusy();
        System.out.println("manufacturerID = 0x" + Integer.toHexString(manufacturerID));
        System.out.println("memoryType = 0x" + Integer.toHexString(memoryType));
        System.out.println("capacity = 0x" + Integer.toHexString(capacity));
        System.out.println("chipID = " + chipID);
        System.out.println("busy = " + busy);

        System.out.println("Erase flash");
        flash.eraseAll();

        ByteBuffer read;
        byte[] write = new byte[]{100, 101, 102, 103, 104, 105, 105, 104, 103, 102, 101, 100};

        read = flash.read(1, write.length);
        dump("read empty flash", read);

        System.out.println();
        System.out.println("Write 12 bytes");

        flash.pageWrite(0, 1, write);

        System.out.println();
        System.out.println("Read 12 bytes");
        read = flash.read(1, write.length);
        dump("read", read);

        System.out.println();
        System.out.println("Fast Read 12 bytes");

        read = flash.fastRead(1, write.length);
        dump("fastread", read);

        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Show W25x read/write functions");
    }}
