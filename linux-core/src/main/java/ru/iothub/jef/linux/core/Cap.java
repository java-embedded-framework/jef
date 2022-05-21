

package ru.iothub.jef.linux.core;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Wrapper to some functions from libcap.so
 */
public abstract class Cap implements NativeSupport {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Cap instance = null;

    public enum CapabilityFlag {
        CAP_EFFECTIVE(0),                        /* Specifies the effective flag */
        CAP_PERMITTED(1),                        /* Specifies the permitted flag */
        CAP_INHERITABLE(2);

        int value;

        CapabilityFlag(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static class CapHeaderStruct {
        private final int version;
        private final int pid;

        public CapHeaderStruct(int version, int pid) {
            this.version = version;
            this.pid = pid;
        }

        public int getVersion() {
            return version;
        }

        public int getPid() {
            return pid;
        }
    }

    public static class CapDataStruct {
        private final int effective;
        private final int permitted;
        private final int inheritable;

        public CapDataStruct(int effective, int permitted, int inheritable) {
            this.effective = effective;
            this.permitted = permitted;
            this.inheritable = inheritable;
        }

        public int getEffective() {
            return effective;
        }

        public int getPermitted() {
            return permitted;
        }

        public int getInheritable() {
            return inheritable;
        }
    }

    public static class CapStruct implements AutoCloseable {
        private final CapHeaderStruct head;
        private final CapDataStruct set;
        private final long pointer;

        public CapStruct(CapHeaderStruct head, CapDataStruct set, long pointer) {
            this.head = head;
            this.set = set;
            this.pointer = pointer;
        }

        public CapHeaderStruct getHead() {
            return head;
        }

        public CapDataStruct getSet() {
            return set;
        }

        public long getPointer() {
            return pointer;
        }

        @Override
        public void close() {
            Cap.getInstance().cap_free(this);
        }
    }

    public abstract CapStruct cap_get_pid(int pid);

    public abstract int cap_free(CapStruct cap_t);

    public abstract boolean cap_get_flag(CapStruct cap_t, Capability capability, CapabilityFlag flag);

    public static Cap getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Cap.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Cap.class, false);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }
}
