 

package ru.iothub.jef.devices.library.bosch.bcm280;

/**
 * The BMP280 offers three power modes: sleep mode, forced mode and normal mode.
 * Please see {@link #BMP280_SLEEP_MODE}, {@link #BMP280_FORCED_MODE}, {@link #BMP280_NORMAL_MODE}
 */
public enum PowerMode {
    /**
     * Sleep mode is set by default after power on reset.
     * In sleep mode, no measurements are performed and power consumption (IDDSM) is at a minimum.
     * All registers are accessible; Chip-ID and compensation coefficients can be read.
     */
    BMP280_SLEEP_MODE(0x00),

    /**
     * In forced mode, a single measurement is performed according to selected measurement
     * and filter options. When the measurement is finished, the sensor returns to sleep
     * mode and the measurement results can be obtained from the data registers.
     * For a next measurement, forced mode needs to be selected again.
     * This is similar to BMP180 operation. Forced mode is recommended for applications
     * which require low sampling rate or host-based synchronization.
     */
    BMP280_FORCED_MODE(0x01),

    /**
     * Normal mode 16ontinuously cycles between an (active) measurement period and an
     * (inactive) standby period, whose time is defined by tstandby.
     * The current in the standby period (IDDSB) is slightly higher than in sleep mode.
     * After setting the mode,measurement and filter options, the last measurement
     * results can be obtained from the data registers without the need of further write accesses.
     * Normal mode is recommended when using the IIR filter, and useful for applications in
     * which short-term disturbances (e.g. blowing into the sensor) should be filtered.
     */
    BMP280_NORMAL_MODE(0x03);

    private final int value;

    /**
     * Allocate enum value
     * @param value value
     */
    PowerMode(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link PowerMode}
     * @param i integer value
     * @return power mode value
     */
    public static PowerMode fromInteger(int i) {
        for(PowerMode obj : PowerMode.values()) {
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
        return "PowerMode{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
