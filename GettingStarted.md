#Getting started

##  Main classes

#### General
```
ru.iothub.jef.linux.core.RuntimePermissionsChecker.check(); 
```
This class automatically called by a framework but if you want to see all 
dependencies when you project starts - 
please call this method in 1st line of you program in development cycle. 
JEF try to validate you system configuration and propose some necessary 
changes like permissions/roles.

### SPI interface

```
ru.iothub.jef.linux.spi.SpiBus
```
Provides access to SPI bus. All methods described in javadoc.

### I2C interface
```
ru.iothub.jef.linux.i2c.I2CBus
```
Provides access to SPI bus. All methods described in javadoc.
Best method to work with I2C over **#select** method. In this case bus 
will automatically lock and select your address when you start any
read/write operation for device in this address

### Serial Bus

```
ru.iothub.jef.linux.serial.SerialPort
```
Provides access via classical **java.io.InputStream** and **java.io.OutputStream** to serial buses aka USB/RS-232/etc. All methods described in javadoc.

### One Wire

Please look to the driver implementation in
```
ru.iothub.jef.devices.library.maximintegrated.DS18B20
```
Access to this bus can be implemented over standart linux read/write operations

###GPIO
Please look to the GPIO section in the examples.


## Examples

Please keep in mind what examples can requre modifications to your platform.
Some paths or pins can be others in different platforms or configured by different ways.

### Gpio
* [Read current PINs functions](examples/src/main/java/ru/iothub/jef/examples/gpio/GpioPinFunctions.java)
* [Read all PIN functions](examples/src/main/java/ru/iothub/jef/examples/gpio/GpioReadAll.java)
* [Blink LED](examples/src/main/java/ru/iothub/jef/examples/misc/Blink.java)

### I2C
* [Scan available I2C devices in bus](examples/src/main/java/ru/iothub/jef/examples/i2c/I2CScanner.java)
* [Read BCM280 sensor data](examples/src/main/java/ru/iothub/jef/examples/i2c/BCM280Example.java)

### SPI
* [Erase/Read/Write Winbond W25x flash](examples/src/main/java/ru/iothub/jef/examples/spi/W25xFlashExample.java)

### Serial Bus
* [Read data from USB port](examples/src/main/java/ru/iothub/jef/examples/serial/SerialExample.java)

### One Wire
* [Read DS18B20 sensor data](examples/src/main/java/ru/iothub/jef/examples/onewire/DS18B20Example.java)

