package ru.iothub.jef.mcu.core;

import java.util.List;

public abstract class CpuPin {
    private final Cpu cpu;
    private final int number;


    protected CpuPin(Cpu cpu, int number) {
        this.cpu = cpu;
        this.number = number;
    }

    public abstract int getPinFunction();

    public abstract String getPinFunctionName();

    public abstract List<String> getPingFunctionNames();

    public abstract CpuPinState digitalRead();

    public int getPinNumber() {
        return number;
    }

    public abstract void digitalWrite(CpuPinState state);

    public abstract void pinMode(CpuPinMode mode);
}
