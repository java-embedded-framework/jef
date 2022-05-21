 

package ru.iothub.jef.linux.spi;

import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.gpio.GpioPin;

/**
 * Describes Clock polarity and phase of SPI bus @see <a href="https://en.wikipedia.org/wiki/Serial_Peripheral_Interface#Clock_polarity_and_phase">Wikipedia</a>
 * In addition to setting the clock frequency, the master must also configure the clock polarity and phase with respect to the data. Motorola SPI Block Guide[2] names these two options as CPOL and CPHA (for clock polarity and phase) respectively,
 * a convention most vendors have also adopted.
 */
public enum SpiMode {
    /**
     * Represents CPOL = 0, CPHA = 0 mode
     */
    SPI_MODE_0(SpiModeValues.SPI_MODE_0_VALUE),

    /**
     * Represents CPOL = 0, CPHA = 1 mode
     */
    SPI_MODE_1(SpiModeValues.SPI_MODE_1_VALUE),

    /**
     * Represents CPOL = 1, CPHA = 0 mode
     */
    SPI_MODE_2(SpiModeValues.SPI_MODE_2_VALUE),

    /**
     * Represents CPOL = 1, CPHA = 1 mode
     */
    SPI_MODE_3(SpiModeValues.SPI_MODE_3_VALUE);

    private static final int SPI_CPHA = 0x01;
    private static final int SPI_CPOL = 0x02;

    private static class SpiModeValues {
        private static final int SPI_MODE_0_VALUE = (0 | 0);
        private static final int SPI_MODE_1_VALUE = (0 | SPI_CPHA);
        private static final int SPI_MODE_2_VALUE = (SPI_CPOL | 0);
        private static final int SPI_MODE_3_VALUE = (SPI_CPOL | SPI_CPHA);
    }


    int value;

    SpiMode(int value) {
        this.value = value;
    }

    public static SpiMode valueOf(int value) {
        switch (value) {
            case SpiModeValues.SPI_MODE_0_VALUE:
                return SPI_MODE_0;
            case SpiModeValues.SPI_MODE_1_VALUE:
                return SPI_MODE_1;
            case SpiModeValues.SPI_MODE_2_VALUE:
                return SPI_MODE_2;
            default:
                return SPI_MODE_3;
        }
    }
}
