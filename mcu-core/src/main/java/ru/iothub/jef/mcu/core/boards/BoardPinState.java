

package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.linux.gpio.GpioPin;

public enum BoardPinState {
    NOT_SET(-1),
    LOW(0),
    HIGH(1);

    int value;

    BoardPinState(int value) {
        this.value = value;
    }

    public static BoardPinState valueOf(GpioPin.State read) {
        switch (read) {
            case LOW:
                return BoardPinState.LOW;
            case HIGH:
                return BoardPinState.HIGH;
            default:
                return BoardPinState.NOT_SET;
        }
    }

    int getValue() {
        return value;
    }
}
