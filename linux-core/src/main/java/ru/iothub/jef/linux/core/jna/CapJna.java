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
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import ru.iothub.jef.linux.core.Capability;
import ru.iothub.jef.linux.core.Cap;

public class CapJna extends Cap {
    @Override
    public boolean isNativeSupported() {
        return false;
    }

    @Override
    public CapStruct cap_get_pid(int pid) {
        _cap_struct cap_struct = Delegate.cap_get_pid(pid);

        return new CapStruct(
                new CapHeaderStruct(cap_struct.head.version, cap_struct.head.pid),
                new CapDataStruct(cap_struct.set.effective, cap_struct.set.permitted, cap_struct.set.inheritable),
                Pointer.nativeValue(cap_struct.getPointer())
        );
    }

    @Override
    public int cap_free(CapStruct cap_t) {
        return Delegate.cap_free(new NativeLong(cap_t.getPointer(), true));
    }

    @Override
    public boolean cap_get_flag(CapStruct cap_t, Capability capability, CapabilityFlag flag) {
        IntByReference ref = new IntByReference();
        Pointer pointer = new Pointer(cap_t.getPointer());
        Delegate.cap_get_flag(pointer, capability.getValue(), flag.getValue(), ref.getPointer());
        return ref.getValue() == 1;
    }

    // // https://man7.org/linux/man-pages/man2/capget.2.html
    @Structure.FieldOrder({"version", "pid"})
    public static class __user_cap_header_struct extends Structure {
        public int version;
        public int pid;

        public __user_cap_header_struct() {
        }
    }

    @Structure.FieldOrder({"effective", "permitted", "inheritable"})
    public static class __user_cap_data_struct extends Structure {
        public int effective;
        public int permitted;
        public int inheritable;

        public __user_cap_data_struct() {
        }
    }


    // http://www.castaglia.org/proftpd/doc/devel-guide/src/lib/libcap/libcap.h.html
    @Structure.FieldOrder({"head", "set"})
    public static class _cap_struct extends Structure {
        public __user_cap_header_struct head;
        public __user_cap_data_struct set;

        public _cap_struct() {
            head = new __user_cap_header_struct();
            set = new __user_cap_data_struct();
        }
    }

    static class Delegate {
        static {
            Native.register("cap");
        }

        public static native _cap_struct cap_get_pid(int pid);

        public static native int cap_free(NativeLong cap_t);

        public static native int cap_get_flag(Pointer cap_t, int cap_value_t, int cap_flag_t, Pointer result);
    }
}
