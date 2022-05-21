

package ru.iothub.jef.devices.library.rakwireless.rak3172;

public class JoinLoRaWANParameters {
    private JoinMode mode; // param 1
    private JoinConfig config; // param 2
    private byte attemptInterval = 7; // param 3 - 7 - 255 seconds (8 is default)
    private byte numberOfAttempts = 0; // 0 - 255 (0 is default)

    public JoinLoRaWANParameters(String obj) {
        String[] split = obj.split(":");

        mode = Integer.parseInt(split[0]) == 0 ? JoinMode.STOP_JOINING : JoinMode.JOIN_NETWORK;
        config = Integer.parseInt(split[1]) == 0 ? JoinConfig.NO_AUTO_JOIN: JoinConfig.AUTO_JOIN_ON_POWER_UP;
        attemptInterval = Byte.parseByte(split[2]);
        numberOfAttempts = Byte.parseByte(split[3]);
    }

    public JoinMode getMode() {
        return mode;
    }

    public void setMode(JoinMode mode) {
        this.mode = mode;
    }

    public JoinConfig getConfig() {
        return config;
    }

    public void setConfig(JoinConfig config) {
        this.config = config;
    }

    public byte getAttemptInterval() {
        return attemptInterval;
    }

    public void setAttemptInterval(byte attemptInterval) {
        if (attemptInterval < 7) {
            attemptInterval = 7;
        }

        this.attemptInterval = attemptInterval;
    }

    public byte getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(byte numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public String asString() {
        return mode.value + ":" + config.value + ":" + attemptInterval + ":" + numberOfAttempts;
    }

    public enum JoinMode {
        STOP_JOINING(0),
        JOIN_NETWORK(1);

        final int value;

        JoinMode(int value) {
            this.value = value;
        }
    }

    public enum JoinConfig {
        NO_AUTO_JOIN(0),
        AUTO_JOIN_ON_POWER_UP(1);

        final int value;

        JoinConfig(int value) {
            this.value = value;
        }
    }
}
