

package ru.iothub.jef.linux.spi;

import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.types.IntReference;
import ru.iothub.jef.linux.core.types.SpiIocTransfer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.linux.core.util.StringUtils.dump;

/**
 * The {@link SpiBus} class provides methods for transmitting and receiving data to/from an SPI slave device.
 * On an SPI bus, data is transferred between the SPI master device and an SPI slave device in full duplex. That is, data is transmitted by the SPI master to the SPI slave device at the same time data is received from the SPI slave device by the SPI master.
 *
 */
@SuppressWarnings("unused")
public class SpiBus {
    private static final Logger log = Logger.getLogger(SpiBus.class.getName());

    ThreadLocal<IntReference> intRef = ThreadLocal.withInitial(IntReference::new);

    private final String bus;
    private final FileHandle fd;
    //private int clockFrequency;
    //private final SpiMode clockMode;
    //private int wordLength;
    //private final int bitOrdering;

    private final Ioctl console = Ioctl.getInstance();

    public SpiBus(int busNumber) throws NativeIOException {
        this("/dev/spidev0." + busNumber);
    }

    public SpiBus(String bus) throws NativeIOException {
        this.bus = bus;
        log.log(Level.INFO, () -> String.format("Open SPI Bus '%s'", bus));
        fd = Fcntl.getInstance().open(bus, EnumSet.of(IOFlags.O_RDWR));
    }

    /**
     * Allocates new instance of {@link SpiBus} based on interface ID
     * @param busNumber number of available bus (0, 1, 2, ...)
     * @param clockFrequency working frequency
     * @param clockMode clock mode. see {@link SpiMode}
     * @param wordLength length of word. Typically it's 8-bits.
     * @param bitOrdering bit ordering. 0 - {@link java.nio.ByteOrder#BIG_ENDIAN} or 1 - {@link java.nio.ByteOrder#LITTLE_ENDIAN}
     * @throws NativeIOException if got error from Linux subsystem during initialization
     */
    public SpiBus(int busNumber, int clockFrequency, SpiMode clockMode, int wordLength, int bitOrdering) throws NativeIOException {
        this("/dev/spidev0." + busNumber, clockFrequency, clockMode, wordLength, bitOrdering);
    }

    /**
     * Allocates new instance of {@link SpiBus} based on interface name
     * @param bus path to bus in Linux file system i.e '/dev/spidev0.x'
     * @param clockFrequency working frequency
     * @param clockMode clock mode. see {@link SpiMode}
     * @param wordLength length of word. Typically it's 8-bits.
     * @param bitOrdering bit ordering. 0 - {@link java.nio.ByteOrder#BIG_ENDIAN} or 1 - {@link java.nio.ByteOrder#LITTLE_ENDIAN}
     * @throws NativeIOException if got error from Linux subsystem during initialization
     */
    public SpiBus(String bus, int clockFrequency, SpiMode clockMode, int wordLength, int bitOrdering) throws NativeIOException {
        this(bus);
        log.log(Level.INFO,
                () -> String.format(
                        "Create SPI bus with Bus: '%s' Clock Frequency: '%d' Spi Mode: '%s' Word Length: '%d' Bit Ordering: '%d'",
                        bus, clockFrequency, clockMode, wordLength, bitOrdering)
        );

        //this.clockFrequency = clockFrequency;
        //this.clockMode = clockMode;
        //this.wordLength = wordLength;
        //this.bitOrdering = bitOrdering;

        initSPIHandler(clockFrequency, clockMode, wordLength, bitOrdering);
    }

    /**
     * Path to bus in Linux
     * @return path to bus in Linux file system
     */
    public String getBus() {
        return bus;
    }

    /**
     * {@link FileHandle of bus in Linux file system}
     * @return handler of bus in Linux FS
     */
    public FileHandle getFd() {
        return fd;
    }

    /**
     * Bus clock frequency
     * @return clock frequency
     */
    public int getClockFrequency() throws NativeIOException {
        IntReference arg = intRef.get();
        console.ioctl(fd, console.getSpiIocRdMaxSpeedHz(), arg);
        return arg.getValue();
    }

    public void setClockFrequency(int value) throws NativeIOException {
        IntReference arg = intRef.get();
        arg.setValue(value);
        console.ioctl(fd, console.getSpiIocWrMaxSpeedHz(), arg);
    }

    /**
     * Bus clock mode {@link SpiMode}
     * @return clock mode
     */
    public SpiMode getClockMode() throws NativeIOException {
        IntReference arg = intRef.get();
        console.ioctl(fd, console.getSpiIocRdMode(), arg);
        return SpiMode.valueOf(arg.getValue());
    }

    public void setClockMode(SpiMode clockMode) throws NativeIOException {
        IntReference arg = intRef.get();
        arg.setValue(clockMode.value);
        console.ioctl(fd, console.getSpiIocWrMode(), arg);
    }

    /**
     * SPI Bus word length
     * @return word length
     */
    public int getWordLength() throws NativeIOException {
        IntReference arg = intRef.get();
        console.ioctl(fd, console.getSpiIocRdBitsPerWord(), arg);
        return arg.getValue();
    }

    private void setWordLength(int wordLength) throws NativeIOException {
        IntReference arg = intRef.get();
        arg.setValue(wordLength);
        console.ioctl(fd, console.getSpiIocWrBitsPerWord(), arg);
    }

    /**
     * SPI bus bit ordering. 0 - {@link java.nio.ByteOrder#BIG_ENDIAN} or 1 - {@link java.nio.ByteOrder#LITTLE_ENDIAN}
     * @return bus bit ordering
     */
    public int getBitOrdering() throws NativeIOException {
        IntReference arg = intRef.get();
        //SPI_IOC_RD_LSB_FIRST
        console.ioctl(fd, console.getSpiIocRdLsbFirst(), arg);
        return arg.getValue();
    }

    public void setBitOrdering(int bitOrdering) throws NativeIOException {
        IntReference arg = intRef.get();
        arg.setValue(bitOrdering);
        //        SPI_IOC_WR_LSB_FIRST
        console.ioctl(fd, console.getSpiIocWrLsbFirst(), arg);
    }

    private void initSPIHandler(int clockFrequency, SpiMode clockMode, int wordLength, int bitOrdering) throws NativeIOException {
        //IntReference variable = new IntReference(-1);

        log.log(Level.INFO, () -> "Read current clock value");
        SpiMode currentClockMode = getClockMode();
        log.log(Level.INFO, () -> "current clock is " + currentClockMode);

        if(!currentClockMode.equals(clockMode)) {
            log.log(Level.INFO, () -> "Setup clock variable");
            setClockMode(clockMode);
        } else {
            log.log(Level.INFO, () -> "Current clock is the same");
        }

        log.log(Level.INFO, () -> "Read current bits per word");
        int currentWordLength = getWordLength();

        log.log(Level.INFO, () -> "current bits per word is " + currentWordLength);

        if(currentWordLength != wordLength) {
            log.log(Level.INFO, () -> "Setup bits per word");
            setWordLength(wordLength);
        } else {
            log.log(Level.INFO, () -> "Current bits per word is the same");
        }

        log.log(Level.INFO, () -> "Read current max speed");
        int currentClockFrequency = getClockFrequency();
        //console.ioctl(fd, console.getSpiIocRdMaxSpeedHz(), variable);
        log.log(Level.INFO, () -> "current max speed is " + currentClockFrequency);

        if(clockFrequency != currentClockFrequency) {
            log.log(Level.INFO, () -> "Setup max speed");
            setClockFrequency(clockFrequency);
        } else {
            log.log(Level.INFO, () -> "Current max speed is the same");
        }

        log.log(Level.INFO, () -> "Read bit ordering");
        int currentBitOrdering = getBitOrdering();
        //console.ioctl(fd, console.getSpiIocRdMaxSpeedHz(), variable);
        log.log(Level.INFO, () -> "current bit ordering is " + currentBitOrdering);

        if(currentBitOrdering != bitOrdering) {
            log.log(Level.INFO, () -> "Setup bit ordering");
            setBitOrdering(bitOrdering);
        }


        log.log(Level.INFO, () -> String.format("SPI bus '%s' setup finished", bus));
    }

    /**
     * Read data from SPI bus based on requested parameters
     * @param inputParams request with input parameters. See {@link SpiInputParams}
     * @return buffer with response from SPI bus
     * @throws NativeIOException if SPI bus discard request
     */
    public int readByteData(SpiInputParams inputParams) throws IOException {
        log.log(Level.FINEST, () -> "read byte data");
        return readWriteData(inputParams.getFinal(), 1).get();
    }

    /**
     * Write data to SPI bus
     * @param inputParams request with input parameters. See {@link SpiInputParams}
     * @throws NativeIOException  if SPI bus discard request
     */
    public void writeByteData(SpiInputParams inputParams) throws NativeIOException {
        log.log(Level.FINEST, () -> "write byte data");
        readWriteData(inputParams.getFinal(), 0);
    }

    /**
     * Read array from SPI bus
     * @param inputParams request with input parameters. See {@link SpiInputParams}
     * @param outputSize expected size of response
     * @return buffer with response from SPI bus
     * @throws NativeIOException if SPI bus discard request
     */
    public ByteBuffer readArray(SpiInputParams inputParams, int outputSize) throws NativeIOException {
        log.log(Level.FINEST, () -> "read array");
        return readWriteData(
                inputParams.getFinal(),
                outputSize
        );
    }

    private ByteBuffer readWriteData(ByteBuffer input, int outputSize) throws NativeIOException {
        input.position(0);

        ByteBuffer output = ByteBuffer
                .allocateDirect(outputSize);

        log.log(Level.FINEST, () -> dump(input));

        SpiIocTransfer data = new SpiIocTransfer(
                input,
                output,
                input.capacity() + outputSize,
                getClockFrequency(),
                //clockFrequency,
                (short) 0,
                //(byte) wordLength
                (byte) getWordLength()
        );

        Ioctl.getInstance().ioctl(fd, data);

        log.log(Level.FINEST, () -> dump(output));

        return output.asReadOnlyBuffer();
    }
}
