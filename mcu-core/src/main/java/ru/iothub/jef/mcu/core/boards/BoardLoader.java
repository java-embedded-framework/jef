

package ru.iothub.jef.mcu.core.boards;

import java.io.IOException;
import java.util.Properties;

public interface BoardLoader {
    boolean accept();
    Board create() throws IOException;
}
