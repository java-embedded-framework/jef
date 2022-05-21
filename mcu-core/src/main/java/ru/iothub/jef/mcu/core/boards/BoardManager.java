 

package ru.iothub.jef.mcu.core.boards;

import java.io.IOException;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public class BoardManager {
    private static Board instance = null;
    private static final AtomicBoolean init = new AtomicBoolean(false);

    public static Board getBoard() throws IOException {
        if (!init.get() && instance == null) {
            synchronized (BoardManager.class) {
                if (!init.get() && instance == null) {
                    instance = initBoard();
                    init.set(true);
                }
            }
        }
        return instance;
    }

    private static Board initBoard() throws IOException {
        ServiceLoader<BoardLoader> sl = ServiceLoader.load(BoardLoader.class);
        for (BoardLoader bl : sl) {
            if (bl.accept()) {
                return bl.create();
            }
        }
        throw new IOException("Can't identify board");
    }
}
