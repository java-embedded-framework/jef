

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.util.Objects;

public enum NetworkStatusCheck {
    DISABLED(0),
    ONES_CHECK(1),
    EVERY_PACKET(2);
    int value;

    NetworkStatusCheck(int value) {
        this.value = value;
    }

    public static NetworkStatusCheck fromString(String s) {
        Objects.requireNonNull(s);

        int i = Integer.parseInt(s);
        for(NetworkStatusCheck obj : NetworkStatusCheck.values()) {
            if(obj.value == i) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + s);
    }
}
