 

package ru.iothub.jef.linux.core;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Errno implements NativeSupport {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Errno instance = null;

    public abstract int ierrno();

    public abstract String strerror(int errnum);

    public abstract int perror(String err);

    public abstract String strerror();

    public static Errno getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Errno.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Errno.class);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }
}
