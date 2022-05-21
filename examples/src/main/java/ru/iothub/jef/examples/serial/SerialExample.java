

package ru.iothub.jef.examples.serial;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.linux.serial.SerialBaudRate;
import ru.iothub.jef.linux.serial.SerialPort;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SerialExample implements Example {
    @Override
    public String getName() {
        return "Serial port communication";
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void execute() throws Exception {
        SerialPort port = new SerialPort("/dev/ttyUSB0", SerialBaudRate.B115200);

        InputStream is = port.getInputStream();
        for (int loop=0;loop<10;loop++) {
            int available = is.available();
            if (available > 0) {
                byte[] array = new byte[available];
                for (int i = 0; i < available; i++) {
                    array[i] = (byte) is.read();
                }
                String str = new String(array, StandardCharsets.US_ASCII);
                System.out.println("Read from port: '" + str + "'");
            }
        }
    }

    @Override
    public void showIntro() {
        System.out.println("Read 10 times from Serial Port");
    }
}
