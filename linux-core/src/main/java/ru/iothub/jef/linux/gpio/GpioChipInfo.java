

package ru.iothub.jef.linux.gpio;

public class GpioChipInfo {
    private String name;
    private String label;
    private int lines;

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public int getLines() {
        return lines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "GpioChipInfo{" +
                "name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", lines=" + lines +
                '}';
    }
}
