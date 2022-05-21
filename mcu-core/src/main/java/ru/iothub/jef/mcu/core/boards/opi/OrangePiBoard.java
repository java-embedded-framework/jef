 

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// http://linux-sunxi.org/Xunlong_Orange_Pi
class OrangePiBoard extends OrangePiAbstractBoard {
    public OrangePiBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "PB21", getBoardPin('B', 21)), // TWI2-SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "PB20", getBoardPin('B', 20)), // TWI2-SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "PI3", getBoardPin('I', 3)),
                new BoardPin(8, "PH0", getBoardPin('H', 0)), // UART3_TX
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "PH1", getBoardPin('H', 1)), // UART3_RX

                new BoardPin(11, "PI19", getBoardPin('I', 19)), // UART2_RX
                new BoardPin(12, "PH2", getBoardPin('H', 2)),
                new BoardPin(13, "PI18", getBoardPin('I', 18)), // UART2_TX
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "PI17", getBoardPin('I', 17)), // UART2_CTS
                new BoardPin(16, "PH20", getBoardPin('H', 20)), // CAN_TX
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "PH21", getBoardPin('H', 21)), // CAN_RX
                new BoardPin(19, "PI12", getBoardPin('I', 12)), // SPI0_MOSI
                new BoardPin(20, "Ground", null),

                new BoardPin(21, "PI13", getBoardPin('I', 13)), // SPI0_MISO
                new BoardPin(22, "PI16", getBoardPin('I', 16)), // UART2_RTS
                new BoardPin(23, "PI11", getBoardPin('I', 11)), // SPI0_CLK
                new BoardPin(24, "PI10", getBoardPin('I', 10)), // SPI0_CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "PI14", getBoardPin('I', 14)), // SPI0_CS1
        };
        return Collections.unmodifiableList(Arrays.asList(pins2x13));
    }
}
