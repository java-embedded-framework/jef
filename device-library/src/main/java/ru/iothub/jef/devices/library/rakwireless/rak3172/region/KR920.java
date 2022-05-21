

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;

public class KR920 extends Region<KR920> {
    static final int CODE = 7;

    public KR920(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
