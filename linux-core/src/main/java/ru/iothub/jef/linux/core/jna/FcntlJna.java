 

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.Native;
import ru.iothub.jef.linux.core.IOFlags;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.io.FileHandlerCleaner;

import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.linux.core.LinuxUtils.checkIOResult;
import static ru.iothub.jef.linux.core.util.StringUtils.dump;

public class FcntlJna extends Fcntl {
    private static final Logger log = Logger.getLogger(FcntlJna.class.getName());

    @Override
    public FileHandle open(String pathname, EnumSet<IOFlags> flags) throws NativeIOException {
        int mask = IOFlagsMask(flags);

        log.log(Level.FINEST, () -> String.format("fctl.jna open %s with flags %d", pathname, mask));

        int result = Delegate.open(pathname, mask);
        checkIOResult("open", result);

        FileHandle handle = new FileHandle(result);
        FileHandlerCleaner.register(handle);

        return handle;
    }

    @Override
    public FileHandle open64(String pathname, EnumSet<IOFlags> flags) throws NativeIOException {
        int mask = IOFlagsMask(flags);

        log.log(Level.FINEST, () -> String.format("fctl.jna open64 %s with flags %d", pathname, mask));

        int result = Delegate.open64(pathname, mask);
        checkIOResult("open64", result);

        FileHandle handle = new FileHandle(result);
        FileHandlerCleaner.register(handle);

        return handle;
    }

    @Override
    public void close(FileHandle fd) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("fctl.jna close descriptor '%d'", fd.getHandle()));
        int result = Delegate.close(fd.getHandle());
        checkIOResult("close", result);
    }

    @Override
    public void close(int fd) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("fctl.jna close descriptor '%d'", fd));
        int result = Delegate.close(fd);
        checkIOResult("close", result);
    }

    @Override
    public int read(FileHandle fd, byte[] buffer, int size) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("fctl.jna read from '%d' length '%d'", fd.getHandle(), size));
        int result = Delegate.read(fd.getHandle(), buffer, size);
        log.log(Level.FINEST, () -> dump(buffer));
        checkIOResult("write", result);
        return result;
    }

    @Override
    public void write(FileHandle fd, byte[] buffer, int size) throws NativeIOException {
        log.log(Level.FINEST, () -> String.format("fctl.jna write to '%d' amount '%d'", fd.getHandle(), size));
        log.log(Level.FINEST, () -> dump(buffer));
        int result = Delegate.write(fd.getHandle(), buffer, size);
        checkIOResult("write", result);
    }

    @Override
    public long lseek(FileHandle fd, long offset, Whence whence) {
        return Delegate.lseek(fd.getHandle(), offset, whence.getValue());
    }

    @Override
    public int fcntl(FileHandle fd, int cmd, EnumSet<IOFlags> flags) throws NativeIOException {
        int mask = IOFlagsMask(flags);
        int result = Delegate.fcntl(fd.getHandle(), cmd, mask);
        checkIOResult("fcntl", result);
        return result;
    }

    @Override
    public boolean isNativeSupported() {
        return false;
    }

    static class Delegate {
        static {
            Native.register("c");
        }

        // IO
        public static native int open(String pathname, int flags);

        public static native int open64(String path, int flags);

        public static native int close(int fd);

        public static native int read(int fd, byte[] buffer, int size);

        public static native int write(int fd, byte[] buffer, int size);

        public static native long lseek(int fd, long offset, int whence);

        public static native int fcntl(int fd, int cmd, int mask);
    }
}
