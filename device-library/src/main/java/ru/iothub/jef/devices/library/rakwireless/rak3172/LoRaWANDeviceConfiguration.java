

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import ru.iothub.jef.devices.library.rakwireless.rak3172.region.Region;

import java.io.IOException;
import java.util.Objects;

public class LoRaWANDeviceConfiguration {
    private boolean adaptiveDataRate = false;
    private LoRaWANClass clazz;
    private boolean dutyCycleSettings = false;
    private int dutyCycleTime;
    private LoRaWANDataRate loRaWANDataRate = LoRaWANDataRate.DR_0;
    private int rx1JoinDelay = 2000;
    private int rx2JoinDelay = 1000;
    private LoRaWANDataRate rx2DataRate = LoRaWANDataRate.DR_2;
    private LoraFrequency rx2WindowFrequency = LoraFrequency.FREQUENCY_866_55;

    // TODO Update to Region
    private TxPower txPower;
    private Region<?> region;
    private NetworkStatusCheck networkStatusCheck = NetworkStatusCheck.DISABLED;
    private NetworkStatusCheckListener networkStatusCheckListener;
    private LoRaWANDeviceParameters deviceParameters;

    public LoRaWANDeviceConfiguration() {
    }

    public LoRaWANDeviceConfiguration withAdaptiveDataRate(boolean value) {
        adaptiveDataRate = value;
        return this;
    }

    public LoRaWANDeviceConfiguration withClass(LoRaWANClass clazz) {
        this.clazz = Objects.requireNonNull(clazz);
        return this;
    }

    public LoRaWANDeviceConfiguration withClassA() {
        return withClass(LoRaWANClass.CLASS_A);
    }

    public LoRaWANDeviceConfiguration withClassB() {
        return withClass(LoRaWANClass.CLASS_B);
    }

    public LoRaWANDeviceConfiguration withClassC() {
        return withClass(LoRaWANClass.CLASS_C);
    }

    public LoRaWANDeviceConfiguration withDutyCycleSettings(boolean value) {
        dutyCycleSettings = value;
        return this;
    }

    public LoRaWANDeviceConfiguration withDataRate(LoRaWANDataRate rate) {
        loRaWANDataRate = Objects.requireNonNull(rate);
        return this;
    }

    public LoRaWANDeviceConfiguration withRx1JoinDelay(int delay) {
        rx1JoinDelay = delay;
        return this;
    }

    public LoRaWANDeviceConfiguration withRx2JoinDelay(int delay) {
        rx2JoinDelay = delay;
        return this;
    }

    public LoRaWANDeviceConfiguration withRx2DataRate(LoRaWANDataRate dataRate) {
        rx2DataRate = Objects.requireNonNull(dataRate);
        return this;
    }

    public LoRaWANDeviceConfiguration withTxPower(TxPower txPower) {
        this.txPower = Objects.requireNonNull(txPower);
        return this;
    }

    public LoRaWANDeviceConfiguration withRegion(Region<?> region) {
        this.region = region;
        return this;
    }

    public LoRaWANDeviceConfiguration withNetworkStatusCheck(NetworkStatusCheck check) {
        this.networkStatusCheck = Objects.requireNonNull(check);
        return this;
    }

    public LoRaWANDeviceConfiguration withNetworkStatusListener(NetworkStatusCheckListener listener) {
        this.networkStatusCheckListener = listener;
        return this;
    }

    public LoRaWANDeviceConfiguration withDeviceParameters(LoRaWANDeviceParameters deviceParameters) {
        this.deviceParameters = Objects.requireNonNull(deviceParameters);
        return this;
    }

    void apply(RAK3172 rak) throws IOException {
        if (clazz == null) {
            throw new IOException("LoRaWAN class is not set");
        }

        if(region == null) {
            throw new IOException("Region is not set");
        }

        Rak3172ATCommands commands = rak.getCommands();
        commands.AT_ADR().set(adaptiveDataRate ? 1 : 0);
        commands.AT_CLASS().set(clazz.value);
        commands.AT_DCS().set(dutyCycleSettings ? 1 : 0);
        commands.AT_DR().set(loRaWANDataRate.value);
        commands.AT_JN1DL().set(rx1JoinDelay);
        commands.AT_JN2DL().set(rx2JoinDelay);
        commands.AT_RX2DR().set(rx2DataRate.value);
        commands.AT_RX2FQ().set(rx2WindowFrequency.value);
        commands.AT_TXP().set(txPower.getValue());
        commands.AT_LINKCHECK().set(networkStatusCheck.value);

        if(deviceParameters != null) {
            deviceParameters.apply(rak);
        }

        region.apply(rak);
    }

    void read(RAK3172 rak) throws IOException {
        Rak3172ATCommands commands = rak.getCommands();
        adaptiveDataRate = Integer.parseInt(commands.AT_ADR().get()) == 1;
        clazz = LoRaWANClass.fromString(commands.AT_CLASS().get());
        dutyCycleSettings = Integer.parseInt(commands.AT_DCS().get()) == 1;

        // Read only command
        dutyCycleTime = Integer.parseInt(commands.AT_DUTYTIME().get());

        loRaWANDataRate = LoRaWANDataRate.fromString(commands.AT_DR().get());
        rx1JoinDelay = Integer.parseInt(commands.AT_JN1DL().get());
        rx2JoinDelay = Integer.parseInt(commands.AT_JN2DL().get());
        rx2DataRate = LoRaWANDataRate.fromString(commands.AT_RX2DR().get());
        rx2WindowFrequency = LoraFrequency.fromString(commands.AT_RX2FQ().get());
        txPower = new TxPower(Integer.parseInt(commands.AT_TXP().get()));
        networkStatusCheck = NetworkStatusCheck.fromString(commands.AT_LINKCHECK().get());
        region = Region.create(rak);

        if(deviceParameters == null) {
            deviceParameters = new LoRaWANDeviceParameters();
        }
        deviceParameters.read(rak);
    }
}
