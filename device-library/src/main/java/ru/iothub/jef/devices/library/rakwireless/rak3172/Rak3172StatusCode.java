 

package ru.iothub.jef.devices.library.rakwireless.rak3172;

import java.util.Objects;

public enum Rak3172StatusCode {
    OK(0, "OK", "Command executed correctly without error"),
    AT_ERROR(1, "AT_ERROR", "Generic error or input is not supported"),
    AT_PARAM_ERROR(2, "AT_PARAM_ERROR", "The input parameter of the command is wrong"),
    AT_BUSY_ERROR(3, "AT_BUSY_ERROR", "The network is busy so the command is not completed"),
    AT_TEST_PARAM_OVERFLOW(4, "AT_TEST_PARAM_OVERFLOW", "The parameter is too long"),
    AT_NO_NETWORK_JOINED(5, "AT_NO_NETWORK_JOINED", "Module is not yet joined to a network"),
    AT_RX_ERROR(6, "AT_RX_ERROR", "Error detected during the reception of the command"),
    AT_DUTYCYLE_RESTRICTED(7, "AT_DUTYCYLE_RESTRICTED", "	Duty cycle limited and cannot send data");

    int id;
    String code;
    String description;

    Rak3172StatusCode(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    public static Rak3172StatusCode fromString(String s) {
        Objects.requireNonNull(s);

        for(Rak3172StatusCode obj : Rak3172StatusCode.values()) {
            if(obj.code.equals(s)) {
                return obj;
            }
        }
        throw new IllegalArgumentException("Illegal value: " + s);
    }
}
