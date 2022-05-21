 

package ru.iothub.jef.examples.spi;

import ru.iothub.jef.devices.library.winbond.w25x.W25xFlash;
import ru.iothub.jef.examples.Example;
import ru.iothub.jef.examples.ExampleExecutor;
import ru.iothub.jef.linux.spi.SpiBus;
import ru.iothub.jef.linux.spi.SpiMode;
import ru.iothub.jef.mcu.core.boards.Board;
import ru.iothub.jef.mcu.core.boards.BoardManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import static ru.iothub.jef.linux.core.LinuxUtils.dump;

public class W25xFlashExample implements Example {
    @Override
    public String getName() {
        return "Winbound W25x Flash Example";
    }

    @Override
    public void init() throws Exception {
        System.out.println("Please connect W25x to SPI-0 and press <enter> to continue");
        ExampleExecutor.readLine();
    }

    @Override
    public void execute() throws Exception {
        Board board = BoardManager.getBoard();
        List<SpiBus> spiBuses = board.getSpiBuses();
        if(spiBuses.size() == 0) {
            throw new IOException("I2C buses not enabled");
        }

        SpiBus bus0 = spiBuses.get(0);//new SpiBus(0, 500000, SpiMode.SPI_MODE_3, 8, 0);
        W25xFlash flash = new W25xFlash(bus0);

        int manufacturerID = flash.getManufacturerID();
        int memoryType = flash.getMemoryType();
        int capacity = flash.getCapacity();
        String chipID = flash.getChipID();
        boolean busy = flash.isBusy();
        System.out.println("manufacturerID = 0x" + Integer.toHexString(manufacturerID));
        System.out.println("memoryType = 0x" + Integer.toHexString(memoryType));
        System.out.println("capacity = 0x" + Integer.toHexString(capacity));
        System.out.println("chipID = " + chipID);
        System.out.println("busy = " + busy);

        System.out.println("Erase flash");
        flash.eraseAll();

        ByteBuffer read;
        byte[] write = new byte[]{100, 101, 102, 103, 104, 105, 105, 104, 103, 102, 101, 100};

        read = flash.read(1, write.length);
        dump("read empty flash", read);

        System.out.println();
        System.out.println("Write 12 bytes");

        flash.pageWrite(0, 1, write);

        System.out.println();
        System.out.println("Read 12 bytes");
        read = flash.read(1, write.length);
        dump("read", read);

        System.out.println();
        System.out.println("Fast Read 12 bytes");

        read = flash.fastRead(1, write.length);
        dump("fastread", read);

        System.out.println("Please press <enter> to return to menu");
        ExampleExecutor.readLine();
    }

    @Override
    public void showIntro() {
        System.out.println("Show W25x read/write functions");
    }}
