

package ru.iothub.jef.mcu.core.boards.rpi;

import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.List;

abstract class RaspberryPiAbstractBoard extends Board {
    private List<SpiBus> spis;
    private List<I2CBus> i2cs;
    private List<BoardPin> pins;
    private RpiBoardInfo info;

    public RaspberryPiAbstractBoard()  throws IOException {
        setSpi(initSPI());
        setI2cs(initI2C());
        setPins(initGPIO());
    }

    protected abstract List<BoardPin> initGPIO() throws IOException;

    protected abstract List<I2CBus> initI2C() throws IOException;

    protected abstract List<SpiBus> initSPI() throws IOException;

    protected void setSpi(List<SpiBus> buses) {
        this.spis = buses;
    }

    protected void setI2cs(List<I2CBus> buses) {
        this.i2cs = buses;
    }

    protected void setPins(List<BoardPin> pins) {
        this.pins = pins;
    }

    @Override
    public int getPinCount() {
        return pins.size();
    }

    @Override
    public BoardPin getPin(int index) {
        return pins.get(index - 1);
    }

    @Override
    public List<BoardPin> getPins() {
        return pins;
    }

    @Override
    public List<SpiBus> getSpiBuses() {
        return spis;
    }

    @Override
    public List<I2CBus> getI2CBuses() {
        return i2cs;
    }

    public void setBoardInfo(RpiBoardInfo info) {
        this.info = info;
    }

    @Override
    public String getBoardInfo() {
        return "Raspberry Pi " + info.getModel() + " " + info.getRevision() + " " + info.getRam();
    }
}
