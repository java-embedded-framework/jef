 

package ru.iothub.jef.linux.gpio;

import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.io.FileHandle;

import java.io.IOException;
import java.util.EnumSet;

import static ru.iothub.jef.linux.core.IOFlags.O_CLOEXEC;
import static ru.iothub.jef.linux.core.IOFlags.O_RDONLY;

@SuppressWarnings("unused")
public class GpioPin implements AutoCloseable {
    private final int flags;
    private final String key;
    private final String path;
    private final int number;
    private final String name;
    private final String consumer;
    private final boolean locked;

    private Direction direction;
    private int handle;
    private boolean closed;

    GpioPin(String key, String path, int number) throws NativeIOException {
        this.path = path;
        this.number = number;
        this.key = key;
        this.closed = false;

        try (FileHandle handle = openHandle(path)) {
            GpioLineInfo line = new GpioLineInfo();
            line.setOffset(number);
            Ioctl ioctl = Ioctl.getInstance();
            ioctl.ioctl(handle, Ioctl.getGpioGetLineInfoIoctl(), line);

            this.flags = line.getFlags();
            this.name = line.getName();
            this.consumer = line.getConsumer();

            this.locked = (flags & GpioLineInfo.Flags.GPIOLINE_FLAG_KERNEL.value) > 0;
        }


    }

    public int getPinNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getConsumer() {
        return consumer;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFlags() {
        return flags;
    }

    public State read() throws IOException {
        checkClosed();
        checkLocked();
        checkDirection();

        /*if(this.handle < 0) {
            return (flags & GpioLineInfo.Flags.GPIOLINE_FLAG_ACTIVE_LOW.value) != 0
                    ? State.LOW : State.HIGH;
        }*/

        if (Direction.OUTPUT.equals(this.direction)) {
            throw new IOException("Can't read from output pin " + path + "-" + number);
        }

        GpioHandleData data = new GpioHandleData(new byte[1]);
        Ioctl ioctl = Ioctl.getInstance();
        ioctl.ioctl(handle, ioctl.getGpioHandleGetLineValuesIoctl(), data);

        byte value = data.getValues()[0];
        return value == 0 ? State.LOW : State.HIGH;
    }

    public void write(State state) throws IOException {
        checkClosed();
        checkLocked();
        checkDirection();
        if (Direction.INPUT.equals(this.direction)) {
            throw new IOException("Can't write to input pin " + path + "-" + number);
        }

        byte value = (byte) (state == State.HIGH ? 1 : 0);
        GpioHandleData data = new GpioHandleData(new byte[]{value});

        Ioctl ioctl = Ioctl.getInstance();
        ioctl.ioctl(handle, ioctl.getGpioHandleSetLineValuesIoctl(), data);
    }

    public void setDirection(Direction direction) throws IOException {
        checkClosed();
        checkLocked();
        if (this.handle > 0 && this.direction.equals(direction)) {
            return;
        }
        try (FileHandle fd = openHandle(this.path)) {
            freeHandle();

            int mode = Direction.INPUT.equals(direction) ?
                    GpioHandleRequest.Flags.GPIOHANDLE_REQUEST_INPUT.value :
                    GpioHandleRequest.Flags.GPIOHANDLE_REQUEST_OUTPUT.value;

            GpioHandleRequest request = new GpioHandleRequest();
            request.setLinesOffset(new int[]{number});
            request.setFlags(mode);
            request.setConsumerLabel("jef-gpio-manager");
            request.setLines(1);
            Ioctl ioctl = Ioctl.getInstance();

            ioctl.ioctl(fd, ioctl.getGpioGetLineHandleIoctl(), request);
            this.handle = request.getFd();
            this.direction = direction;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void close() throws Exception {
        freeHandle();
        GpioManager.closePin(key);
        closed = true;
    }

    private void checkDirection() throws IOException {
        if (direction == null) {
            throw new IOException("Gpio Pin direction not established");
        }
    }

    private void checkClosed() throws IOException {
        if (closed) {
            throw new IOException("Gpio Pin '" + key + "' is closed");
        }
    }

    private void checkLocked() throws IOException {
        if(locked) {
            throw new IOException("Gpio Pin '" + key + "' locked by kernel");
        }
    }

    private FileHandle openHandle(String path) throws NativeIOException {
        return Fcntl.getInstance().open(path, EnumSet.of(O_RDONLY, O_CLOEXEC));
    }

    private void freeHandle() throws NativeIOException {
        if (handle > 0) {
            Fcntl.getInstance().close(handle);
        }
    }

    public enum State {
        LOW(0),
        HIGH(1);

        private final int value;

        State(int value) {
            this.value = value;
        }

        public static State valueOf(int value) {
            for(State st : State.values()) {
                if(st.value == value) {
                    return st;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Direction {
        INPUT,
        OUTPUT
    }
}
