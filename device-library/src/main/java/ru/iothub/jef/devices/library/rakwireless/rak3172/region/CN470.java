 

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;

import java.io.IOException;

public class CN470 extends MaskedRegion<CN470, CN470ChanelMask> {
    static final int CODE = 1;

    public CN470(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }

    @Override
    public CN470 withMask(ChannelMask<CN470ChanelMask> mask) {
        mask(mask);
        return this;
    }

    @Override
    protected void read(RAK3172 rak) throws IOException {
        super.read(rak);
        Rak3172ATCommands commands = rak.getCommands();
        String value = commands.AT_MASK().get();
        mask(CN470ChanelMask.fromString(value));
    }
}
