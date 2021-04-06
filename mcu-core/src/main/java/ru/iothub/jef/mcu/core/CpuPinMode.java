package ru.iothub.jef.mcu.core;

public enum CpuPinMode {
    // 000 = GPIO Pin 9 is an input
    //001 = GPIO Pin 9 is an output
    //100 = GPIO Pin 9 takes alternate function 0
    //101 = GPIO Pin 9 takes alternate function 1
    //110 = GPIO Pin 9 takes alternate function 2
    //111 = GPIO Pin 9 takes alternate function 3
    //011 = GPIO Pin 9 takes alternate function 4
    //010 = GPIO Pin 9 takes alternate function 5
    INPUT(0b000),
    OUTPUT(0b001),
    ALT_0(0b100),
    ALT_1(0b101),
    ALT_2(0b110),
    ALT_3(0b111),
    ALT_4(0b011),
    ALT_5(0b010);

    private final int value;

    CpuPinMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
