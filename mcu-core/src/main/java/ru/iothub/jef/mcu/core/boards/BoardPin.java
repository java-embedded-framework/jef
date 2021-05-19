package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.linux.gpio.GpioLineInfo;
import ru.iothub.jef.linux.gpio.GpioPin;

import java.io.IOException;

@SuppressWarnings("unused")
public class BoardPin {
    private final int number;
    private final String name;
    private final GpioPin pin;
    private final BoardPinInfo info;

    public BoardPin(int number, String name, GpioPin cpuPin) {
        this.number = number;
        this.name = name;
        this.pin = cpuPin;
        this.info = cpuPin == null ? new BoardPinInfo() : new BoardPinInfo(cpuPin.getFlags());
    }

    public int getPinNumber() {
        return number;
    }

    public int getCpuPinNumber() {
        return isDummyPin() ? -1 : pin.getPinNumber();
    }

    public BoardPinInfo getPinInfo() {
        return info;
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

    public static class BoardPinInfo {
        private final boolean dummy;
        private final boolean usedByKernel;
        private final boolean outputPin;
        private final boolean activeLow;

        public BoardPinInfo() {
            this(true, false, false, false);
        }

        public BoardPinInfo(int flags) {
            this(
                    false,
                    (flags & GpioLineInfo.Flags.GPIOLINE_FLAG_KERNEL.getValue()) > 0,
                    (flags & GpioLineInfo.Flags.GPIOLINE_FLAG_IS_OUT.getValue()) > 0,
                    (flags & GpioLineInfo.Flags.GPIOLINE_FLAG_ACTIVE_LOW.getValue()) > 0
            );
        }

        private BoardPinInfo(boolean dummy, boolean usedByKernel, boolean outputPin, boolean activeLow) {
            this.dummy = dummy;
            this.usedByKernel = usedByKernel;
            this.outputPin = outputPin;
            this.activeLow = activeLow;
        }

        public boolean isDummyPin() {
            return dummy;
        }

        public boolean isUsedByKernel() {
            return usedByKernel;
        }

        public boolean isOutputPin() {
            return outputPin;
        }

        public boolean isActiveLow() {
            return activeLow;
        }
    }
}
