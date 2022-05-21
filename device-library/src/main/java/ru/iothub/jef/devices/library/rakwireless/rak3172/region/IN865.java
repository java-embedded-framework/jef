

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;

public class IN865 extends Region<IN865> {
    static final int CODE = 3;

    public IN865(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
