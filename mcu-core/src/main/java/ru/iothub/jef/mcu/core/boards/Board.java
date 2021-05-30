package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.linux.spi.SpiBus;

import java.util.List;

@SuppressWarnings("unused")
public abstract class Board {
    public abstract String getBoardInfo();

    public abstract int getPinCount();

    public abstract BoardPin getPin(int index);

    public abstract List<BoardPin> getPins();

    public abstract List<SpiBus> getSpiBuses();

    public abstract List<I2CBus> getI2CBuses();
}
