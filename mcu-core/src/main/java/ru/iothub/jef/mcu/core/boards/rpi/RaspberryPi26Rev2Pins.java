 

package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.linux.gpio.GpioManager;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class RaspberryPi26Rev2Pins {
    static List<BoardPin> createPins() throws IOException {
        BoardPin[] pins = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "GPIO02", getPin(2)),
                new BoardPin(4, "5V", null),
                new BoardPin(5, "GPIO03", getPin(3)),
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "GPIO04", getPin(4)),
                new BoardPin(8, "GPIO14", getPin(14)),
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "GPI015", getPin(15)),
                new BoardPin(11, "GPIO17", getPin(17)),
                new BoardPin(12, "GPIO18", getPin(18)),
                new BoardPin(13, "GPIO27", getPin(27)),
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "GPIO22", getPin(22)),
                new BoardPin(16, "GPIO23", getPin(23)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "GPIO24", getPin(24)),
                new BoardPin(19, "GPIO10", getPin(10)),
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "GPIO09", getPin(9)),
                new BoardPin(22, "GPIO25", getPin(25)),
                new BoardPin(23, "GPIO11", getPin(11)),
                new BoardPin(24, "GPIO08", getPin(8)),
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "GPIO07", getPin(7)),
        };
        return Collections.unmodifiableList(Arrays.asList(pins));
    }

    private static GpioPin getPin(int number) throws IOException {
        return GpioManager.getPin("/dev/gpiochip0", number);
    }
}
