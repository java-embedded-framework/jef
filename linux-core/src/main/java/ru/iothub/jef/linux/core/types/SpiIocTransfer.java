 

package ru.iothub.jef.linux.core.types;

import java.nio.ByteBuffer;

public class SpiIocTransfer {
    private final ByteBuffer txBuffer;
    private final ByteBuffer rxBuffer;

    private final int length;
    private final int speed;
    private final short delay;
    private final byte bitsPerWord;
    private final byte csChange;
    private final byte pad;

    public SpiIocTransfer(ByteBuffer txBuffer, ByteBuffer rxBuffer, int length, int speed, short delay, byte bitsPerWord) {
        this(txBuffer, rxBuffer, length, speed, delay, bitsPerWord, (byte) -1, (byte) -1);
    }

    public SpiIocTransfer(ByteBuffer txBuffer, ByteBuffer rxBuffer, int length, int speed, short delay, byte bitsPerWord, byte csChange, byte pad) {
        this.txBuffer = txBuffer;
        this.rxBuffer = rxBuffer;
        this.length = length;
        this.speed = speed;
        this.delay = delay;
        this.bitsPerWord = bitsPerWord;
        this.csChange = csChange;
        this.pad = pad;
    }

    public ByteBuffer getTxBuffer() {
        return txBuffer;
    }

    public ByteBuffer getRxBuffer() {
        return rxBuffer;
    }

    public int getLength() {
        return length;
    }

    public int getSpeed() {
        return speed;
    }

    public short getDelay() {
        return delay;
    }

    public byte getBitsPerWord() {
        return bitsPerWord;
    }

    public byte getCsChange() {
        return csChange;
    }

    public byte getPad() {
        return pad;
    }
}
