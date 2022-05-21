

package ru.iothub.jef.linux.gpio;

import java.util.EnumSet;

@SuppressWarnings("unused")
public class GpioLineInfo {
    private int offset;
    private int flags;
    private String name;
    private String consumer;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "GpioLineInfo{" +
                "offset=" + offset +
                ", flags=" + flags +
                ", name='" + name + '\'' +
                ", consumer='" + consumer + '\'' +
                '}';
    }

    public enum Flags {
        GPIOLINE_FLAG_KERNEL(1), // 1
        GPIOLINE_FLAG_IS_OUT(1 << 1),// 2
        GPIOLINE_FLAG_ACTIVE_LOW(1 << 2),// 4
        GPIOLINE_FLAG_OPEN_DRAIN(1 << 3),// 8
        GPIOLINE_FLAG_OPEN_SOURCE(1 << 4), // 16
        GPIOLINE_FLAG_BIAS_PULL_UP(1 << 5), // 32
        GPIOLINE_FLAG_BIAS_PULL_DOWN(1 << 6), // 64
        GPIOLINE_FLAG_BIAS_DISABLE(1 << 7) // 128
        ;
        int value;

        Flags(int value) {
            this.value = value;
        }

        public static EnumSet<Flags> valueOf(int mask) {
            EnumSet<Flags> flags = EnumSet.noneOf(Flags.class);

            for (Flags flag : flags) {
                if ((mask & flag.value) != 0) {
                    flags.add(flag);
                }
            }

            return flags;
        }

        public int getValue() {
            return value;
        }
    }
}
