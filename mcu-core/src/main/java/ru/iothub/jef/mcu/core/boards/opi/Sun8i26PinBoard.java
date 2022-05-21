 

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


// http://linux-sunxi.org/Xunlong_Orange_Pi_Zero
class Sun8i26PinBoard extends OrangePiAbstractBoard {

    public Sun8i26PinBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "GPIO12", getBoardPin(12)), // 	TWI0_SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "GPIO11", getBoardPin(11)), // 	TWI0_SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "GPIO6", getBoardPin(6)),
                new BoardPin(8, "GPIO198", getBoardPin(198)), // UART1_TX
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "GPIO199", getBoardPin(199)), // UART1_RX
                new BoardPin(11, "GPIO1", getBoardPin(1)), // UART2_RX
                new BoardPin(12, "GPIO7", getBoardPin(7)),
                new BoardPin(13, "GPIO", getBoardPin(0)), // UART2_TX
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "GPIO3", getBoardPin(3)),
                new BoardPin(16, "GPIO19", getBoardPin(19)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "GPIO18", getBoardPin(18)),
                new BoardPin(19, "GPIO15", getBoardPin(15)), // SPI1_MOSI
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "GPIO16", getBoardPin(16)), // SPI1_MISO
                new BoardPin(22, "GPIO2", getBoardPin(2)),
                new BoardPin(23, "GPIO14", getBoardPin(14)), // SPI1_CLK
                new BoardPin(24, "GPIO13", getBoardPin(13)), // SPI1_CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "GPIO10", getBoardPin(10)),
        };

        return Collections.unmodifiableList(Arrays.asList(pins2x13));
    }
}
