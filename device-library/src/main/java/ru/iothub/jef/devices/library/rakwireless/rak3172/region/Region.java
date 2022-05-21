

package ru.iothub.jef.devices.library.rakwireless.rak3172.region;

import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;

import java.io.IOException;

public abstract class Region<T extends Region<T>> {
    protected final RAK3172 rak;

    public Region(RAK3172 rak) {
        this.rak = rak;
    }

    public abstract int getCode();

    public void apply(RAK3172 rak) throws IOException {
        Rak3172ATCommands commands = rak.getCommands();
        commands.AT_BAND().set(getCode());
    }

    public static Region<?> create(RAK3172 rak) throws IOException {
        Rak3172ATCommands commands = rak.getCommands();
        int code = Integer.parseInt(commands.AT_BAND().get());

        Region<?> result;

        switch (code) {
            case EU433.CODE:
                result = new EU433(rak);
                break;
            case CN470.CODE:
                result = new CN470(rak);
                break;
            case RU864.CODE:
                result = new RU864(rak);
                break;
            case IN865.CODE:
                result = new IN865(rak);
                break;
            case EU868.CODE:
                result = new IN865(rak);
                break;
            case US915.CODE:
                result = new US915(rak);
                break;
            case AU915.CODE:
                result = new AU915(rak);
                break;
            case KR920.CODE:
                result = new KR920(rak);
                break;
            case AS923.CODE:
                result = new AS923(rak);
                break;
            default:
                throw new IOException("Unknown device region: " + code);
        }
        result.read(rak);
        return result;
    }

    protected void read(RAK3172 rak) throws IOException {

    }
}
