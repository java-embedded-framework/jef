package ru.iothub.jef.examples.gpio;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.gpio.Pin;
import ru.iothub.jef.mcu.core.boards.BoardPin;
import ru.iothub.jef.mcu.core.boards.rpi.RaspberryPi4B;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class GpioPinFunctions implements Example {
    private RaspberryPi4B board;

    private static void showPinsFunctions(RaspberryPi4B board) throws IOException {
        int count = board.getPinCount();
        showHeader();
        for (int i = 1; i <= count; i++) {
            writePin(board, i);
        }
        showHeader();
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("This example show current board pin functions");
    }

    private static void writePin(RaspberryPi4B board, int i) {
        BoardPin pin = board.getPin(i);
        String cpuPin = pinToString(pin.getCpuPinNumber());
        System.out.printf(" | %2d | %3s | %8s | %16s | %1s | %-88s |\n",
                i,
                cpuPin,
                pin.getName(),
                pin.getPinFunctionName(),
                getPinInOut(pin.digitalRead()),
                listToComaSeparatedString(new ArrayList<>()/*pin.getPingFunctionNames()*/)
        );
    }

    private static String pinToString(int pin) {
        return pin == -1 ? "-" : Integer.toString(pin);
    }

    private static void showHeader() {
        System.out.print(" +----+-----+----------+------------------+---+------------------------------------------------------------------------------------------+\n");
        System.out.print(" | Pi | BCM |   Name   |        Mode      | V |                                        Functions                                         |\n");
        System.out.print(" +----+-----+----------+------------------+---+------------------------------------------------------------------------------------------+\n");
    }

    private static String listToComaSeparatedString(List<String> names) {
        StringJoiner joiner = new StringJoiner(" : ");
        for (String item : names) {
            joiner.add(item);
        }
        return joiner.toString();
    }

    private static String getPinInOut(Pin.State val) {
        switch (val) {
            case LOCKED:
                return "-";
            case LOW:
                return "0";
            default:
                return "1";
        }
    }

    @Override
    public String getName() {
        return "Show GPIO functions";
    }

    @Override
    public void init() throws Exception {
        board = new RaspberryPi4B();
    }

    @Override
    public void execute() throws Exception {
        showPinsFunctions(board);
    }
}
