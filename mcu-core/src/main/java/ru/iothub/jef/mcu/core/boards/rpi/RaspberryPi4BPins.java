package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.mcu.core.Cpu;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RaspberryPi4BPins {
    static List<BoardPin> createPins(Cpu cpu) {
        BoardPin[] pins = {
            new BoardPin(1, "3.3v", null),
            new BoardPin(2, "5V", null),
            new BoardPin(3, "GPIO02", cpu.getPin(2)),
            new BoardPin(4, "5V", null),
            new BoardPin(5, "GPIO03", cpu.getPin(3)),
            new BoardPin(6, "Ground", null),
            new BoardPin(7, "GPIO04", cpu.getPin(4)),
            new BoardPin(8, "GPIO14", cpu.getPin(14)),
            new BoardPin(9, "Ground", null),
            new BoardPin(10, "GPI015", cpu.getPin(15)),
            new BoardPin(11, "GPIO17", cpu.getPin(17)),
            new BoardPin(12, "GPIO18", cpu.getPin(18)),
            new BoardPin(13, "GPIO27", cpu.getPin(27)),
            new BoardPin(14, "Ground", null),
            new BoardPin(15, "GPIO22", cpu.getPin(22)),
            new BoardPin(16, "GPIO23", cpu.getPin(23)),
            new BoardPin(17, "3.3v", null),
            new BoardPin(18, "GPIO24", cpu.getPin(24)),
            new BoardPin(19, "GPIO10", cpu.getPin(10)),
            new BoardPin(20, "Ground", null),
            new BoardPin(21, "GPIO09", cpu.getPin(9)),
            new BoardPin(22, "GPIO25", cpu.getPin(25)),
            new BoardPin(23, "GPIO11", cpu.getPin(11)),
            new BoardPin(24, "GPIO08", cpu.getPin(8)),
            new BoardPin(25, "Ground", null),
            new BoardPin(26, "GPIO07", cpu.getPin(7)),
            new BoardPin(27, "GPIO00", cpu.getPin(0)),
            new BoardPin(28, "GPIO01", cpu.getPin(1)),
            new BoardPin(29, "GPIO05", cpu.getPin(5)),
            new BoardPin(30, "Ground", null),
            new BoardPin(31, "GPIO06", cpu.getPin(6)),
            new BoardPin(32, "GPIO12", cpu.getPin(12)),
            new BoardPin(33, "GPIO13", cpu.getPin(13)),
            new BoardPin(34, "Ground", null),
            new BoardPin(35, "GPIO19", cpu.getPin(19)),
            new BoardPin(36, "GPIO16", cpu.getPin(16)),
            new BoardPin(37, "GPIO26", cpu.getPin(26)),
            new BoardPin(38, "GPIO20", cpu.getPin(20)),
            new BoardPin(39, "Ground", null),
            new BoardPin(40, "GPIO21", cpu.getPin(21)),
        };
        return new ArrayList<>(Arrays.asList(pins));
    }
}
