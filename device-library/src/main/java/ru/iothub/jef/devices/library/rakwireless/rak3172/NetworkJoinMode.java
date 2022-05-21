

package ru.iothub.jef.devices.library.rakwireless.rak3172;

public enum NetworkJoinMode {
    ABP(0),
    OTAA(1);


    final int value;

    NetworkJoinMode(int value) {
        this.value = value;
    }

    public static NetworkJoinMode fromInteger(int i) {
        for (NetworkJoinMode obj : NetworkJoinMode.values()) {
            if (obj.value == i) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + i);
    }
}
