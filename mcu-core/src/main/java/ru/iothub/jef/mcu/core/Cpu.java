package ru.iothub.jef.mcu.core;

import java.io.IOException;


public abstract class Cpu {
    public Cpu() throws IOException {
        initCpu();
    }

    public abstract int getPinsCount();

    public abstract long getClockSpeed();

    public abstract CpuTimer getTimer();

    public abstract CpuClock getClock();

    public abstract CpuPin getPin(int index);

    protected abstract void initCpu() throws IOException;
}
