 

package ru.iothub.jef.linux.gpio;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class GpioManager {
    private final static Map<String, GpioPin> pinsets = new HashMap<>();

    public static GpioPin getPin(String path, int number) throws IOException {
        synchronized (GpioManager.class) {
            GpioPin pin;
            String key = getKey(path, number);
            if ((pin = getCachedKey(key)) != null) {
                return pin;
            }
            pin = new GpioPin(key, path, number);
            pinsets.put(key, pin);
            return pin;
        }
        //return getPin(path, number, null);
    }

    public static boolean isUsed(String path, int number) {
        synchronized (GpioManager.class) {
            return getCachedKey(getKey(path, number)) != null;
        }
    }

    /*private static GpioPin getPin(String path, int number, GpioPin.Direction direction) throws IOException {
        synchronized (GpioManager.class) {
            GpioPin pin;
            String key = getKey(path, number);
            if (direction != null && (pin = getCachedKey(key)) != null) {
                if (pin.getDirection() != direction) {
                    pin.setDirection(direction);
                }
                return pin;
            }
            pin = direction == null ?
                    new GpioPin(key, path, number) :
                    new GpioPin(key, path, number, direction);
            pinsets.put(key, pin);
            return pin;
        }
    }*/

    private static String getKey(String path, int number) {
        return path + "-" + number;
    }

    private static GpioPin getCachedKey(String cacheKey) {
        return pinsets.get(cacheKey);
    }

    static void closePin(String key) {
        synchronized (GpioManager.class) {
            pinsets.remove(key);
        }
    }
}
