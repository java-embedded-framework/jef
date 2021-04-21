package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.linux.gpio.Pin;

import java.io.IOException;

// Wrapper for CPU Pin
public class BoardPin {
    private final int number;
    private final String name;
    private final Pin pin;

    public BoardPin(int number, String name, Pin cpuPin) {
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

    public Pin getPin() {
        return pin;
    }

    public String getPinFunctionName() {
        return isDummyPin() ?
                name : pin.getName();
    }

    private boolean isDummyPin() {
        return pin == null;
    }

    public Pin.State digitalRead()  {
        try {
            return pin == null ? Pin.State.LOCKED : getPin().getState();
        } catch (IOException e) {
            return Pin.State.LOCKED;
        }
    }

    public void digitalWrite(Pin.State value) {
        if(!isDummyPin() && !pin.isLocked()) {
            try {
                pin.setState(value);
            } catch (IOException ignored) {
            }
        }
    }

    public void pinMode(Pin.Mode mode) {
            if(!isDummyPin() && !pin.isLocked()) {
                pin.setMode(mode);
            }
    }
}
