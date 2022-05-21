 

package ru.iothub.jef.linux.core.io;

import ru.iothub.jef.linux.core.Fcntl;

import java.io.IOException;

public class FileHandle implements AutoCloseable {
    private final int handle;

    public FileHandle(int handle) {
        this.handle = handle;
    }

    public int getHandle() {
        return handle;
    }

    @Override
    public void close() {
        try {
            Fcntl.getInstance().close(handle);
        } catch (IOException ignored) {

        }
    }

    @Override
    public String toString() {
        return "FileHandle{" +
                "handle=" + handle +
                '}';
    }
}
