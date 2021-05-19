package ru.iothub.jef.examples.gpio;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardPin;
import ru.iothub.jef.mcu.core.boards.rpi.RaspberryPi4B;

public class GpioReadAll implements Example {
    private RaspberryPi4B board;

    private static void showPinsStatus(Board board) {
        int count = board.getPinCount();
        showHeader();

        for (int i = 1; i <= count; i += 2) {
            writePinsLine(board, i);
        }
        showHeader();
    }

    private static void showHeader() {
        System.out.print(" +-----+----------+------------------+---+----++----+---+------------------+----------+-----+\n");
        System.out.print(" | BCM |   Name   |       Mode       | V | Physical | V |       Mode       |   Name   | BCM |\n");
        System.out.print(" +-----+----------+------------------+---+----++----+---+------------------+----------+-----+\n");
    }

    private static void writePinsLine(Board board, int i) {
        BoardPin pin = board.getPin(i);
        String cpuPin = pinToString(pin.getCpuPinNumber());
        System.out.printf(" | %3s | %8s | %16s | %1s | %2d |",
                cpuPin,
                pin.getName(),
                pin.getPinFunctionName(),
                getPinInOut(pin.getPinInfo()),
                pin.getPinNumber()
                /*listToComaSeparatedString(pin.getPingFunctionNames())*/
        );
        pin = board.getPin(i + 1);
        cpuPin = pinToString(pin.getCpuPinNumber());
        System.out.printf("| %2d | %1s | %16s | %8s | %3s |\n",
                pin.getPinNumber(),
                getPinInOut(pin.getPinInfo()),
                pin.getPinFunctionName(),
                pin.getName(),
                cpuPin
                /*listToComaSeparatedString(pin.getPingFunctionNames())*/
        );
    }

    private static String pinToString(int pin) {
        return pin == -1 ? "-" : Integer.toString(pin);
    }

    private static String getPinInOut(BoardPin.BoardPinInfo val) {
        if (val.isDummyPin()) return "-";
        if (val.isActiveLow()) return "0";
        return "1";
        /*switch (val) {
            case HIGH:
                return "1";
            case LOW:
                return "0";
            default:
                return "-";
        }*/
    }

    @Override
    public String getName() {
        return "Read current GPIO board values";
    }

    @Override
    public void init() throws Exception {
        board = new RaspberryPi4B();
    }

    @Override
    public void execute() throws Exception {
        showPinsStatus(board);
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("This example show current GPIO status");
    }

}
