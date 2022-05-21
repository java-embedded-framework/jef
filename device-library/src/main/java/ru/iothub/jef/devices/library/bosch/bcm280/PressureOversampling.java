 

package ru.iothub.jef.devices.library.bosch.bcm280;


import static ru.iothub.jef.devices.library.bosch.bcm280.Oversampling.*;

/**
 * The current consumption depends on Output Data Rate(ODR) and oversampling setting.
 * The values given below are normalized to an ODR of 1 Hz.
 * The actual consumption at a given ODR can be calculated by multiplying the consumption in
 * Table 12 in datasheet with the ODR used. The actual ODR is defined either by the frequency
 * at which the user sets forced measurements or by oversampling and tstandby settings in
 * normal mode in Table 14 in datasheet.
 */
public enum PressureOversampling {
    SKIP(BMP280_OVERSAMP_SKIPPED),
    ULTRA_LOW_POWER(BMP280_OVERSAMP_1X),
    LOW_POWER(BMP280_OVERSAMP_2X),
    STANDARD(BMP280_OVERSAMP_4X),
    HIGH_RESOLUTION(BMP280_OVERSAMP_8X),
    ULTRA_HIGH(BMP280_OVERSAMP_16X);

    private final int value;

    /**
     * Allocate enum based on {@link Oversampling} value
     * @param oversampling value
     */
    PressureOversampling(Oversampling oversampling) {
        this(oversampling.getValue());
    }

    /**
     * Allocate enum value
     * @param value value
     */
    PressureOversampling(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link PressureOversampling}
     * @param i integer value
     * @return power mode value
     */
    public static PressureOversampling fromInteger(int i) {
        for(PressureOversampling obj : PressureOversampling.values()) {
            if(obj.value == i) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + i);
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
        return "PressureOversampling{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
