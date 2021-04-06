package ru.iothub.jef.mcu.core.bcm;

import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Mman;
import ru.iothub.jef.mcu.core.Cpu;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.EnumSet;

import static ru.iothub.jef.linux.core.Mman.MemoryFlag.MAP_SHARED;
import static ru.iothub.jef.linux.core.Mman.MemoryProtection.*;

// datasheet https://www.raspberrypi.org/app/uploads/2012/02/BCM2835-ARM-Peripherals.pdf
public abstract class BCMCpu extends Cpu {
    private final static String MEM_PATH = "/dev/mem";
    private final static String ACCESS_MODE = "rws";

    private final static int BLOCK_SIZE = 4 * 1024;

    private final long BCM_CORE_CLK_HZ = 250000000	/*!< 250 MHz */;

    private final static long GPIO_BASE_OFFSET = Integer.toUnsignedLong(0x00200000);
    private final static long GPIO_CLOCK_BASE_OFFSET = Integer.toUnsignedLong(0x00101000);
    private final static long GPIO_PADS_OFFSET = Integer.toUnsignedLong(0x00100000);
    private final static long GPIO_TIMER_OFFSET = Integer.toUnsignedLong(0x0000B000);
    private final static long GPIO_PWM_OFFSET = Integer.toUnsignedLong(0x0020C000);

    private IntBuffer gpioMemory;
    private BCMTimer timer;
    private BCMCpuClock clock;
    private FileHandle fd;

    public abstract long getCpuBaseMemoryOffset();

    public BCMCpu() throws IOException {
        super();
    }

    @Override
    public void initCpu() throws IOException {
        initMemory();
    }

    protected void initMemory() throws IOException {
        Fcntl io = Fcntl.getInstance();
        Mman mem = Mman.getInstance();

        fd = io.open(MEM_PATH, EnumSet.of(IOFlags.O_RDWR, IOFlags.O_SYNC));

        long cpuBaseMemoryOffset = getCpuBaseMemoryOffset();
        long memoryOffset = cpuBaseMemoryOffset + getGpioBaseOffset();

        gpioMemory = mem.mmap(fd, PROT_READ_WRITE, MAP_SHARED, memoryOffset, BLOCK_SIZE )
                .asIntBuffer();

        timer = new BCMTimer(fd, cpuBaseMemoryOffset + getGpioTimerOffset());
        clock = new BCMCpuClock(fd, cpuBaseMemoryOffset + getGpioClockBaseOffset());
    }

    protected long getGpioTimerOffset() {
        return GPIO_TIMER_OFFSET;
    }

    protected long getGpioClockBaseOffset() {
        return GPIO_CLOCK_BASE_OFFSET;
    }

    protected long getGpioBaseOffset() {
        return GPIO_BASE_OFFSET;
    }

    public BCMTimer getTimer() {
        return timer;
    }

    @Override
    public BCMCpuClock getClock() {
        return clock;
    }

    @Override
    public long getClockSpeed() {
        return BCM_CORE_CLK_HZ;
    }

    protected IntBuffer getGpioMemory() {
        return gpioMemory;
    }

    protected IntBuffer getMemoryByOffset(int offset, int length) throws IOException {
        final long addr = getCpuBaseMemoryOffset() + offset;
        return Mman.getInstance().mmap(fd, PROT_READ_WRITE, MAP_SHARED, addr, length)
                .asIntBuffer();
    }

    public IntBuffer getMemoryByOffset(int offset) throws IOException {
        return getMemoryByOffset(offset, BLOCK_SIZE);
    }

    protected int getGPIORegister(int offset) {
        return getGpioMemory().get(offset);
    }

    protected void setGPIORegister(int offset, int value) {
        getGpioMemory().put(offset, value);
    }

}
