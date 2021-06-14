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
