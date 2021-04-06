package ru.iothub.jef.mcu.core.bcm;

import java.util.*;

public class BCM2711Pin extends BCMCorePin {
    private final Map<Integer, BCM2711PinFunctions> functions;

    public BCM2711Pin(BCM2711 cpu, int number, BCM2711PinFunctions... alternatives) {
        super(cpu, number);
        functions = fillFSelFunctions(alternatives);
    }

    private Map<Integer, BCM2711PinFunctions> fillFSelFunctions(BCM2711PinFunctions... alt) {
        if (alt == null || alt.length != 6) {
            throw new RuntimeException("Invalid mapping for BCM2711 pin:" + getPinNumber());
        }

        Map<Integer, BCM2711PinFunctions> result = new LinkedHashMap<>(8);

        result.put(FSEL_INPUT,BCM2711PinFunctions.IN);
        result.put(FSEL_OUTPUT, BCM2711PinFunctions.OUT);
        result.put(FSEL_ALT_0, alt[0]);
        result.put(FSEL_ALT_1, alt[1]);
        result.put(FSEL_ALT_2, alt[2]);
        result.put(FSEL_ALT_3, alt[3]);
        result.put(FSEL_ALT_4, alt[4]);
        result.put(FSEL_ALT_5, alt[5]);
        return result;
    }

    @Override
    public String getPinFunctionName() {
        int index = getPinFunction();
        if (index < 0 || index > 7) {
            throw new IllegalStateException("Invalid pin function: " + index);
        }
        return functions.get(index).getName();
    }

    @Override
    public List<String> getPingFunctionNames() {
        List<String> result = new ArrayList<>();

        for (BCM2711PinFunctions item : functions.values()) {
            result.add(item.getName());
        }

        return result;
    }
}
