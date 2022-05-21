 

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;

public class RU864 extends Region<RU864> {
    static final int CODE = 2;

    public RU864(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
