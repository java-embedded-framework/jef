

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.io.IOException;

@SuppressWarnings("unused")
public class DeviceInformation {
    private int receiveSignalStrengthIndicator;
    private int signalToNoiseRatio;
    private String version;

    public DeviceInformation(RAK3172 rak) throws IOException {
        read(rak);
    }

    public int getReceiveSignalStrengthIndicator() {
        return receiveSignalStrengthIndicator;
    }

    public int getSignalToNoiseRatio() {
        return signalToNoiseRatio;
    }

    public String getVersion() {
        return version;
    }

    void read(RAK3172 rak) throws IOException {
        Rak3172ATCommands commands = rak.getCommands();
        receiveSignalStrengthIndicator = Integer.parseInt(commands.AT_RSSI().get());
        signalToNoiseRatio = Integer.parseInt(commands.AT_SNR().get());
        version = commands.AT_VER().get();
    }

    @Override
    public String toString() {
        return "DeviceInformation{" +
                "receiveSignalStrengthIndicator=" + receiveSignalStrengthIndicator +
                ", signalToNoiseRatio=" + signalToNoiseRatio +
                ", version='" + version + '\'' +
                '}';
    }
}
