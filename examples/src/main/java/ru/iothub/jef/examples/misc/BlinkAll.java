 

package ru.iothub.jef.examples.misc;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.gpio.GpioPin;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;
import ru.iothub.jef.mcu.core.boards.BoardPin;
import ru.iothub.jef.mcu.core.boards.BoardPinState;

import java.util.List;

public class BlinkAll implements Example {
    private Board board;
    public static final int DELAY_TIME_MS = 1000;

    @Override
    public String getName() {
        return "Blink all pins";
    }

    @Override
    public void init() throws Exception {
        board = BoardManager.getBoard();
        System.out.println("Please press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        List<BoardPin> pins = board.getPins();
        int number = 0;

        for (BoardPin pin : pins) {
            number++;
            if (pin.isDummyPin()) {
                System.out.println("pin['"+number+"'] is not functional pin. Skipped.");
                Thread.sleep(DELAY_TIME_MS);
                continue;
            }

            if(pin.getPinInfo().isUsedByKernel()) {
                System.out.println("pin['"+number+"'] is used by kernel. Skipped.");
                Thread.sleep(DELAY_TIME_MS);
                continue;
            }

            boolean on = false;
            pin.pinMode(GpioPin.Direction.OUTPUT);
            for (int i = 0; i < 10; i++) {
                System.out.println("Blinking pin['"+number+"']... " + (on ? "ON" : "OFF"));
                pin.digitalWrite(on ? BoardPinState.HIGH : BoardPinState.LOW);
                on = !on;
                Thread.sleep(DELAY_TIME_MS);
            }

        }
    }

    @Override
    public void showIntro() {
        System.out.println("Blinking LED 10 times by every pin");
    }
}
