 

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// http://linux-sunxi.org/Xunlong_Orange_Pi_Mini
class OrangePiMiniBoard extends OrangePiBoard {
    public OrangePiMiniBoard() throws IOException {
    }

    @Override
    protected List<BoardPin> initGPIO() throws IOException {
        // Mini board have the same 26 pins from OPi + 27-40
        BoardPin[] pins = {
                new BoardPin(27, "PB5", getBoardPin('B', 8)), // I2S_MCLK
                new BoardPin(28, "PI12", getBoardPin('I', 12)),
                new BoardPin(29, "PB6", getBoardPin('B', 6)), // I2S_BCLK
                new BoardPin(30, "Ground", null),
                new BoardPin(31, "PB7", getBoardPin('B', 7)), // I2S_LRCK
                new BoardPin(32, "PI20", getBoardPin('I', 20)), // UART7_TX
                new BoardPin(33, "PB8", getBoardPin('B', 8)), // I2S_DO0
                new BoardPin(34, "Ground", null),
                new BoardPin(35, "PB12", getBoardPin('B', 12)), // I2S_DI
                new BoardPin(36, "PI21", getBoardPin('I', 21)), // UART7_RX
                new BoardPin(37, "PB13", getBoardPin('B', 13)), // SDPIF_D0
                new BoardPin(38, "PH3", getBoardPin('H', 3)),
                new BoardPin(39, "Ground", null),
                new BoardPin(40, "PH5", getBoardPin('H', 5)),
        };
        ArrayList<BoardPin> result = new ArrayList<>();
        result.addAll(super.initGPIO());
        result.addAll(Arrays.asList(pins));
        return result;
    }
}
