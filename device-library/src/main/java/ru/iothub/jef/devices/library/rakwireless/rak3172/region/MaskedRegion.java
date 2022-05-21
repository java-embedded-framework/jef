 

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;

import java.io.IOException;

public abstract class MaskedRegion<E extends Region<E>, M> extends Region<E> {
    private ChannelMask<M> mask;

    public MaskedRegion(RAK3172 rak) {
        super(rak);
    }

    public abstract E withMask(ChannelMask<M> mask);

    protected void mask(ChannelMask<M> mask) {
        this.mask = mask;
    }

    @Override
    public void apply(RAK3172 rak) throws IOException {
        super.apply(rak);
        Rak3172ATCommands commands = rak.getCommands();
        commands.AT_MASK().set(mask.getHex());
    }
}
