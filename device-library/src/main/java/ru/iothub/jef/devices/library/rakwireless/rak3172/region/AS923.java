 

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;

public class AS923 extends Region<AS923> {
    static final int CODE = 8;

    public AS923(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
