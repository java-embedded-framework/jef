

package ru.iothub.jef.linux.core.types;

@SuppressWarnings("unused")
public class ByteArrayReference extends Reference {
    private byte[] value;

    public ByteArrayReference() {
    }

    public ByteArrayReference(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
