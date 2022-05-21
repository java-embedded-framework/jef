

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.util.Objects;

// https://www.baranidesign.com/faq-articles/2019/4/23/lorawan-usa-frequencies-channels-and-sub-bands-for-iot-devices
public enum LoraFrequency {
    // TODO implement all chanels = https://www.thethingsnetwork.org/docs/lorawan/frequency-plans/
    FREQUENCY_866_55(866550000);
    final int value;

    LoraFrequency(int value) {
        this.value = value;
    }

    public static LoraFrequency fromString(String s) {
        Objects.requireNonNull(s);

        int i = Integer.parseInt(s);
        for(LoraFrequency obj : LoraFrequency.values()) {
            if(obj.value == i) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + s);
    }
}
