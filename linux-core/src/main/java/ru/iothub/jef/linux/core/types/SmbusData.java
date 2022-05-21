 

package ru.iothub.jef.linux.core.types;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class SmbusData {
    private byte[] buf;

    public SmbusData() {
        buf = new byte[34];
    }

    public byte getByte() {
        return buf[0];
    }

    public void setByte(byte b) {
        buf[0] = b;
    }

    public short getWord() {
        return ByteBuffer.wrap(buf).get(0);
    }

    public void setWord(short word) {
        ByteBuffer.wrap(buf).putShort(0, word);
    }

    public byte[] getBlock() {
        return buf;
    }

    public void setBlock(byte[] buf) {
        this.buf = buf;
    }

    @Override
    public String toString() {
        return "SmbusData{" +
                "buf=" + Arrays.toString(buf) +
                '}';
    }
}
