 

package ru.iothub.jef.linux.core.types;

public class IntReference extends Reference {
    private int value;

    public IntReference() {
    }

    public IntReference(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
