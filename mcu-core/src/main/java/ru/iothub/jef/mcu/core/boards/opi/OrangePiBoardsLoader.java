 

package ru.iothub.jef.mcu.core.boards.opi;

import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OrangePiBoardsLoader implements BoardLoader {
    private final static String BOARD_NAME_KEY = "BOARD_NAME";
    private final static String BOARD_FAMILY_KEY = "BOARDFAMILY";
    private final static String ARMBIAN_RELEASE = "/etc/armbian-release";


    private final static Map<String, OrangePiBoardInfo> mapping = new HashMap<String, OrangePiBoardInfo>() {
        {
            putValue(OrangePiBoardInfo.ORANGE_PI);
            putValue(OrangePiBoardInfo.ORANGE_PI_R1);
            putValue(OrangePiBoardInfo.ORANGE_PI_R1_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_RK3399);
            putValue(OrangePiBoardInfo.ORANGE_PI_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_3);
            putValue(OrangePiBoardInfo.ORANGE_PI_4);
            putValue(OrangePiBoardInfo.ORANGE_PI_LITE);
            putValue(OrangePiBoardInfo.ORANGE_PI_LITE_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_MINI);
            putValue(OrangePiBoardInfo.ORANGE_PI_ONE);
            putValue(OrangePiBoardInfo.ORANGE_PI_ONE_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_PC);
            putValue(OrangePiBoardInfo.ORANGE_PI_PC_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_PC_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_PLUS_2E);
            putValue(OrangePiBoardInfo.ORANGE_PI_PRIME);
            putValue(OrangePiBoardInfo.ORANGE_PI_WIN);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_PLUS);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_PLUS_2);
            putValue(OrangePiBoardInfo.ORANGE_PI_ZERO_PLUS_2A);
        }

        private void putValue(OrangePiBoardInfo orangePi) {
            put(key(orangePi.getName(), orangePi.getFamily()), orangePi);
        }
    };

    private final Properties props;

    public OrangePiBoardsLoader() throws IOException {
        props = new Properties();
        try (InputStreamReader is = new InputStreamReader(new FileInputStream(ARMBIAN_RELEASE))) {
            props.load(is);
        }
    }

    @Override
    public boolean accept() {
        String key = key(props.getProperty(BOARD_NAME_KEY), props.getProperty(BOARD_FAMILY_KEY));
        return mapping.containsKey(key);
    }

    @Override
    public Board create() throws IOException {
        String key = key(props.getProperty(BOARD_NAME_KEY), props.getProperty(BOARD_FAMILY_KEY));
        OrangePiBoardInfo info = mapping.get(key);
        Class<? extends OrangePiAbstractBoard> provider = info.getProvider();
        try {
            OrangePiAbstractBoard board = provider.getDeclaredConstructor().newInstance();
            board.setBoardInfo(info);
            return board;
        } catch (Exception e) {
            throw new IOException("board provider not implemented for " + info.getName());
        }
    }

    private static String key(String name, String family) {
        return filter(name) + "-" + filter(family);
    }

    private static String filter(String s) {
        if(s.startsWith("\"")) {
            s = s.substring(1);
        }
        if(s.endsWith("\"")) {
            s = s.substring(0, s.length()-1);
        }
        return s;
    }
}
