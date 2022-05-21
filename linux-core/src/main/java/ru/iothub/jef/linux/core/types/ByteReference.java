 

package ru.iothub.jef.linux.core.types;

public class ByteReference extends Reference {
    private byte value;

    public ByteReference() {
    }

    public ByteReference(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
