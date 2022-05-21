

package ru.iothub.jef.devices.library.rakwireless.rak3172;

public class LoRaWANPayload {
    private final int port;
    private final String hexData;

    LoRaWANPayload(String obj) {
        String[] split = obj.split(":");
        port = Integer.parseInt(split[0]);
        hexData = split[1];
    }

    public int getPort() {
        return port;
    }

    public String getHexData() {
        return hexData;
    }
}
