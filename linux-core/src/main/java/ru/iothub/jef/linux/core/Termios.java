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

package ru.iothub.jef.linux.core;

import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.serial.TermiosStructure;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public abstract class Termios implements NativeSupport {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    public static final int TCIOFLUSH = 2;
    public static final int CSIZE = 48;
    public static final int CS5 = 0;
    public static final int CS6 = 16;
    public static final int CS7 = 32;
    public static final int CS8 = 48;
    public static final int CSTOPB = 64;
    public static final int CREAD = 128;
    public static final int PARENB = 256;
    public static final int PARODD = 512;
    public static final int HUPCL = 1024;
    public static final int CLOCAL = 2048;
    /* c_lflag bits */
    public static final int ISIG = 1;
    public static final int ICANON = 2;
    public static final int XCASE = 4;
    public static final int ECHO = 8;
    public static final int ECHOE = 16;
    public static final int ECHOK = 32;
    public static final int ECHONL = 64;
    public static final int NOFLSH = 128;
    public static final int TOSTOP = 256;
    public static final int ECHOPRT = 1024;
    public static final int ECHOKE = 2048;
    public static final int FLUSHO = 4096;
    public static final int PENDIN = 16384;
    public static final int OPOST = 1;
    /* c_cc characters */
    public static final int VINTR = 0;
    public static final int VERASE = 2;
    public static final int VQUIT = 1;
    public static final int VKILL = 3;
    public static final int VEOF = 4;
    public static final int VTIME = 5;
    public static final int VMIN = 6;
    public static final int VSWTC = 7;
    public static final int VSTART = 8;
    public static final int VSTOP = 9;
    public static final int VSUSP = 10;
    public static final int VEOL = 11;
    public static final int VREPRINT = 12;
    public static final int VDISCARD = 13;
    public static final int VWERASE = 14;
    public static final int VLNEXT = 15;
    public static final int VEOL2 = 16;

    public static final int TCSANOW = 0;
    public static final int TCSADRAIN = 1;
    public static final int TCSAFLUSH = 2;

    public static final int TIOCM_LE    =    0x001;
    public static final int TIOCM_DTR    =   0x002;
    public static final int TIOCM_RTS    =   0x004;
    public static final int TIOCM_ST    =    0x008;
    public static final int TIOCM_SR    =    0x010;
    public static final int TIOCM_CTS   =    0x020;
    public static final int TIOCM_CAR  =     0x040;
    public static final int TIOCM_RNG  =     0x080;
    public static final int TIOCM_DSR  =     0x100;
    public static final int TIOCM_CD   =     TIOCM_CAR;
    public static final int TIOCM_RI   =     TIOCM_RNG;
    public static final int TIOCM_OUT1 =     0x2000;
    public static final int TIOCM_OUT2   =   0x4000;
    public static final int TIOCM_LOOP   =   0x8000;

    public static final int IBAUD0   =     020000000000;
    public static final int CBAUD   =      000000010017; /* Baud speed mask (not in POSIX).  */
    public static final int CBAUDEX = 000000010000; /* Extra baud speed mask, included in CBAUD.
                                 (not in POSIX).  */

    private static Termios instance = null;

    public static Termios getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Termios.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Termios.class);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }

    public abstract TermiosStructure tcgetattr(FileHandle handle);

    public abstract void cfmakeraw(TermiosStructure options);

    public abstract int cfsetispeed(TermiosStructure options, int value) throws NativeIOException;

    public abstract int cfsetospeed(TermiosStructure options, int value) throws NativeIOException;

    public abstract void tcsetattr(FileHandle handle, int tcsanow, TermiosStructure options) throws NativeIOException;

    public abstract int cfgetispeed(TermiosStructure options);

    public abstract int cfgetospeed(TermiosStructure options);

    public abstract int tcflush(FileHandle handle, int queue_selector);
}
