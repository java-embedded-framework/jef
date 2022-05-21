 

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.util.Objects;

public enum LoRaWANDataRate {
    DR_0(0),
    DR_1(1),
    DR_2(2),
    DR_3(3),
    DR_4(4),
    DR_5(5),
    DR_6(6),
    DR_7(7),
    ;


    final int value;

    LoRaWANDataRate(int value) {
        this.value = value;
    }

    public static LoRaWANDataRate fromString(String s) {
        Objects.requireNonNull(s);
        int i = Integer.parseInt(s);

        for(LoRaWANDataRate obj : LoRaWANDataRate.values()) {
            if(obj.value == i) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + s);
    }
}
