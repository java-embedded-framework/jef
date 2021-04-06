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

package ru.iothub.jef.linux.serial;

import ru.iothub.jef.linux.core.Fcntl;
import ru.iothub.jef.linux.core.Ioctl;
import ru.iothub.jef.linux.core.NativeIOException;
import ru.iothub.jef.linux.core.Termios;
import ru.iothub.jef.linux.core.io.FileHandle;
import ru.iothub.jef.linux.core.types.IntReference;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

import static ru.iothub.jef.linux.core.IOFlags.*;

@SuppressWarnings("unused")
public class SerialPort {
    private final Fcntl fcntl;
    private final Ioctl ioctl;
    private final Termios termios;
    private final FileHandle handle;
    private final SerialInputStream is;
    private final SerialOutputStream os;

    public SerialPort(String path, SerialBaudRate rate) throws NativeIOException {
        fcntl = Fcntl.getInstance();
        ioctl = Ioctl.getInstance();
        termios = Termios.getInstance();
        handle = fcntl.open(path, EnumSet.of(O_RDWR, O_NOCTTY, O_NONBLOCK));

        TermiosStructure options = termios.tcgetattr(handle);

        termios.cfmakeraw(options);
        int speed = rate.value;

        termios.cfsetispeed(options, speed);
        termios.cfsetospeed(options, speed);

        options.c_cflag |= (Termios.CLOCAL | Termios.CREAD);
        options.c_cflag &= ~Termios.PARENB;
        options.c_cflag &= ~Termios.CSTOPB;
        options.c_cflag &= ~Termios.CSIZE;
        options.c_cflag |= Termios.CS8;
        options.c_lflag &= ~(Termios.ICANON | Termios.ECHO | Termios.ECHOE | Termios.ISIG);
        options.c_oflag &= ~Termios.OPOST;

        options.c_cc[Termios.VMIN] = 0;
        options.c_cc[Termios.VTIME] = 100;    // Ten seconds (100 deciseconds)

        termios.tcsetattr(handle, Termios.TCSANOW, options);

        IntReference statusRef = new IntReference();

        ioctl.ioctl(handle, Ioctl.TIOCMGET, statusRef);

        int status = statusRef.getValue();

        status |= Termios.TIOCM_DTR;
        status |= Termios.TIOCM_RTS;

        statusRef.setValue(status);

        ioctl.ioctl(handle, Ioctl.TIOCMSET, statusRef);

        is = new SerialInputStream();
        os = new SerialOutputStream();
    }

    public void flush() {
        termios.tcflush(handle, Termios.TCIOFLUSH);
    }

    public void close() {
        handle.close();
    }

    private void put(int i) throws IOException {
        fcntl.write(handle, new byte[]{(byte) i}, 1);
    }

    private int available() throws NativeIOException {
        IntReference ref = new IntReference();
        if (ioctl.ioctl(handle, Ioctl.FIONREAD, ref) == -1) {
            return -1;
        }
        return ref.getValue();
    }

    private int read() throws NativeIOException {
        byte[] b = new byte[1];
        if (fcntl.read(handle, b, 1) != 1) {
            return -1;
        }
        return b[0] & 0xFF;
    }

    public InputStream getInputStream() {
        return is;
    }

    public OutputStream getOutputStream() {
        return os;
    }

    private final class SerialOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            SerialPort.this.put(b);
        }

        @Override
        public void flush() {
            SerialPort.this.flush();
        }

        @Override
        public void close() throws IOException {
            SerialPort.this.close();
        }
    }

    private final class SerialInputStream extends InputStream {
        @Override
        public int read() throws IOException {
            return SerialPort.this.read();
        }

        @Override
        public int available() throws IOException {
            return SerialPort.this.available();
        }

        @Override
        public void close() throws IOException {
            SerialPort.this.close();
        }
    }
}
