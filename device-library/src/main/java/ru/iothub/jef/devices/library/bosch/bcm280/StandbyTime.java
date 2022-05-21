 

package ru.iothub.jef.devices.library.bosch.bcm280;

/**
 * Standup time for sensor
 * Please see datasheet for details
 */
public enum StandbyTime {
    /**
     * 1 ms standby time
     */
    STANDBY_TIME_1_MS(0x00),
    /**
     * 63 ms standby time
     */
    STANDBY_TIME_63_MS(0x01),
    /**
     * 125 ms standby time
     */
    STANDBY_TIME_125_MS(0x02),
    /**
     * 250 ms standby time
     */
    STANDBY_TIME_250_MS(0x03),
    /**
     * 500 ms standby time
     */
    STANDBY_TIME_500_MS(0x04),
    /**
     * 1000 ms standby time
     */
    STANDBY_TIME_1000_MS(0x05),
    /**
     * 2000 ms standby time
     */
    STANDBY_TIME_2000_MS(0x06),
    /**
     * 4000 ms standby time
     */
    STANDBY_TIME_4000_MS(0x07);

    private final int value;

    /**
     * Allocate enum value
     * @param value value
     */
    StandbyTime(int value) {
        this.value = value;
    }

    /**
     * Convert {@code int} to {@link StandbyTime}
     * @param i integer value
     * @return power mode value
     */
    public static StandbyTime fromInteger(int i) {
        for(StandbyTime obj : StandbyTime.values()) {
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
        return "StandbyTime{" +
                "name=" + super.toString() +
                ", value=" + value +
                '}';
    }
}
