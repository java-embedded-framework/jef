package ru.iothub.jef.mcu.core.bcm;

import ru.iothub.jef.mcu.core.CpuPin;
import ru.iothub.jef.mcu.core.CpuPinMode;
import ru.iothub.jef.mcu.core.CpuPinState;

import java.util.HashMap;
import java.util.Map;

import static ru.iothub.jef.mcu.core.CpuPinMode.*;

public abstract class BCMCorePin extends CpuPin {
    // BCM2835 datasheet - page 92
    // Tables 6-2 till 6-7  GPIO Register Assignment
    //
    // BCM2711 Datasheet - page 65
    // GPFSEL0 Register - GPFSEL5 Register
    /*protected final static String[] DEFAULT_PIN_FUNCTIONS = {
            "IN", "OUT", "ALT5", "ALT4", "ALT0", "ALT1", "ALT2", "ALT3"
    };*/

    protected final static int FSEL_INPUT = 0b000;
    protected final static int FSEL_OUTPUT = 0b001;
    protected final static int FSEL_ALT_0 = 0b100;
    protected final static int FSEL_ALT_1 = 0b101;
    protected final static int FSEL_ALT_2 = 0b110;
    protected final static int FSEL_ALT_3 = 0b111;
    protected final static int FSEL_ALT_4 = 0b011;
    protected final static int FSEL_ALT_5 = 0b010;

    private final static Map<CpuPinMode, Integer> FSEL = new HashMap<>() {
        {
            put(INPUT, FSEL_INPUT);
            put(OUTPUT, FSEL_OUTPUT);
            put(ALT_0, FSEL_ALT_0);
            put(ALT_1, FSEL_ALT_1);
            put(ALT_2, FSEL_ALT_2);
            put(ALT_3, FSEL_ALT_3);
            put(ALT_4, FSEL_ALT_4);
            put(ALT_5, FSEL_ALT_5);
        }
    };

    // BCM2835 datasheet - page 89
    // 6.1 Register View
    //
    // BCM2711 Datasheet - page 65
    // 5.2. Register View
    protected final int[] GPFSEL = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
    };

    protected final int[] SHIFT = {
            0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
            0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
            0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
            0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
            0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
            0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
    };

    // GPLEV1-0 Registers. Used for reading gpio pin values.
    // BCM2835 datasheet - page 90
    // GPLEV0 - GPLEV1 Register
    //
    // BCM2711 Datasheet - page 64
    // GPLEV0 - GPLEV1 Register
    // Shift to 13 32 bit to addr 0x34 and 0x38
    private final int[] GPLEV =
            {
                    13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13,
                    14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14,
            };

    // GPSET1-0 Registers. Used for setting gpio pin values.
    // BCM2835 datasheet - page 90
    // GPSET0 - GPSET1 Register
    //
    // BCM2711 Datasheet - page 64
    // GPLEV0 - GPLEV1 Register
    // Shift to 13 32 bit to addr 0x34 and 0x38
    private final static int[] GPSET =
            {
                    7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
                    8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
            };

    // GPCLR1-0 Registers. Used for setting gpio pin values.
    // BCM2835 datasheet - page 90
    // GPCLR0 - GPCLR1 Register
    //
    // BCM2711 Datasheet - page 64
    // GPCLR0 - GPCLR1 Register
    // Shift to 13 32 bit to addr 0x34 and 0x38
    private final static int[] GPCLR =
            {
                    10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                    11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,
            };

    private final BCMCpu cpu;

    protected BCMCorePin(BCMCpu cpu, int number) {
        super(cpu, number);
        this.cpu = cpu;
    }

    protected BCMCpu getCpu() {
        return cpu;
    }

    @Override
    public int getPinFunction() {
        int number = getPinNumber();
        int gpfsel = GPFSEL[number];
        int shift = SHIFT[number];
        int register = getCpu().getGPIORegister(gpfsel);
        return register >> shift & 7;
    }

    @Override
    public CpuPinState digitalRead() {
        int number = getPinNumber();
        int offset = GPLEV[number];
        int a = getCpu().getGPIORegister(offset);
        int b = (1 << (number & 31));
        int c = a & b;
        return c == 0 ? CpuPinState.LOW : CpuPinState.HIGH;
    }

    @Override
    public void digitalWrite(CpuPinState state) {
        int number = getPinNumber();
        int offset = state.equals(CpuPinState.HIGH) ?
                GPSET[number] : GPCLR[number];
        int value = 1 << (number & 31);
        getCpu().setGPIORegister(offset, value);
    }

    @Override
    public void pinMode(CpuPinMode mode) {
        int number = getPinNumber();
        int gpfsel = GPFSEL[number];
        int shift = SHIFT[number];
        synchronized (this) {
            int oldValue = getCpu().getGPIORegister(gpfsel);
            int regValue = FSEL.get(mode);

            int newValue = mode.equals(INPUT) ?
                    oldValue & ~(7 << shift) :
                    oldValue & ~(7 << shift) | (regValue << shift);

            getCpu().setGPIORegister(gpfsel, newValue);
        }
    }
}
