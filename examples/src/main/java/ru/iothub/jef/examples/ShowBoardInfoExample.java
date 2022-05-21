 

package ru.iothub.jef.examples;

import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;

public class ShowBoardInfoExample implements Example {
    private Board board;

    @Override
    public String getName() {
        return "Show board info";
    }

    @Override
    public void init() throws Exception {
        board = BoardManager.getBoard();
    }

    @Override
    public void execute() throws Exception {
        System.out.println("Board is '" + board.getBoardInfo() + "'");
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("This example show current board version");
    }
}
