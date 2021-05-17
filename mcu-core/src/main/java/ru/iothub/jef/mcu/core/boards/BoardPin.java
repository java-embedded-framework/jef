package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.linux.gpio.GpioPin;

import java.io.IOException;

// Wrapper for CPU Pin
public class BoardPin {
    private final int number;
    private final String name;
    private final GpioPin pin;

    public BoardPin(int number, String name, GpioPin cpuPin) {
        this.number = number;
        this.name = name;
        this.pin = cpuPin;
    }

    public int getPinNumber() {
        return number;
    }

    public int getCpuPinNumber() {
        return isDummyPin() ? -1 : pin.getPinNumber();
    }

    public String getName() {
        return name;
    }

    public GpioPin getPin() {
        return pin;
    }

    public String getPinFunctionName() {
        return isDummyPin() ?
                name : pin.getName();
    }

    private boolean isDummyPin() {
        return pin == null;
    }

    public BoardPinState digitalRead() {
        try {
            return pin == null ? BoardPinState.NOT_SET : BoardPinState.valueOf(getPin().read());
        } catch (IOException e) {
            return BoardPinState.NOT_SET;
        }
    }

    public void digitalWrite(BoardPinState value) {
        if (!isDummyPin()) {
            try {
                pin.write(GpioPin.State.valueOf(value.getValue()));
            } catch (IOException ignored) {
            }
        }
    }

    public void pinMode(GpioPin.Direction mode) throws IOException {
        if (!isDummyPin()) {
            pin.setDirection(mode);
        }
    }
}
