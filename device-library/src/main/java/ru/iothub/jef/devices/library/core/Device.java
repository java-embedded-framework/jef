 

package ru.iothub.jef.devices.library.core;

import java.util.List;
import java.util.Map;

public interface Device {
    Map<String, Property> getUserProperties();
    Map<String, Property> getSystemProperties();
}
