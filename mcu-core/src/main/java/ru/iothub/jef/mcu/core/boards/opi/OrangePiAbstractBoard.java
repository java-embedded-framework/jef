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

import ru.iothub.jef.linux.gpio.GpioManager;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.linux.spi.SpiMode;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardPin;
import ru.iothub.jef.mcu.core.boards.sunxi.BallToPin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class OrangePiAbstractBoard extends Board {
    private OrangePiBoardInfo info;
    private List<SpiBus> spis;
    private List<I2CBus> i2cs;
    private List<BoardPin> pins;

    public OrangePiAbstractBoard() throws IOException {
        setSpi(initSPI());
        setI2cs(initI2C());
        setPins(initGPIO());
    }

    protected abstract List<BoardPin> initGPIO() throws IOException;

    protected List<I2CBus> initI2C() throws IOException {
        final List<I2CBus> i2cs = new ArrayList<>();

        if (new File("/dev/i2c-2").exists()) {
            i2cs.add(
                    new I2CBus("/dev/i2c-2")
            );
        }
        return Collections.unmodifiableList(i2cs);
    }

    protected List<SpiBus> initSPI() throws IOException {
        List<SpiBus> ss = new ArrayList<>();
        if (new File("/dev/spidev0.0").exists()) {
            ss.add(
                    new SpiBus("/dev/spidev0.0", 500000, SpiMode.SPI_MODE_3, 8, 0)
            );
        }
        return Collections.unmodifiableList(ss);
    }

    protected void setSpi(List<SpiBus> buses) {
        this.spis = buses;
    }

    protected void setI2cs(List<I2CBus> buses) {
        this.i2cs = buses;
    }

    protected void setPins(List<BoardPin> pins) {
        this.pins = pins;
    }

    @Override
    public int getPinCount() {
        return pins.size();
    }

    @Override
    public BoardPin getPin(int index) {
        return pins.get(index - 1);
    }

    @Override
    public List<BoardPin> getPins() {
        return pins;
    }

    @Override
    public List<SpiBus> getSpiBuses() {
        return spis;
    }

    @Override
    public List<I2CBus> getI2CBuses() {
        return i2cs;
    }

    public void setBoardInfo(OrangePiBoardInfo info) {
        this.info = info;
    }

    @Override
    public String getBoardInfo() {
        return info.getName() + " " + info.getDescription();
    }

    protected GpioPin getBoardPin(int number) throws IOException {
        return GpioManager.getPin("/dev/gpiochip0", number);
    }

    protected GpioPin getBoardPin(char c, int number) throws IOException {
        return getBoardPin(BallToPin.convert(c, number));
    }

}
