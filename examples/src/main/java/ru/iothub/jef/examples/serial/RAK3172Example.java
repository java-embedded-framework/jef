 

package ru.iothub.jef.examples.serial;

import ru.iothub.jef.devices.library.rakwireless.rak3172.LoRaWANDeviceConfiguration;
import ru.iothub.jef.devices.library.rakwireless.rak3172.RAK3172;
import ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172ATCommands;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Sys;
import ru.iothub.jef.linux.serial.SerialBaudRate;
import ru.iothub.jef.linux.serial.SerialPort;

import java.io.IOException;

public class RAK3172Example extends RAK3172ExampleBaseline {
    @Override
    public String getName() {
        return "RAK3172 communication";
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public SerialPort getSerial() throws NativeIOException {
        return new SerialPort("/dev/ttyDUMMY", SerialBaudRate.B9600);
    }



    @Override
    public void showIntro() {

    }
}
