

package ru.iothub.jef.devices.library.bosch.bcm280;

/**
 * Represents hardcoded addressed for BMP280 sensor
 */
@SuppressWarnings("unused")
public enum I2CAddress {
    /**
     * 0x76 address
     */
    I2C_ADDRESS1(0x76),
    /**
     * 0x77 address
     */
    I2C_ADDRESS2(0x77);

    private final int value;

    /**
     * Allocates enum instance
     * @param addr value
     */
    I2CAddress(int addr) {
        this.value = addr;
    }

    /**
     * Returns {@code int} representation of value
     * @return int value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Bmp280i2cAddress{" +
                "value=" + value +
                '}';
    }
}
