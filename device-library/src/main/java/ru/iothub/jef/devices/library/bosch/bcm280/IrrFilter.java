 

package ru.iothub.jef.devices.library.bosch.bcm280;

/**
 * The environmental pressure is subject to many short-term changes, caused e.g.
 * by slamming of a door or window, or wind blowing into the sensor. To suppress
 * these disturbances in the output data without causing additional interface traffic
 * and processor work load, the BMP280 features an internal IIR filter.
 * It effectively reduces the bandwidth of the output signals6.
 * The output of a next measurement step is filter using the following formula
 * described in section 3.3.3 in datasheet
 */
public enum IrrFilter {
    /**
     * Filter coefficient - off
     */
    COEFF_OFF(0x00),
    /**
     * Filter coefficient - x2
     */
    COEFF_2(0x01),
    /**
     * Filter coefficient - x4
     */
    COEFF_4(0x02),
    /**
     * Filter coefficient - x8
     */
    COEFF_8(0x03),
    /**
     * Filter coefficient - x6
     */
    COEFF_16(0x04);

    private final int value;

    /**
     * Allocate enum value
     * @param value value
     */
    IrrFilter(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link IrrFilter}
     * @param i integer value
     * @return power mode value
     */
    public static IrrFilter fromInteger(int i) {
        for(IrrFilter obj : IrrFilter.values()) {
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
        return "IrrFilter{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
