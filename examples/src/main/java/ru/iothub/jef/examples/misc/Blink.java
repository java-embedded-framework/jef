 

package ru.iothub.jef.examples.misc;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.gpio.GpioManager;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;

public class Blink implements Example {
    public static final int PIN_NUMBER = 40;
    public static final int DELAY_TIME_MS = 1000;

    private Board board;

    @Override
    public String getName() {
        return "Blink LED in PIN " + PIN_NUMBER;
    }

    @Override
    public void init() throws Exception {
        board = BoardManager.getBoard();
        System.out.println("Please connect LED to PIN-" + PIN_NUMBER + " press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        String path = "/dev/gpiochip0";
        int pinNumber = 21;
        GpioPin pin = GpioManager.getPin(path, pinNumber);
        pin.setDirection(GpioPin.Direction.OUTPUT);

        boolean active = true;

        for (int i = 0; i < 10; i++) {
            System.out.println("Blinking... " + (active ? "ON" : "OFF"));
            pin.write(active ? GpioPin.State.HIGH : GpioPin.State.LOW);

            active = !active;
            Thread.sleep(DELAY_TIME_MS);
        }

        System.out.println("Please press <enter> to return to menu");

        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Blinking LED 10 times in RPI Pin " + PIN_NUMBER);
    }
}
