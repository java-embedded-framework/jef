 

package ru.iothub.jef.examples.onewire;

import ru.iothub.jef.devices.library.maximintegrated.DS18B20;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;

public class DS18B20Example implements Example {
    private DS18B20 sensor;

    @Override
    public String getName() {
        return "DS18B20 Temperature Sensor";
    }

    @Override
    public void init() throws Exception {
        //TODO add find in path
        sensor = new DS18B20("/sys/bus/w1/devices/28-01203882217a/w1_slave");
        System.out.println("Please connect DS18B20 to GPIO Pin 4 (Board Pin - 7) and press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        for (int i = 0; i < 10; i++) {
            double c = sensor.getTemperatureCelsius();
            System.out.printf("temperature: %.2f °C\n", c);
            Thread.sleep(1000);
        }
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Please connect DS18B20 to GPIO4 (Board Pin #7) and press any <enter>");
    }
}
