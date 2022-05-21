

package ru.iothub.jef.linux.core;

@SuppressWarnings("unused")
public interface SmBusConstants {
    long I2C_PEC = 0x0708L;
    long I2C_SMBUS = 0x0720L;

    byte I2C_SMBUS_READ = 1;
    byte I2C_SMBUS_WRITE = 0;

    byte I2C_SMBUS_QUICK = 0;
    byte I2C_SMBUS_BYTE = 1;
    byte I2C_SMBUS_BYTE_DATA = 2;
    byte I2C_SMBUS_WORD_DATA = 3;
    byte I2C_SMBUS_PROC_CALL = 4;
    byte I2C_SMBUS_BLOCK_DATA = 5;
    byte I2C_SMBUS_I2C_BLOCK_BROKEN = 6;
    byte I2C_SMBUS_BLOCK_PROC_CALL = 7;
    byte I2C_SMBUS_I2C_BLOCK_DATA = 8;

    int I2C_SMBUS_BLOCK_MAX = 32;
    int BUFFER_SIZE = I2C_SMBUS_BLOCK_MAX + 2;
}
