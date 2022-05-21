

package ru.iothub.jef.linux.gpio;

import java.util.Objects;

public class GpioHandleData {
    private final byte[] values;

    public GpioHandleData(byte[] values) {
        Objects.requireNonNull(values);
        this.values = values;
    }

    public byte[] getValues() {
        return values;
    }

}
