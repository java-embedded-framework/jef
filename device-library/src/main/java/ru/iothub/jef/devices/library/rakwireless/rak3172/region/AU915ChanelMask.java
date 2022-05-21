

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import java.util.Objects;

public enum AU915ChanelMask implements ChannelMask<AU915ChanelMask> {
    ALL("0000", "ALL"),
    SUB_BAND_1("0001", "0-7"),
    SUB_BAND_2("0002", "8-15"),
    SUB_BAND_3("0004", "16-23"),
    SUB_BAND_4("0008", "24-31"),
    SUB_BAND_5("0010", "32-39"),
    SUB_BAND_6("0020", "40-47"),
    SUB_BAND_7("0040", "48-55"),
    SUB_BAND_8("0080", "56-63");

    final String hex;
    final String chanels;

    AU915ChanelMask(String hex, String chanels) {
        this.hex = hex;
        this.chanels = chanels;
    }

    public static AU915ChanelMask fromString(String value) {
        Objects.requireNonNull(value);

        for(AU915ChanelMask obj : AU915ChanelMask.values()) {
            if(obj.hex.equals(value)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + value);
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
