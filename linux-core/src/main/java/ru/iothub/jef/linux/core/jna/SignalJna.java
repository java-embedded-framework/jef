

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import ru.iothub.jef.linux.core.Signal;

public class SignalJna extends Signal {
    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public int sigaction(int sigint, Sigaction sa, Sigaction old) {
        return Delegate.sigaction(
                sigint,
                new sigaction(sa),
                old==null?null:new sigaction(old)
        );
    }

    @Structure.FieldOrder({"sa_handler", "sa_flags"})
    public static class sigaction extends Structure {
        public __sighandler_t sa_handler;
        public NativeLong sa_flags;

        private transient Sigaction obj;

        public sigaction(Sigaction obj) {
            sa_handler = signal -> obj.getHandler().handle(signal);
            sa_flags = new NativeLong(obj.getSaFlags(), true);
        }
    }

    private interface __sighandler_t extends Callback {
        void invoke(int signal);
    }

    static class Delegate {
        static {
            Native.register("c");
        }

        public static native int sigaction(int signal, sigaction sa, sigaction old);
    }
}
