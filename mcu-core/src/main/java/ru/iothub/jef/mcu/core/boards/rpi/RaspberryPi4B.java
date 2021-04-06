package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.mcu.core.Cpu;
import ru.iothub.jef.mcu.core.CpuTimer;
import ru.iothub.jef.mcu.core.bcm.BCM2711;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.List;

public class RaspberryPi4B extends Board {
    private final BCM2711 cpu;
    private final List<BoardPin> pins;

    public RaspberryPi4B() throws IOException {
        cpu = new BCM2711();
        pins = RaspberryPi4BPins.createPins(cpu);
    }

    @Override
    public Cpu getCpu() {
        return cpu;
    }

    public CpuTimer getTimer() {
        return cpu.getTimer();
    }

    @Override
    public int getPinCount() {
        return pins.size();
    }

    @Override
    public BoardPin getPin(int index) {
        return pins.get(index-1);
    }
}
