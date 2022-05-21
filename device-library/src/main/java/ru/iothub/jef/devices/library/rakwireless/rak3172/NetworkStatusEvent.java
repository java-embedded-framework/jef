

package ru.iothub.jef.devices.library.rakwireless.rak3172;

public class NetworkStatusEvent {
    boolean executeSuccess;
    int demodMargin;
    int numberOfGatewaysRecieved;
    int signalStrengthIndicator;
    int signalNoiseRatio;

    NetworkStatusEvent(boolean executeSuccess, int demodMargin, int numberOfGatewaysRecieved, int signalStrengthIndicator, int signalNoiseRatio) {
        this.executeSuccess = executeSuccess;
        this.demodMargin = demodMargin;
        this.numberOfGatewaysRecieved = numberOfGatewaysRecieved;
        this.signalStrengthIndicator = signalStrengthIndicator;
        this.signalNoiseRatio = signalNoiseRatio;
    }

    public boolean isExecuteSuccess() {
        return executeSuccess;
    }

    public int getDemodMargin() {
        return demodMargin;
    }

    public int getNumberOfGatewaysRecieved() {
        return numberOfGatewaysRecieved;
    }

    public int getSignalStrengthIndicator() {
        return signalStrengthIndicator;
    }

    public int getSignalNoiseRatio() {
        return signalNoiseRatio;
    }
}
