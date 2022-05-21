 

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

public abstract class AbstractChanelMask implements ChannelMask {
    final String hex;
    final String chanels;

    public AbstractChanelMask(String hex, String chanels) {
        this.hex = hex;
        this.chanels = chanels;
    }

    @Override
    public String getHex() {
        return hex;
    }

    @Override
    public String getChanels() {
        return chanels;
    }
}
