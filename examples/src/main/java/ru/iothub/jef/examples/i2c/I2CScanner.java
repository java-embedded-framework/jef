

package ru.iothub.jef.examples.i2c;

import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.i2c.I2CBus;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;

import java.io.IOException;
import java.util.List;

public class I2CScanner implements Example {
    private I2CBus bus;

    @Override
    public String getName() {
        return "Show available I2C Devices";
    }

    @Override
    public void init() throws Exception {
        Board board = BoardManager.getBoard();
        List<I2CBus> i2CBuses = board.getI2CBuses();
        if(i2CBuses.size() == 0) {
            throw new IOException("I2C buses not enabled");
        }
        bus = i2CBuses.get(0);
    }

    @Override
    public void execute() throws Exception {
        List<I2CBus.Status> enumerate = bus.enumerate();
        System.out.println("    00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F");
        for (int i=0;i<128;i+=16) {
            System.out.printf("%02x: ", i);
            for(int j=0;j<16;j++) {
                I2CBus.Status status = enumerate.get(i+j);
                if(status == I2CBus.Status.AVAILABLE) {
                    System.out.printf("%02x ", i+j);
                } else {
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Show I2C devices what available in I2C map");
    }
}
