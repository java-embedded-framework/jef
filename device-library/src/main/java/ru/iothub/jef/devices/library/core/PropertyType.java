 

package ru.iothub.jef.devices.library.core;

public enum PropertyType {
    UNKNOWN(-1),
    STRING(0),
    BYTE(1),
    SHORT(2),
    INTEGER(3),
    LONG(4),
    DOUBLE(5),
    FLOAT(6),
    BOOLEAN(7),
    ENUM(8);

    private final int value;

    PropertyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
