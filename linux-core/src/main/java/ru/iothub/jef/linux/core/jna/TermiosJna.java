/*
 * Copyright (c) 2021, IOT-Hub.RU and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is dual-licensed: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License Version 3 as
 * published by the Free Software Foundation. For the terms of this
 * license, see <http://www.gnu.org/licenses/>.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License Version 3 for more details (a copy is included in the LICENSE
 * file that accompanied this code).
 *
 * You should have received a copy of the GNU Affero General Public License
 * version 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact support@iot-hub.ru or visit www.iot-hub.ru if you need
 * additional information or have any questions.
 *
 * You can be released from the requirements of the license by purchasing
 * a Java Embedded Framework Commercial License. Buying such a license is
 * mandatory as soon as you develop commercial activities involving the
 * Java Embedded Framework software without disclosing the source code of
 * your own applications.
 *
 * Please contact sales@iot-hub.ru if you have any question.
 */

package ru.iothub.jef.linux.core.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Termios;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.serial.TermiosStructure;

import static ru.iothub.jef.linux.core.LinuxUtils.checkIOResult;

public class TermiosJna extends Termios {
    private final static int NCCS = 32;

    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public TermiosStructure tcgetattr(FileHandle handle) throws NativeIOException {
        termios t = new termios();
        //termios.TermiosByReference ref = new termios.TermiosByReference(t.getPointer());
        int result = Delegate.tcgetattr(handle.getHandle(), t);
        if (result < 0) {
            checkIOResult("tcgetattr", result);
            System.out.println("SHEEEEEEEET");
        }
        return t.generalize();
    }

    @Override
    public void cfmakeraw(TermiosStructure options) {
        termios t = new termios(options);
        Delegate.cfmakeraw(t);
        t.generalize(options);
    }

    @Override
    public int cfsetispeed(TermiosStructure options, int value) throws NativeIOException {
        termios t = new termios(options);
        int result = Delegate.cfsetispeed(t, value);
        checkIOResult("cfsetispeed", result);
        t.generalize(options);
        return result;
    }

    @Override
    public int cfsetospeed(TermiosStructure options, int value) throws NativeIOException {
        termios t = new termios(options);
        int result = Delegate.cfsetospeed(t, value);
        checkIOResult("cfsetospeed", result);
        t.generalize(options);
        return result;
    }

    @Override
    public void tcsetattr(FileHandle handle, int tcsanow, TermiosStructure options) throws NativeIOException {
        termios t = new termios(options);
        int result = Delegate.tcsetattr(handle.getHandle(), tcsanow, t);
        checkIOResult("tcsetattr", result);
        t.generalize(options);
    }

    @Override
    public int cfgetispeed(TermiosStructure options) {
        termios t = new termios(options);
        int result = Delegate.cfgetispeed(t);
        t.generalize(options);
        return result;
    }

    @Override
    public int cfgetospeed(TermiosStructure options) {
        termios t = new termios(options);
        int result = Delegate.cfgetospeed(t);
        t.generalize(options);
        return result;
    }

    @Override
    public int tcflush(FileHandle handle, int queue_selector) {
        return Delegate.tcflush(handle.getHandle(), queue_selector);
    }

    @Structure.FieldOrder({"c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_line", "c_cc", "c_ispeed", "c_ospeed"})
    public static class termios extends Structure {
        /*public static class TermiosByReference extends termios implements Structure.ByReference{
            public TermiosByReference() {
            }

            public TermiosByReference(Pointer p) {
                super(p);
                read();
            }
        }*/

        public int c_iflag;
        public int c_oflag;
        public int c_cflag;
        public int c_lflag;
        public byte c_line;
        public byte[] c_cc = new byte[NCCS];
        public int c_ispeed;
        public int c_ospeed;

        public termios() {
        }

        public termios(Pointer p) {
            super(p);
            read();
        }

        public termios(TermiosStructure options) {
            c_iflag = options.getC_iflag();
            c_oflag = options.getC_oflag();
            c_cflag = options.getC_cflag();
            c_lflag = options.getC_lflag();
            c_line = options.getC_line();
            c_cc = options.getC_cc();
            c_ispeed = options.getC_ispeed();
            c_ospeed = options.getC_ospeed();
        }

        public TermiosStructure generalize(TermiosStructure ts) {
            ts.setC_iflag(c_iflag);
            ts.setC_oflag(c_oflag);
            ts.setC_cflag(c_cflag);
            ts.setC_lflag(c_lflag);
            ts.setC_line(c_line);
            ts.setC_cc(c_cc);
            ts.setC_ispeed(c_ispeed);
            ts.setC_ospeed(c_ospeed);
            return ts;
        }

        public TermiosStructure generalize() {
            return generalize(new TermiosStructure());
        }
    }

    static class Delegate {
        static {
            Native.register("c");
        }

        public static native int tcgetattr(int fd, termios t);

        public static native void cfmakeraw(termios termios);

        public static native int cfsetispeed(termios t, int value);

        public static native int cfsetospeed(termios t, int value);

        public static native int tcsetattr(int handle, int tcsanow, termios t);

        public static native int cfgetispeed(termios t);

        public static native int cfgetospeed(termios t);

        public static native int tcflush(int handle, int queue_selector);
    }

}
