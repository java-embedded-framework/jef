

package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.List;

class RaspberryPiRev2Board extends RaspberryPiRev1Board {
    public RaspberryPiRev2Board() throws IOException {
        super();
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        return RaspberryPi26Rev2Pins.createPins();
    }
}
