 

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import ru.iothub.jef.devices.library.core.DeviceFactory;

public abstract class RakWirelessDeviceFactory implements DeviceFactory {
    private final static String VENDOR = "RakWireless";

    @Override
    public String getVendor() {
        return VENDOR;
    }

    @Override
    public String getVendorIcon() {
        return null;
    }
}
