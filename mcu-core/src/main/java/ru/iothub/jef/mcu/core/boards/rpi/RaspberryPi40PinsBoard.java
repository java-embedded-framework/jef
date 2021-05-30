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

class RaspberryPi40PinsBoard extends RaspberryPiAbstractBoard {
    public RaspberryPi40PinsBoard() throws IOException {
        super();
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        return RaspberryPi40Pins.createPins();
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

    protected List<SpiBus> initSPI() throws IOException {
        List<SpiBus> ss = new ArrayList<>();
        if (new File("/dev/spidev0.0").exists()) {
            ss.add(
                    new SpiBus("/dev/spidev0.0", 500000, SpiMode.SPI_MODE_3, 8, 0)
            );
        }

        if (new File("/dev/spidev0.1").exists()) {
            ss.add(
                    new SpiBus("/dev/spidev0.1", 500000, SpiMode.SPI_MODE_3, 8, 0)
            );
        }

        return Collections.unmodifiableList(ss);
    }
}
