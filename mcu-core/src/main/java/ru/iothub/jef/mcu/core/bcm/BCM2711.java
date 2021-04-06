package ru.iothub.jef.mcu.core.bcm;

import ru.iothub.jef.mcu.core.CpuPin;

import java.io.IOException;
import java.util.List;

// datasheet https://datasheets.raspberrypi.org/bcm2711/bcm2711-peripherals.pdf
public class BCM2711 extends BCMCpu {
    private final static long GPIO_PERI_BASE_2711 = Integer.toUnsignedLong(0xFE000000);

    private final List<BCM2711Pin> pins;

    public BCM2711() throws IOException {
        super();
        pins = BCM2711Pins.createPins(this);
    }

    @Override
    public int getPinsCount() {
        return pins.size();
    }

    @Override
    public CpuPin getPin(int index) {
        return pins.get(index);
    }


    @Override
    public long getCpuBaseMemoryOffset() {
        return GPIO_PERI_BASE_2711;
    }
}
