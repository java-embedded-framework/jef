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
    /* tcflush() and TCFLSH use these */
    public static final int TCIFLUSH = 0;
    public static final int TCOFLUSH = 1;
    public static final int TCIOFLUSH = 2;

    public static final int CIBAUD = 0x100f0000;/* input baud rate (not used) */
    public static final int CMSPAR = 0x40000000;/* mark or space (stick) parity */
    public static final int CRTSCTS = 0x80000000;/* flow control */

    /* OK */
    public static final int CSIZE = 0x000030;
    public static final int CS5 = 0x0;
    public static final int CS6 = 0x000010;
    public static final int CS7 = 0x000020;
    public static final int CS8 = 0x000030;
    public static final int CSTOPB = 0x000040;
    public static final int CREAD = 0x000080;
    public static final int PARENB = 0x000100;
    public static final int PARODD = 0x000200;
    public static final int HUPCL = 0x000400;
    public static final int CLOCAL = 0x000800;
    public static final int CBAUDEX = 0x001000;


    /* c_lflag bits */
    public static final int ISIG = 0x0000001;
    public static final int ICANON = 0x0000002;
    public static final int XCASE = 0x0000004;
    public static final int ECHO = 0x0000010;
    public static final int ECHOE = 0x0000020;
    public static final int ECHOK = 0x0000040;
    public static final int ECHONL = 0x0000100;
    public static final int NOFLSH = 0x0000200;
    public static final int TOSTOP = 0x0000400;
    public static final int ECHOCTL = 0x0001000;
    public static final int ECHOPRT = 0x0002000;
    public static final int ECHOKE = 0x0004000;
    public static final int FLUSHO = 0x0010000;
    public static final int PENDIN = 0x0040000;
    public static final int IEXTEN = 0x0100000;
    public static final int EXTPROC = 0x0200000;


    /* c_iflag bits - OK */
    public static final int IGNBRK = 0x000001;
    public static final int BRKINT = 0x000002;
    public static final int IGNPAR = 0x000004;
    public static final int PARMRK = 0x000008;
    public static final int INPCK = 0x000010;
    public static final int ISTRIP = 0x000020;
    public static final int INLCR = 0x000040;
    public static final int IGNCR = 0x000080;
    public static final int ICRNL = 0x000100;
    public static final int IUCLC = 0x000200;
    public static final int IXON = 0x0002000;
    public static final int IXANY = 0x000800;
    public static final int IXOFF = 0x001000;
    public static final int IMAXBEL = 0x002000;
    public static final int IUTF8 = 0x004000;

    /* c_oflag bits */
    public static final int OPOST = 0x0000001;
    public static final int OLCUC = 0x0000002;
    public static final int ONLCR = 0x0000004;
    public static final int OCRNL = 0x0000010;
    public static final int ONOCR = 0x0000020;
    public static final int ONLRET = 0x0000040;
    public static final int OFILL = 0x0000100;
    public static final int OFDEL = 0x0000200;
    public static final int NLDLY = 0x0000400;
    public static final int NL0 = 0x0000000;
    public static final int NL1 = 0x0000400;
    public static final int CRDLY = 0x0003000;
    public static final int CR0 = 0x0000000;
    public static final int CR1 = 0x0001000;
    public static final int CR2 = 0x0002000;
    public static final int CR3 = 0x0003000;
    public static final int TABDLY = 0x0014000;
    public static final int TAB0 = 0x0000000;
    public static final int TAB1 = 0x0004000;
    public static final int TAB2 = 0x0010000;
    public static final int TAB3 = 0x0014000;
    public static final int BSDLY = 0x0020000;
    public static final int BS0 = 0x0000000;
    public static final int BS1 = 0x0020000;
    public static final int FFDLY = 0x0100000;
    public static final int FF0 = 0x0000000;
    public static final int FF1 = 0x0100000;


    /* c_cc characters OK */
    public static final int VINTR = 0;
    public static final int VQUIT = 1;
    public static final int VERASE = 2;
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


    /* modem lines */
    public static final int TIOCM_LE = 0x001;
    public static final int TIOCM_DTR = 0x002;
    public static final int TIOCM_RTS = 0x004;
    public static final int TIOCM_ST = 0x008;
    public static final int TIOCM_SR = 0x010;
    public static final int TIOCM_CTS = 0x020;
    public static final int TIOCM_CAR = 0x040;
    public static final int TIOCM_RNG = 0x080;
    public static final int TIOCM_DSR = 0x100;
    public static final int TIOCM_CD = TIOCM_CAR;
    public static final int TIOCM_RI = TIOCM_RNG;
    public static final int TIOCM_OUT1 = 0x2000;
    public static final int TIOCM_OUT2 = 0x4000;
    public static final int TIOCM_LOOP = 0x8000;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

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

    public abstract TermiosStructure tcgetattr(FileHandle handle) throws NativeIOException;

    public abstract void cfmakeraw(TermiosStructure options);

    public abstract int cfsetispeed(TermiosStructure options, int value) throws NativeIOException;

    public abstract int cfsetospeed(TermiosStructure options, int value) throws NativeIOException;

    public abstract void tcsetattr(FileHandle handle, int tcsanow, TermiosStructure options) throws NativeIOException;

    public abstract int cfgetispeed(TermiosStructure options);

    public abstract int cfgetospeed(TermiosStructure options);

    public abstract int tcflush(FileHandle handle, int queue_selector);
}
