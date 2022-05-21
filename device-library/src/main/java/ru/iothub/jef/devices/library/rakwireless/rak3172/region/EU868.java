

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;

public class EU868 extends Region<EU868> {
    static final int CODE = 4;

    public EU868(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
