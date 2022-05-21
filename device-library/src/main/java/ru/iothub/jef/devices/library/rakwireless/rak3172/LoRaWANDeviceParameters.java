 

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;

@SuppressWarnings("unused")
public class LoRaWANDeviceParameters {
    private long eui;
    private long applicationUniqueIdentifier;
    private BigInteger applicationKey = BigInteger.ZERO;
    private int deviceAddress;
    private BigInteger applicationSessionKey = BigInteger.ZERO;
    private BigInteger networkSessionKey = BigInteger.ZERO;

    public LoRaWANDeviceParameters() {

    }

    public LoRaWANDeviceParameters withEUI(long eui) {
        this.eui = eui;
        return this;
    }

    public LoRaWANDeviceParameters withAppUID(long uid) {
        this.applicationUniqueIdentifier = uid;
        return this;
    }

    public LoRaWANDeviceParameters withAppKey(BigInteger applicationKey) {
        this.applicationKey = Objects.requireNonNull(applicationKey);
        return this;
    }

    public LoRaWANDeviceParameters withDeviceAddress(int deviceAddress) {
        this.deviceAddress = deviceAddress;
        return this;
    }

    public LoRaWANDeviceParameters withAppSessionKey(BigInteger key) {
        this.applicationSessionKey = Objects.requireNonNull(key);
        return this;
    }

    public LoRaWANDeviceParameters withNetworkSessionKey(BigInteger key) {
        this.networkSessionKey = Objects.requireNonNull(key);
        return this;
    }

    public long getEui() {
        return eui;
    }

    public long getApplicationUniqueIdentifier() {
        return applicationUniqueIdentifier;
    }

    public BigInteger getApplicationKey() {
        return applicationKey;
    }

    public int getDeviceAddress() {
        return deviceAddress;
    }

    public BigInteger getApplicationSessionKey() {
        return applicationSessionKey;
    }

    public BigInteger getNetworkSessionKey() {
        return networkSessionKey;
    }

    void apply(RAK3172 rak) throws IOException {
        Rak3172ATCommands cmd = rak.getCommands();
        cmd.AT_DEVEUI().set(String.format("%08X", eui));
        cmd.AT_APPEUI().set(String.format("%08X",applicationUniqueIdentifier));
        cmd.AT_APPKEY().set( String.format("%016X", applicationKey) );
        cmd.AT_APPSKEY().set( String.format("%016X", applicationSessionKey) );
        cmd.AT_NWKSKEY().set( String.format("%016X", networkSessionKey) );
        cmd.AT_DEVADDR().set( String.format("%04X", deviceAddress) );
    }

    void read(RAK3172 rak) throws IOException {
        Rak3172ATCommands cmd = rak.getCommands();
        eui = Long.parseLong(cmd.AT_DEVEUI().get(), 16);
        applicationUniqueIdentifier = Long.parseLong(cmd.AT_APPEUI().get(), 16);
        applicationKey = new BigInteger(cmd.AT_APPKEY().get(), 16);
        deviceAddress = Integer.parseInt(cmd.AT_DEVADDR().get(), 16);
        applicationSessionKey = new BigInteger(cmd.AT_APPSKEY().get(), 16);
        networkSessionKey = new BigInteger(cmd.AT_NWKSKEY().get(), 16);
    }
}
