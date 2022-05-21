 

package ru.iothub.jef.devices.library.rakwireless.rak3172;

// TODO implement
// https://docs.rakwireless.com/Product-Categories/WisDuo/RAK3172-Module/AT-Command-Manual/#appendix-ii-tx-power-by-region
public class TxPower {
    int value;

    public TxPower(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }
}
