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

package ru.iothub.jef.examples.i2c;

import ru.iothub.jef.devices.library.bosch.bcm280.*;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;

import java.io.IOException;
import java.util.List;

public class BCM280Example implements Example {
    private static void setConfiguration(BMP280 bmp280) throws IOException {
        System.out.println("Update configuration to different mode");
        // Set configuration
        bmp280.setPowerMode(PowerMode.BMP280_NORMAL_MODE);
        bmp280.setTemperatureOversampling(TemperatureOversampling.ULTRA_HIGH);
        bmp280.setPressureOversampling(PressureOversampling.ULTRA_HIGH);
        bmp280.setIrrFilter(IrrFilter.COEFF_16);
        bmp280.setStandbyTime(StandbyTime.STANDBY_TIME_250_MS);
    }

    private static void showConfiguration(BMP280 bmp280) throws IOException {
        // check configuration
        System.out.println("***********************************************************");
        System.out.println("PowerMode :" + bmp280.getPowerMode());
        System.out.println("PressureOversampling :" + bmp280.getPressureOversampling());
        System.out.println("TemperatureOversampling :" + bmp280.getTemperatureOversampling());
        System.out.println("IrrFilter :" + bmp280.getIrrFilter());
        System.out.println("StandbyTime :" + bmp280.getStandbyTime());
        System.out.println("Spi3w :" + bmp280.isSpi3wEnabled());
        System.out.println("MeasuringStatus :" + bmp280.getMeasuringStatus());
        System.out.println("ImUpdateStatus :" + bmp280.getImUpdateStatus());
    }

    @Override
    public String getName() {
        return "Bosch BCM280 Example";
    }

    @Override
    public void init() throws Exception {
        System.out.println("Please connect BCM280 to I2C-1 and press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        Board board = BoardManager.getBoard();
        List<I2CBus> i2CBuses = board.getI2CBuses();
        if(i2CBuses.size() == 0) {
            throw new IOException("I2C buses not enabled");
        }

        I2CBus bus = i2CBuses.get(0);
        BMP280 bmp280 = new BMP280(bus, I2CAddress.I2C_ADDRESS1);
        int chipId = bmp280.getChipId();

        System.out.println("chipId = 0x" + Integer.toHexString(chipId));

        showConfiguration(bmp280);
        setConfiguration(bmp280);
        showConfiguration(bmp280);

        System.out.println();
        for (int i = 0; i < 10; i++) {
            BMP280.BMP280Data bmp280Data = bmp280.getBMP280Data();
            System.out.printf("pressure   : %.2f hPa\n", bmp280Data.getPressure());
            System.out.printf("temperature: %.2f °C\n", bmp280Data.getTemperatureCelsius());
            System.out.printf("altitude   : %.2f m\n\n", bmp280Data.getAltitude());
            System.out.println();
            Thread.sleep(1000);
        }
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Show Bosch BMP280 functions 10 times");
    }
}
