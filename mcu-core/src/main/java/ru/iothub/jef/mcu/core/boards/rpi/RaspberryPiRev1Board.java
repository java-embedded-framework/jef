 

package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.linux.spi.SpiMode;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RaspberryPiRev1Board extends RaspberryPiAbstractBoard {
    public RaspberryPiRev1Board() throws IOException {
        super();
    }

    @Override
    protected List<I2CBus> initI2C() throws IOException {
        final List<I2CBus> i2cs = new ArrayList<>();
        if (new File("/dev/i2c-1").exists()) {
            i2cs.add(
                    new I2CBus("/dev/i2c-1")
            );
        }
        return Collections.unmodifiableList(i2cs);
    }

    @Override
    protected List<SpiBus> initSPI() throws IOException {
        List<SpiBus> ss = new ArrayList<>();
        if (new File("/dev/spidev0.0").exists()) {
            ss.add(
                    new SpiBus("/dev/spidev0.0", 500000, SpiMode.SPI_MODE_3, 8, 0)
            );
        }
        return Collections.unmodifiableList(ss);
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        return RaspberryPi26Rev1Pins.createPins();
    }
}
