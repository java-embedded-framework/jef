 

package ru.iothub.jef.linux.core;

import java.io.IOException;

public class NativeIOException extends IOException {
    private final int code;

    public NativeIOException(String message) {
        super(message);
        code = -1;
    }

    public NativeIOException(int code) {
        this.code = code;
    }

    public NativeIOException(String message, int code) {
        super(message);
        this.code = code;
    }

    public NativeIOException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public NativeIOException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
