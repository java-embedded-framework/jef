

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import ru.iothub.jef.linux.core.Errno;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.Mmap;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;

public class MmapJna extends Mmap {
    public MmapJna() {
    }

    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public ByteBuffer mmap(FileHandle handle, MemoryProtection protection, MemoryFlag flags, long offset, int size) throws IOException {
        return mmap(handle, EnumSet.of(protection), flags, offset, size);
    }

    @Override
    public ByteBuffer mmap(FileHandle handle, EnumSet<MemoryProtection> protection, MemoryFlag flags, long offset, int size) throws IOException {
        Pointer address = new Pointer(0);

        Pointer result = Delegate.mmap(
                address,
                new NativeLong(size),
                memoryProtectionFlag(protection),
                flags.getValue(),
                handle.getHandle(),
                new NativeLong(offset)
        );

        if (Pointer.nativeValue(result) == -1) {
            Errno err = Errno.getInstance();
            System.out.println(err.strerror());
            throw new IOException("mmap failed: " + err.strerror());
        }

        return result.getByteBuffer(0, size);
    }

    static class Delegate {

        public static native Pointer mmap(Pointer addr,
                                          NativeLong len,
                                          int prot,
                                          int flags,
                                          int fd,
                                          NativeLong off);
        static {
            Native.register("c");
        }
    }


}
