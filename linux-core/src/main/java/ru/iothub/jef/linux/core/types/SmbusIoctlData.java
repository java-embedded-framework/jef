

package ru.iothub.jef.linux.core.types;

public class SmbusIoctlData {
    private byte readWrite;
    private long command;
    private int size;
    private SmbusData data;

    public SmbusIoctlData(byte readWrite, long command, int size, SmbusData data) {
        this.readWrite = readWrite;
        this.command = command;
        this.size = size;
        this.data = data;
    }

    public byte getReadWrite() {
        return readWrite;
    }

    public void setReadWrite(byte readWrite) {
        this.readWrite = readWrite;
    }

    public long getCommand() {
        return command;
    }

    public void setCommand(long command) {
        this.command = command;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SmbusData getData() {
        return data;
    }

    public void setData(SmbusData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SmbusIoctlData{" +
                "readWrite=" + readWrite +
                ", command=" + command +
                ", size=" + size +
                ", data=" + data +
                '}';
    }
}
