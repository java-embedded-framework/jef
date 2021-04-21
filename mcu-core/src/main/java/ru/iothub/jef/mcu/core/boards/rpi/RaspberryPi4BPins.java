package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.linux.gpio.PinSet;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RaspberryPi4BPins {
    static List<BoardPin> createPins(PinSet set) {
        BoardPin[] pins = {
            new BoardPin(1, "3.3v", null),
            new BoardPin(2, "5V", null),
            new BoardPin(3, "GPIO02", set.getPin(2)),
            new BoardPin(4, "5V", null),
            new BoardPin(5, "GPIO03", set.getPin(3)),
            new BoardPin(6, "Ground", null),
            new BoardPin(7, "GPIO04", set.getPin(4)),
            new BoardPin(8, "GPIO14", set.getPin(14)),
            new BoardPin(9, "Ground", null),
            new BoardPin(10, "GPI015", set.getPin(15)),
            new BoardPin(11, "GPIO17", set.getPin(17)),
            new BoardPin(12, "GPIO18", set.getPin(18)),
            new BoardPin(13, "GPIO27", set.getPin(27)),
            new BoardPin(14, "Ground", null),
            new BoardPin(15, "GPIO22", set.getPin(22)),
            new BoardPin(16, "GPIO23", set.getPin(23)),
            new BoardPin(17, "3.3v", null),
            new BoardPin(18, "GPIO24", set.getPin(24)),
            new BoardPin(19, "GPIO10", set.getPin(10)),
            new BoardPin(20, "Ground", null),
            new BoardPin(21, "GPIO09", set.getPin(9)),
            new BoardPin(22, "GPIO25", set.getPin(25)),
            new BoardPin(23, "GPIO11", set.getPin(11)),
            new BoardPin(24, "GPIO08", set.getPin(8)),
            new BoardPin(25, "Ground", null),
            new BoardPin(26, "GPIO07", set.getPin(7)),
            new BoardPin(27, "GPIO00", set.getPin(0)),
            new BoardPin(28, "GPIO01", set.getPin(1)),
            new BoardPin(29, "GPIO05", set.getPin(5)),
            new BoardPin(30, "Ground", null),
            new BoardPin(31, "GPIO06", set.getPin(6)),
            new BoardPin(32, "GPIO12", set.getPin(12)),
            new BoardPin(33, "GPIO13", set.getPin(13)),
            new BoardPin(34, "Ground", null),
            new BoardPin(35, "GPIO19", set.getPin(19)),
            new BoardPin(36, "GPIO16", set.getPin(16)),
            new BoardPin(37, "GPIO26", set.getPin(26)),
            new BoardPin(38, "GPIO20", set.getPin(20)),
            new BoardPin(39, "Ground", null),
            new BoardPin(40, "GPIO21", set.getPin(21)),
        };
        return new ArrayList<>(Arrays.asList(pins));
    }
}
