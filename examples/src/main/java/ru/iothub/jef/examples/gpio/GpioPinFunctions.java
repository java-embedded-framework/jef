package ru.iothub.jef.examples.gpio;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;
import ru.iothub.jef.mcu.core.boards.BoardPin;

import java.io.IOException;

public class GpioPinFunctions implements Example {
    private Board board;

    private static void showPinsFunctions(Board board) throws IOException {
        int count = board.getPinCount();
        showHeader();
        for (int i = 1; i <= count; i++) {
            writePin(board, i);
        }
        showHeader();
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    private static void writePin(Board board, int i) {
        BoardPin pin = board.getPin(i);
        String cpuPin = pinToString(pin.getCpuPinNumber());
        String functionName = pin.getPinFunctionName();

        String pinInOut = getPinInOut(pin.getPinInfo());
        System.out.printf(" | %2d | %3s | %8s | %16s | %1s |\n",
                i,
                cpuPin,
                pin.getName(),
                functionName,
                pinInOut
        );
    }

    private static String pinToString(int pin) {
        return pin == -1 ? "-" : Integer.toString(pin);
    }

    private static void showHeader() {
        System.out.print(" +----+-----+----------+------------------+---+\n");
        System.out.print(" | Pi | CPU |   Name   |        Mode      | V |\n");
        System.out.print(" +----+-----+----------+------------------+---+\n");
    }

    private static String getPinInOut(BoardPin.BoardPinInfo val) {
        if (val.isDummyPin()) return "-";
        if (val.isActiveLow()) return "0";
        return "1";
    }

    @Override
    public void showIntro() {
        System.out.println("This example show current board pin functions");
    }

    @Override
    public String getName() {
        return "Show GPIO functions";
    }

    @Override
    public void init() throws Exception {
        board = BoardManager.getBoard();
    }

    @Override
    public void execute() throws Exception {
        showPinsFunctions(board);
    }
}
