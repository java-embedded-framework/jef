

package ru.iothub.jef.devices.library.core;

public interface DeviceFactory {
    String getVendor();
    String getVendorIcon();

    String getType();
    String getTypeIcon();

    String getDeviceName();
    String getDeviceIcon();
    String getDeviceDescription();

    String getDatasheet();

    Device createDevice();
}
