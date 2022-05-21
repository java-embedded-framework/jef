

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.util.Objects;

public enum LoRaWANClass {
    CLASS_A('A'),
    CLASS_B('B'),
    CLASS_C('C');


    final char value;

    LoRaWANClass(char value) {
        this.value = value;
    }

    public static LoRaWANClass fromString(String s) {
        Objects.requireNonNull(s);
        if(s.length()==1) {
            for (LoRaWANClass obj : LoRaWANClass.values()) {
                if (obj.value == s.charAt(0)) {
                    return obj;
                }
            }
        }
        throw new IllegalArgumentException("Illegal value: " + s);
    }
}
