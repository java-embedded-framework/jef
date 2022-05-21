

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.io.IOException;

public class ATCommandException extends IOException {
    public ATCommandException() {
    }

    public ATCommandException(String message) {
        super(message);
    }

    public ATCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public ATCommandException(Throwable cause) {
        super(cause);
    }

    public ATCommandException(Rak3172StatusCode status) {
        this(status.description);
    }
}
