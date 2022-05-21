 

package ru.iothub.jef.examples;

import ru.iothub.jef.examples.bluetooth.SimpleScanExample;
import ru.iothub.jef.examples.gpio.GpioPinFunctions;
import ru.iothub.jef.examples.gpio.GpioReadAll;
import ru.iothub.jef.examples.i2c.BCM280Example;
import ru.iothub.jef.examples.i2c.I2CScanner;
import ru.iothub.jef.examples.misc.Blink;
import ru.iothub.jef.examples.misc.BlinkAll;
import ru.iothub.jef.examples.onewire.DS18B20Example;
import ru.iothub.jef.examples.serial.RAK3172Example;
import ru.iothub.jef.examples.serial.RAK3172ExampleUSB;
import ru.iothub.jef.examples.serial.SerialExample;
import ru.iothub.jef.examples.spi.W25xFlashExample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Static holder for examples
 */
class ExampleManager {
    private final static List<ExampleGroup> groups = Collections.unmodifiableList(new ArrayList<ExampleGroup>() {
        {
            add(new ExampleGroup("General") {
                {
                    add(new ShowBoardInfoExample());
                    add(new GpioPinFunctions());
                    add(new GpioReadAll());

                }
            });

            add(new ExampleGroup("GPIO") {
                {
                    add(new Blink());
                    add(new BlinkAll());
                }
            });

            add(new ExampleGroup("I2C") {
                {
                    add(new I2CScanner());
                    add(new BCM280Example());
                }
            });

            add(new ExampleGroup("SPI") {
                {
                    add(new W25xFlashExample());
                }
            });

            add(new ExampleGroup("1-Wire") {
                {
                    add(new DS18B20Example());
                }
            });

            add(new ExampleGroup("Serial") {
                {
                    add(new SerialExample());
/*                    add(new RAK3172Example());
                    add(new RAK3172ExampleUSB());*/
                }
            });

            /*add(new ExampleGroup("Bluetooth") {
                {
                    add(new SimpleScanExample());
                }
            });*/
        }
    });

    static List<ExampleGroup> getExamples() {
        return groups;
    }

    private static Example getExample(int index) {
        int x = 0;
        for (ExampleGroup group : groups) {
            for (Example example : group.getItems()) {
                if (x == index) {
                    return example;
                }
                x++;
            }

        }
        return null;
    }

    static void execute(int index) throws Exception {
        Example example = getExample(index);
        if (example != null) {
            example.init();
            example.showIntro();
            example.execute();
        }
    }
}
