

package ru.iothub.jef.linux.gpio;

public class GpioHandleRequest {
    public enum Flags {
        GPIOHANDLE_REQUEST_INPUT(1),
        GPIOHANDLE_REQUEST_OUTPUT(1 << 1),
        GPIOHANDLE_REQUEST_ACTIVE_LOW(1 << 2),
        GPIOHANDLE_REQUEST_OPEN_DRAIN(1 << 3),
        GPIOHANDLE_REQUEST_OPEN_SOURCE(1 << 4)
        ;
        int value;

        Flags(int value) {
            this.value = value;
        }
    }

    private int[] lineoffsets;
    private int flags;
    private byte[] default_values;
    private byte[] consumer_label;
    private int lines;
    private int fd;

    public int getLines() {
        return lines;
    }

    public int getFd() {
        return fd;
    }

    public int getFlags() {
        return flags;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setLinesOffset(int[] offset) {
        this.lineoffsets = offset;
    }

    public int[] getLineOffsets() {
        return lineoffsets;
    }

    public void setFd(int fd) {
        this.fd = fd;
    }

    public void setConsumerLabel(String jef) {
        consumer_label = jef.getBytes();
    }

    public byte[] getConsumerLabel() {
        return consumer_label;
    }

    public byte[] getDefaultValues() {
        return default_values;
    }

    public void setDefaultValues(byte[] default_values) {
        this.default_values = default_values;
    }
}
