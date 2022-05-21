 

package ru.iothub.jef.linux.core;

import ru.iothub.jef.linux.core.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.iothub.jef.linux.core.Sys.AccessFlag.R_OK;
import static ru.iothub.jef.linux.core.Sys.AccessFlag.W_OK;

public class RuntimePermissionsChecker {
    private static final Logger log = Logger.getLogger(RuntimePermissionsChecker.class.getName());

    private final static AtomicBoolean validated = new AtomicBoolean();
    private final static long ROOT_USER_ID = 0;
    private final static List<String> lines = new ArrayList<>();

    private static int maxStringLength = 0;

    public static void check() {
        if (!validated.get()) {
            synchronized (RuntimePermissionsChecker.class) {
                if (!validated.get()) {
                    validate();
                    validated.set(true);
                }
            }
        }
    }

    private static void validate() {
        Sys ss = Sys.getInstance();

        log.log(Level.SEVERE, write("Checking OS permissions to execute this application"));
        write("");

        boolean error = false;

        if (ss.getuid() == ROOT_USER_ID && ss.geteuid() == ROOT_USER_ID) {
            log.log(Level.SEVERE, write("PASSED: Executed with ROOT permissions. Should be ok."));
            write("");
        } else {
            boolean ok = false;
            boolean groupok = true;
            List<String> groups = new ArrayList<>();

            List<String> userGroups = ss.getUserGroups();
            /*if (!userGroups.contains("kmem")) {
                log.log(Level.SEVERE, write("User not contains 'kmem' group."));
                groupok = false;
                groups.add("kmem");
            }*/

            if (!userGroups.contains("i2c")) {
                log.log(Level.SEVERE, write("User not contains 'i2c' group."));
                groupok = false;
                groups.add("i2c");
            }

            if (!userGroups.contains("spi")) {
                log.log(Level.SEVERE, write("User not contains 'spi' group."));
                groupok = false;
                groups.add("spi");
            }

            if (!userGroups.contains("gpio")) {
                log.log(Level.SEVERE, write("User not contains 'spi' group."));
                groupok = false;
                groups.add("spi");
            }

            if (!groupok) {
                String str = String.join(",", groups);
                log.log(Level.SEVERE, write("ERROR: Current user don't required groups. Please run command 'sudo usermod -a -G " + str + " " + ss.getLogin() + "'"));
                write("");
            } else {
                log.log(Level.SEVERE, write("PASSED: User in all necessary groups."));
                write("");
            }

            /*try {
                ok = ss.access("/dev/mem", EnumSet.of(R_OK, W_OK));
            } catch (NativeIOException ignored) {
            }

            if (!ok) {
                log.log(Level.SEVERE, write("ERROR: Current user don't have access to '/dev/mem'. Please run command 'sudo chmod g+rw /dev/mem'"));
                write("");
                error = true;
            }*/

            /*int pid = ss.getpid();
            Cap cp = Cap.getInstance();
            try (Cap.CapStruct cap_t = cp.cap_get_pid(pid)) {
                boolean flag = cp.cap_get_flag(cap_t, Capability.CAP_SYS_RAWIO, Cap.CapabilityFlag.CAP_EFFECTIVE);
                if (flag) {
                    log.log(Level.SEVERE, write("PASSED: cap_sys_rawio assigned to application. Should be ok"));
                    write("");
                } else {
                    String path = ss.getcwd();
                    log.log(Level.SEVERE, write("ERROR: cap_sys_rawio NOT assigned to application."));
                    log.log(Level.SEVERE, write("ERROR: Please run command 'sudo setcap cap_sys_rawio=ep " + path + "' before execution."));
                    write("");
                    error = true;
                }
            } catch (Exception ignored) {
            }*/
        }

        dump();
        /*if (error) {
            System.exit(-2);
        }*/
    }

    private static void dump() {
        StringBuilder builder = new StringBuilder();
        String header = StringUtils.repeat("*", maxStringLength + 4);

        builder.append(header).append("\n");
        for (String s : lines) {
            builder
                    .append("* ")
                    .append(s)
                    .append(spaces(s.length()))
                    .append(" *\n")
            ;
        }
        builder.append(header).append("\n");
        System.out.println(builder);
    }

    private static String spaces(int length) {
        int max = maxStringLength - length;
        return StringUtils.repeat(" ", Math.max(0, max));
    }

    private static String write(String s) {
        lines.add(s);
        int length = s.length();
        if (length > maxStringLength) {
            maxStringLength = length;
        }
        return s;
    }
}
