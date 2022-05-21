

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;

import java.io.IOException;

public class US915 extends MaskedRegion<US915, US915ChanelMask> {
    static final int CODE = 5;

    public US915(RAK3172 rak) {
        super(rak);
    }

    @Override
    protected void read(RAK3172 rak) throws IOException {
        super.read(rak);
        Rak3172ATCommands commands = rak.getCommands();
        String value = commands.AT_MASK().get();
        mask(US915ChanelMask.fromString(value));
    }

    @Override
    public int getCode() {
        return CODE;
    }

    @Override
    public US915 withMask(ChannelMask<US915ChanelMask> mask) {
        mask(mask);
        return this;
    }

}
