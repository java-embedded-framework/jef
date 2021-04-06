package ru.iothub.jef.mcu.core.bcm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.iothub.jef.mcu.core.bcm.BCM2711PinFunctions.*;

public class BCM2711Pins {
    public static List<BCM2711Pin> createPins(BCM2711 cpu) {
        BCM2711Pin[] pins = {
                new BCM2711Pin(cpu, 0, SDA0, SA5, PCLK, SPI3_CE0_N, TXD2, SDA6),
                new BCM2711Pin(cpu, 1, SCL0, SA4, DE, SPI3_MISO, RXD2, SCL6),
                new BCM2711Pin(cpu, 2, SDA1, SA3, LCD_VSYNC, SPI3_MOSI, CTS2, SDA3),
                new BCM2711Pin(cpu, 3, SCL1, SA2, LCD_HSYNC, SPI3_SCLK, RTS2, SCL3),
                new BCM2711Pin(cpu, 4, GPCLK0, SA1, DPI_D0, SPI4_CE0_N, TXD3, SDA3),
                new BCM2711Pin(cpu, 5, GPCLK1, SA0, DPI_D1, SPI4_MISO, RXD3, SCL3),
                new BCM2711Pin(cpu, 6, GPCLK2, SOE_N_SE, DPI_D2, SPI4_MOSI, CTS3, SDA4),
                new BCM2711Pin(cpu, 7, SPI0_CE1_N, SWE_N_SRW_N, DPI_D3, SPI4_SCLK, RTS3, SCL4),
                new BCM2711Pin(cpu, 8, SPI0_CE0_N, SD0, DPI_D4, BSCSL_CE_N, TXD4, SDA4),
                new BCM2711Pin(cpu, 9, SPI0_MISO, SD1, DPI_D5, BSCSL_MISO, RXD4, SCL4),
                new BCM2711Pin(cpu, 10, SPI0_MOSI, SD2, DPI_D6, BSCSL_SDA_MOSI, CTS4, SDA5),
                new BCM2711Pin(cpu, 11, SPI0_SCLK, SD3, DPI_D7, BSCSL_SCL_SCLK, RTS4, SCL5),
                new BCM2711Pin(cpu, 12, PWM0_0, SD4, DPI_D8, SPI5_CE0_N, TXD5, SDA5),
                new BCM2711Pin(cpu, 13, PWM0_1, SD5, DPI_D9, SPI5_MISO, RXD5, SCL5),
                new BCM2711Pin(cpu, 14, TXD0, SD6, DPI_D10, SPI5_MOSI, CTS5, TXD1),
                new BCM2711Pin(cpu, 15, RXD0, SD7, DPI_D11, SPI5_SCLK, RTS5, RXD1),
                new BCM2711Pin(cpu, 16, RESERVED, SD8, DPI_D12, CTS0, SPI1_CE2_N, CTS1),
                new BCM2711Pin(cpu, 17, RESERVED, SD9, DPI_D13, RTS0, SPI1_CE1_N, RTS1),
                new BCM2711Pin(cpu, 18, PCM_CLK, SD10, DPI_D14, SPI6_CE0_N, SPI1_CE0_N, PWM0_0),
                new BCM2711Pin(cpu, 19, PCM_FS, SD11, DPI_D15, SPI6_MISO, SPI1_MISO, PWM0_1),
                new BCM2711Pin(cpu, 20, PCM_DIN, SD12, DPI_D16, SPI6_MOSI, SPI1_MOSI, GPCLK0),
                new BCM2711Pin(cpu, 21, PCM_DOUT, SD13, DPI_D17, SPI6_SCLK, SPI1_SCLK, GPCLK1),
                new BCM2711Pin(cpu, 22, SD0_CLK, SD14, DPI_D18, SD1_CLK, ARM_TRST, SDA6),
                new BCM2711Pin(cpu, 23, SD0_CMD, SD15, DPI_D19, SD1_CMD, ARM_RTCK, SCL6),
                new BCM2711Pin(cpu, 24, SD0_DAT0, SD16, DPI_D20, SD1_DAT0, ARM_TDO, SPI3_CE1_N),
                new BCM2711Pin(cpu, 25, SD0_DAT1, SD17, DPI_D21, SD1_DAT1, ARM_TCK, SPI4_CE1_N),
                new BCM2711Pin(cpu, 26, SD0_DAT2, RESERVED, DPI_D22, SD1_DAT2, ARM_TDI, SPI5_CE1_N),
                new BCM2711Pin(cpu, 27, SD0_DAT3, RESERVED, DPI_D23, SD1_DAT3, ARM_TMS, SPI6_CE1_N),
                new BCM2711Pin(cpu, 28, SDA0, SA5, PCM_CLK, RESERVED, MII_A_RX_ERR, RGMII_MDIO),
                new BCM2711Pin(cpu, 29, SCL0, SA4, PCM_FS, RESERVED, MII_A_TX_ERR, RGMII_MDC),
                new BCM2711Pin(cpu, 30, RESERVED, SA3, PCM_DIN, CTS0, MII_A_CRS, CTS1),
                new BCM2711Pin(cpu, 31, RESERVED, SA2, PCM_DOUT, RTS0, MII_A_COL, RTS1),
                new BCM2711Pin(cpu, 32, GPCLK0, SA1, RESERVED, TXD0, SD_CARD_PRE_S, TXD1),
                new BCM2711Pin(cpu, 33, RESERVED, SA0, RESERVED, RXD0, SD_CARD_WR_PROT, RXD1),
                new BCM2711Pin(cpu, 34, GPCLK0, SOE_N_SE, RESERVED, SD1_CLK, SD_CARD_LED, RGMII_IRQ),
                new BCM2711Pin(cpu, 35, SPI0_CE1_N, SWE_N_SRW_N, N_A, SD1_CMD, RGMII_START_STOP, N_A),
                new BCM2711Pin(cpu, 36, SPI0_CE0_N, SD0, TXD0, SD1_DAT0, RGMII_RX_OK, MII_A_RX_ERR),
                new BCM2711Pin(cpu, 37, SPI0_MISO, SD1, RXD0, SD1_DAT1, RGMII_MDIO, MII_A_TX_ERR),
                new BCM2711Pin(cpu, 38, SPI0_MOSI, SD2, RTS0, SD1_DAT2, RGMII_MDC, MII_A_CRS),
                new BCM2711Pin(cpu, 39, SPI0_SCLK, SD3, CTS0, SD1_DAT3, RGMII_IRQ, MII_A_COL),
                new BCM2711Pin(cpu, 40, PWM1_0, SD4, N_A, SD1_DAT4, SPI0_MISO, TXD1),
                new BCM2711Pin(cpu, 41, PWM1_1, SD5, RESERVED, SD1_DAT5, SPI0_MOSI, RXD1),
                new BCM2711Pin(cpu, 42, GPCLK1, SD6, RESERVED, SD1_DAT6, SPI0_SCLK, RTS1),
                new BCM2711Pin(cpu, 43, GPCLK2, SD7, RESERVED, SD1_DAT7, SPI0_CE0_N, CTS1),
                new BCM2711Pin(cpu, 44, GPCLK1, SDA0, SDA1, RESERVED, SPI0_CE1_N, SD_CARD_VOLT),
                new BCM2711Pin(cpu, 45, PWM0_1, SCL0, SCL1, RESERVED, SPI0_CE2_N, SD_CARD_PW_R0),
                new BCM2711Pin(cpu, 46, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 47, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 48, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 49, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 50, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 51, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 52, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 53, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 54, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 55, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 56, INTERNAL, N_A, N_A, N_A, N_A, N_A),
                new BCM2711Pin(cpu, 57, INTERNAL, N_A, N_A, N_A, N_A, N_A),

        };

        return new ArrayList<>(Arrays.asList(pins));
    }
}
