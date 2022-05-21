

package ru.iothub.jef.devices.library.maximintegrated;

import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.io.FileHandle;

import java.io.IOException;

/**
 * Provides ablility to Maxis Integrated DS18B20 sensor
 * Datasheet available here - https://datasheets.maximintegrated.com/en/ds/DS18B20.pdf
 *
 */
@SuppressWarnings("unused")
public class DS18B20 {
    private final String name;
    private final Fcntl fcntl;
    private final byte[] buffer;

    /**
     * Allocate new instance of DS18B20 sensor
     * @param path path to sensor in Linux file system
     *             like {@code '/sys/bus/w1/devices/28-&#42;/w1_slave'}
     */
    public DS18B20(String path) {
        name = path;
        fcntl = Fcntl.getInstance();
        buffer = new byte[255];
    }

    /**
     * Gets templerature in celsius
     * @return temperature
     * @throws IOException if i2c bus not allow this operation
     */
    public double getTemperatureCelsius() throws IOException {
        synchronized (this) {
            try(FileHandle handle = fcntl.open(name, IOFlags.O_RDONLY)) {
                int length = fcntl.read(handle, buffer, buffer.length);
                String s = new String(buffer, 0, length);

                if (!s.contains("YES")) {
                    throw new IOException("Invalid CRC for '" + name + "'");
                }

                int idx = s.indexOf("t=");
                if (idx == -1) {
                    throw new IOException("Invalid temperature format data for '" + name + "'");
                }

                String substring = s.substring(idx + 2);
                return Double.parseDouble(substring) / 1000;
            }
        }
    }

    /**
     * Gets fahrenheit in celsius
     * @return temperature
     * @throws IOException if i2c bus not allow this operation
     */
    public double getTemperatureFahrenheit() throws IOException {
        return getTemperatureCelsius() * 1.8 + 32;
    }
}
