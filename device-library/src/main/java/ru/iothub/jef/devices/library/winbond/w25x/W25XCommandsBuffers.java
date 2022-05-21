 

package ru.iothub.jef.devices.library.winbond.w25x;

import ru.iothub.jef.linux.spi.SpiInputParams;

/**
 * Wrapper for register commands sets based on specification
 * Please see datasheet for details
 */
class W25XCommandsBuffers {
    public static SpiInputParams B_CMD_READ_UNIQUE_ID = SpiInputParams
            .allocate(5)
            .put(W25XCommands.CMD_READ_UNIQUE_ID.value)
            .putInt(0);

    public static SpiInputParams B_CMD_JEDEC_ID = SpiInputParams.
            allocate(1)
            .put(W25XCommands.CMD_JEDEC_ID.value);

    public static SpiInputParams B_CMD_READ_STATUS_R1 = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_READ_STATUS_R1.value);

    public static SpiInputParams B_CMD_POWER_DOWN = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_POWER_DOWN.value);

    public static SpiInputParams B_CMD_RELEASE_PDOWN_ID = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_RELEASE_PDOWN_ID.value);

    public static SpiInputParams B_CMD_WRIRE_ENABLE = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_WRIRE_ENABLE.value);

    public static SpiInputParams B_CMD_WRITE_DISABLE = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_WRITE_DISABLE.value);

    public static SpiInputParams B_CMD_CHIP_ERASE = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_CHIP_ERASE.value);

    public static SpiInputParams B_CMD_BLOCK_ERASE64KB = SpiInputParams
            .allocate(1)
            .put(W25XCommands.CMD_BLOCK_ERASE64KB.value);
}
