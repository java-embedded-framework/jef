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

package ru.iothub.jef.devices.library.rakwireless.rak3172;


import ru.iothub.jef.linux.core.Fcntl;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

import static ru.iothub.jef.devices.library.rakwireless.rak3172.Rak3172StatusCode.OK;

public class ATCommand {
    private static final Logger log = Logger.getLogger(ATCommand.class.getName());
    private final static String TERMINATOR = "\r\n";

    private final RAK3172 rak;
    private final BufferedReader br;
    private final OutputStreamWriter bw;
    private final String command;

    ATCommand(RAK3172 rak, String command) {
        this.br = new BufferedReader(rak.getReader());
        this.rak = rak;
        this.bw = rak.getWriter();
        this.command = command;
    }

    private static void checkStatusCode(String value) throws ATCommandException {
        Rak3172StatusCode status = Rak3172StatusCode.fromString(value);
        if (status != OK) {
            throw new ATCommandException(status);
        }
    }

    private String commandName() {
        return command;
    }

    public void run() throws IOException {
        write(commandName());
        read();
        checkStatusCode(read());
    }

    public String query() throws IOException {
        write(commandName() + "?");
        String result = read();
        read();
        checkStatusCode(read());
        return result;
    }

    public void set(Object o) throws IOException {
        write(commandName() + "=" + o);
        read();
        checkStatusCode(read());
    }

    public String get() throws IOException {
        write(commandName() + "=?");
        String result = read();
        read();
        checkStatusCode(read());
        return result;
    }

    private void write(String command) throws IOException {
        log.info("Write: '" + command + "'");
        /*bw.write(command + "\r\n");
        bw.flush();*/
        byte[] bytes = command.getBytes();
        Fcntl.getInstance().write(rak.getHandle(), bytes, bytes.length);
        log.info("Write: '" + command + "' done");
    }

    private String read() throws IOException {
        log.info("Read from device");
        //String result = readBytes();//br.readLine();
        Scanner scanner = new Scanner(rak.getReader());
        String result = scanner.nextLine();
        //String result = br.readLine();
        log.info("Read: '" + result + "'");
        return result;
    }

    private String readBytes() throws IOException {
        boolean cont = true;
        String result = "";
        int i;
        System.out.println("Begin read");
        while (cont && (i = br.read()) != -1) {
            char ch = (char)i;
            System.out.println("Readed: " + ch);
            if(ch == '\n') {
                cont = false;
            }
            result += ch;
        }
        System.out.println("Read completed");
        return result;
    }

}
