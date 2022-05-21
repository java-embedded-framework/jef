 

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import ru.iothub.jef.linux.core.Bluetooth;
import ru.iothub.jef.linux.core.Socket;

public class SocketJna extends Socket {
    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public int getsockopt(int sockfd, int level, int optname, Bluetooth.HciFilter optval) {
        BluetoothJna.hci_filter val = new BluetoothJna.hci_filter(optval);
        int result = Delegate.getsockopt(sockfd, level, optname, val, val.size());
        val.fill(optval);
        return result;
    }

    @Override
    public int setsockopt(int sockfd, int level, int optname, Bluetooth.HciFilter optval) {
        BluetoothJna.hci_filter val = new BluetoothJna.hci_filter(optval);
        int result = Delegate.setsockopt(sockfd, level, optname, val, val.size());
        val.fill(optval);
        return result;
    }

    static class Delegate {
        static {
            Native.register("c");
        }

        public static native int getsockopt(int sockfd, int level, int optname, Structure optval, int optlen);

        public static native int setsockopt(int sockfd, int level, int optname, Structure optval, int optlen);
    }
}
