 

package ru.iothub.jef.devices.library.bosch.bcm280;

/**
 * Basic oversample values for sensor register
 */
enum Oversampling {
    BMP280_OVERSAMP_SKIPPED(0x00),
    BMP280_OVERSAMP_1X(0x01),
    BMP280_OVERSAMP_2X(0x02),
    BMP280_OVERSAMP_4X(0x03),
    BMP280_OVERSAMP_8X(0x04),
    BMP280_OVERSAMP_16X(0x05);

    private final int value;

    Oversampling(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
