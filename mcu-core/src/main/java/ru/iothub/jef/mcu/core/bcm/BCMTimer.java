package ru.iothub.jef.mcu.core.bcm;

import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Mman;
import ru.iothub.jef.mcu.core.CpuTimer;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.IntBuffer;

import static ru.iothub.jef.linux.core.Mman.MemoryFlag.MAP_SHARED;
import static ru.iothub.jef.linux.core.Mman.MemoryProtection.*;

public class BCMTimer extends CpuTimer {
    // Timer
    // BCM2835 page 197
    // BCM2711 page 157
    private final static int TIMER_LOAD = (0x400 >> 2);
    private final static int TIMER_VALUE = (0x404 >> 2);
    private final static int TIMER_CONTROL = (0x408 >> 2);
    private final static int TIMER_IRQ_CLR = (0x40C >> 2);
    private final static int TIMER_IRQ_RAW = (0x410 >> 2);
    private final static int TIMER_IRQ_MASK = (0x414 >> 2);
    private final static int TIMER_RELOAD = (0x418 >> 2);
    private final static int TIMER_PRE_DIV = (0x41C >> 2);
    private final static int TIMER_COUNTER = (0x420 >> 2);

    private final static int BLOCK_SIZE = 1060;

    private final IntBuffer map;

    public BCMTimer(FileHandle fd, long memOffset) throws IOException {
        map = Mman.getInstance().mmap(fd, PROT_READ_WRITE, MAP_SHARED, memOffset, BLOCK_SIZE)
                .asIntBuffer();
    }

    public long getCurrentSpeed() {
        return Integer.toUnsignedLong(
                map.get(TIMER_VALUE)
        );
    }

    public void dumb(PrintStream ps) {
        dumpRegister(ps, "TIMER_LOAD", TIMER_LOAD);
        dumpRegister(ps, "TIMER_VALUE", TIMER_VALUE);
        dumpRegister(ps, "TIMER_CONTROL", TIMER_CONTROL);
        dumpRegister(ps, "TIMER_IRQ_CLR", TIMER_IRQ_CLR);
        dumpRegister(ps, "TIMER_IRQ_RAW", TIMER_IRQ_RAW);
        dumpRegister(ps, "TIMER_IRQ_MASK", TIMER_IRQ_MASK);
        dumpRegister(ps, "TIMER_RELOAD", TIMER_RELOAD);
        dumpRegister(ps, "TIMER_PRE_DIV", TIMER_PRE_DIV);
        dumpRegister(ps, "TIMER_COUNTER", TIMER_COUNTER);
    }

    public void dump() {
        dumb(System.out);
    }

    private void dumpRegister(PrintStream ps, String name, int reg) {
        int value = map.get(reg);
        ps.println("Register['" + name + "']=0x" + Integer.toHexString(value) + "(" + value + ")");
    }
}
