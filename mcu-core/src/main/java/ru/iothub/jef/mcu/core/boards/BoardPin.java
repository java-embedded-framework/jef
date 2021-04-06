package ru.iothub.jef.mcu.core.boards;

import ru.iothub.jef.mcu.core.CpuPin;
import ru.iothub.jef.mcu.core.CpuPinMode;
import ru.iothub.jef.mcu.core.CpuPinState;

import java.util.ArrayList;
import java.util.List;

// Wrapper for CPU Pin
public class BoardPin {
    private final int number;
    private final String name;
    private final CpuPin cpuPin;
    // Wrapper for null pins
    private final List<String> nameList = new ArrayList<>();

    public BoardPin(int number, String name, CpuPin cpuPin) {
        this.number = number;
        this.name = name;
        this.cpuPin = cpuPin;
        nameList.add(name);
    }

    public int getPinNumber() {
        return number;
    }

    public int getCpuPinNumber() {
        return isDummyPin() ? -1 : cpuPin.getPinNumber();
    }

    public String getName() {
        return name;
    }

    public CpuPin getCpuPin() {
        return cpuPin;
    }

    public String getPinFunctionName() {
        return isDummyPin() ?
                name : cpuPin.getPinFunctionName();
    }

    public List<String> getPingFunctionNames() {
        return isDummyPin() ?
                nameList : cpuPin.getPingFunctionNames();
    }

    private boolean isDummyPin() {
        return cpuPin == null;
    }

    public CpuPinState digitalRead() {
        return cpuPin == null ? CpuPinState.UNKNOWN : getCpuPin().digitalRead();
    }

    public void digitalWrite(CpuPinState value) {
        if(!isDummyPin()) {
            //System.out.println("Board pin("+number+") writing to cpuPin: " + cpuPin.getPinNumber() + " " + cpuPin.getPinFunctionName());
            cpuPin.digitalWrite(value);
        }
    }

    public void pinMode(CpuPinMode mode) {
        if(!isDummyPin()) {
            cpuPin.pinMode(mode);
        }
    }

}
