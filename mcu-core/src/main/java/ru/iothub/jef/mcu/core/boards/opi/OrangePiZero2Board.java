 

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// https://linux-sunxi.org/Xunlong_Orange_Pi_Zero2
class OrangePiZero2Board extends OrangePiAbstractBoard {
    public OrangePiZero2Board() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        BoardPin[] pins2x13 = {
                new BoardPin(1, "3.3v", null),
                new BoardPin(2, "5V", null),
                new BoardPin(3, "PH5", getBoardPin('H', 5)), // I2C3-SDA
                new BoardPin(4, "5V", null),
                new BoardPin(5, "PH4", getBoardPin('H', 4)), // I2C3-SCK
                new BoardPin(6, "Ground", null),
                new BoardPin(7, "PC9", getBoardPin('C', 9)),
                new BoardPin(8, "PH2", getBoardPin('H', 2)), // UART5-TX
                new BoardPin(9, "Ground", null),
                new BoardPin(10, "PH3", getBoardPin('H', 3)),  // UART5-RX
                new BoardPin(11, "PC6", getBoardPin('C', 6)),
                new BoardPin(12, "PC11", getBoardPin('C', 11)),
                new BoardPin(13, "PC5", getBoardPin('C', 5)),
                new BoardPin(14, "Ground", null),
                new BoardPin(15, "PC8", getBoardPin('C', 8)),
                new BoardPin(16, "PC15", getBoardPin('C', 15)),
                new BoardPin(17, "3.3v", null),
                new BoardPin(18, "PC14", getBoardPin('C', 14)),
                new BoardPin(19, "PH7", getBoardPin('H', 7)),  // SPI1-MOSI
                new BoardPin(20, "Ground", null),
                new BoardPin(21, "PH8", getBoardPin('H', 8)),  // SPI1-MISO
                new BoardPin(22, "PC7", getBoardPin('C', 7)),
                new BoardPin(23, "PH6", getBoardPin('H', 6)), // SPI1-SCK
                new BoardPin(24, "PH9", getBoardPin('H', 9)), // SPI1-CS
                new BoardPin(25, "Ground", null),
                new BoardPin(26, "PC10", getBoardPin('C', 10)),
        };

        return Collections.unmodifiableList(Arrays.asList(pins2x13));
    }
}
