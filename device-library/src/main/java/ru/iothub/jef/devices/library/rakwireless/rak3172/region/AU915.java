 

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;

import java.io.IOException;

public class AU915 extends MaskedRegion<AU915, AU915ChanelMask> {
    static final int CODE = 6;

    public AU915(RAK3172 rak) {
        super(rak);
    }

    @Override
    public int getCode() {
        return CODE;
    }

    @Override
    public AU915 withMask(ChannelMask<AU915ChanelMask> mask) {
        mask(mask);
        return this;
    }

    @Override
    protected void read(RAK3172 rak) throws IOException {
        super.read(rak);
        Rak3172ATCommands commands = rak.getCommands();
        String value = commands.AT_MASK().get();
        mask(AU915ChanelMask.fromString(value));
    }
}