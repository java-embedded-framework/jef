 

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Page 12 - https://linux-sunxi.org/images/6/6c/ORANGEPI-Winplus-V1_3.pdf
class OrangePiWinBoard extends OrangePiAbstractBoard {
    public OrangePiWinBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x20 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "TWI1-SDA", getBoardPin('H', 3)), // TWI1-SDA/PH3
                new BoardPin(4, "5V", null),
                new BoardPin(5, "TWI1-SCK", getBoardPin('H', 2)), // TWI1-SCK/PH2
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "IO-GCLK", getBoardPin('L', 10)), // S_PWM/PL10
                new BoardPin(8, "TXD0", getBoardPin('L', 2)), // S_UART_TX/PL2
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "RXD0", getBoardPin('L', 3)),  // S_UART_RX/PL3
                new BoardPin(11, "IO-0", getBoardPin('H', 5)), // UART3_RX/PH5
                new BoardPin(12, "IO-1", getBoardPin('D', 4)), // PD4
                new BoardPin(13, "IO-2", getBoardPin('H', 4)), // UART3_TX/PH4
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "IO-3", getBoardPin('H', 7)), // UART3_CTS/PH7
                new BoardPin(16, "IO-4", getBoardPin('L', 9)), // PL9
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "IO-5", getBoardPin('C', 4)), // PC4
                new BoardPin(19, "SPI-MOSI", getBoardPin('D', 2)),  // SPI1_MOSI/PD2
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "SPI-MISO", getBoardPin('D', 3)),  // SPI1-MISO/PD3
                new BoardPin(22, "IO-6", getBoardPin('H', 6)), // UART3_RTS/PH6
                new BoardPin(23, "SPI_CLK", getBoardPin('D', 1)), // SPI1_CLK/PD1
                new BoardPin(24, "SPI-CE0", getBoardPin('D', 0)), // SPI1_CS0
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "SPI-CE1", getBoardPin('D', 6)), // PD6
                new BoardPin(27, "ID-SD", getBoardPin('E', 15)), // TWI2-SDA/PE15
                new BoardPin(28, "ID-SC", getBoardPin('E', 14)), // TWI2-SCK/PE14
                new BoardPin(29, "IO-7", getBoardPin('B', 4)), // PB4
                new BoardPin(30, "Ground",null),
                new BoardPin(31, "IO-8", getBoardPin('B', 5)), // PB5
                new BoardPin(32, "IO-9", getBoardPin('B', 2)), // UART2_RTS/PB2
                new BoardPin(33, "IO-10", getBoardPin('B', 6)), // PB6
                new BoardPin(34, "Ground", null),
                new BoardPin(35, "IO-12", getBoardPin('B', 7)), // PB7
                new BoardPin(36, "IO-13", getBoardPin('B', 3)), // UART2_CTS/PB3
                new BoardPin(37, "IO-14", getBoardPin('D', 5)), // PD5
                new BoardPin(38, "IO-15", getBoardPin('B', 0)), // UART2_TX
                new BoardPin(39, "Ground", null),
                new BoardPin(40, "IO-16", getBoardPin('B', 1)), // UART2_RX

        };
        return Collections.unmodifiableList(Arrays.asList(pins2x20));
    }
}
