

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
