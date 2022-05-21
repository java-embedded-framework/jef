

package ru.iothub.jef.examples.serial;

import ru.iothub.jef.devices.library.rakwireless.rak3172.ATCommand;
import ru.iothub.jef.devices.library.rakwireless.rak3172.LoRaWANDeviceConfiguration;
import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.serial.SerialPort;

public abstract class RAK3172ExampleBaseline implements Example {
    public abstract SerialPort getSerial() throws NativeIOException;

    @Override
    public void execute() throws Exception {
        SerialPort port = getSerial();
        //SerialPort port = new SerialPort("/dev/ttyUSB0", SerialBaudRate.B9600);

        LoRaWANDeviceConfiguration conf = new LoRaWANDeviceConfiguration()
                .withClassA();

        RAK3172 rak = new RAK3172(port, conf);
        Rak3172ATCommands commands = rak.getCommands();
        run(commands.AT());
        run(commands.AT());
        run(commands.AT());
        run(commands.AT());
        get(commands.AT_SN());
    }

    private void get(ATCommand command) {
        try {
            command.get();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

    }

    protected void run(ATCommand command) {
        try {
            command.run();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
