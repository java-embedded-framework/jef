 

package ru.iothub.jef.devices.library.rakwireless.rak3172;

public enum NetworkWorkMode {
    P2P(0),
    LoRaWAN(1);

    final int value;

    NetworkWorkMode(int value) {
        this.value = value;
    }

    public static NetworkWorkMode fromInteger(int i) {
        for(NetworkWorkMode obj : NetworkWorkMode.values()) {
            if(obj.value == i ) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + i);
    }
}
