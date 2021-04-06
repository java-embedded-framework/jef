package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.mcu.core.Cpu;

public abstract class Board {
    public abstract int getPinCount();

    public abstract BoardPin getPin(int index);

    public abstract Cpu getCpu();
}
