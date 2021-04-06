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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Sys implements NativeSupport {
    public final static int PATH_MAX = 4096;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Sys instance = null;

    public static Sys getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Sys.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Sys.class, false);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }

    public abstract long getuid();

    public abstract long geteuid();

    public abstract int getpid();

    public List<String> getUserGroups() {
        List<Integer> groupIDs = getgroups();

        List<String> result = new ArrayList<>();
        for (int i : groupIDs) {
            //System.out.println("Group ID: " + i);
            Group group = getgrgid(i);
            //System.out.println("group = " + group.getName());
            result.add(group.getName());
        }
        return result;
    }

    public boolean access(String filename, AccessFlag flag) throws NativeIOException {
        return access(filename, EnumSet.of(flag));
    }

    public abstract boolean access(String filename, EnumSet<AccessFlag> flags) throws NativeIOException;

    public abstract String getcwd() throws NativeIOException;

    protected abstract passwd getpwuid(long uid);

    //protected abstract int[] getgrouplist(passwd obj);
    protected abstract List<Integer> getgroups();

    protected abstract Group getgrgid(int gid);

    protected int flagToInt(EnumSet<AccessFlag> flags) {
        int result = 0;
        for (AccessFlag flag : flags) {
            result = result | flag.getValue();
        }
        return result;
    }

    public String getLogin() {
        passwd pw = getpwuid(getuid());
        return pw.username;
    }

    public enum AccessFlag {
        R_OK(4),        /* Test for read permission.  */
        W_OK(2),        /* Test for write permission.  */
        X_OK(1),        /* Test for execute permission.  */
        F_OK(0)    /* Test for existence.  */;
        int value;

        AccessFlag(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    protected static class passwd {
        String username;      /* Username.  */
        String password;      /* Hashed passphrase, if shadow database not in use (see shadow.h).  */
        int uid;              /* User ID.  */
        int gid;              /* Group ID.  */
        String realName;      /* Real name.  */
        String homeDirectory; /* Home directory.  */
        String shellProgram;  /* Shell program.  */

        public passwd(String username, String password, int uid, int gid, String realName, String homeDirectory, String shellProgram) {
            this.username = username;
            this.password = password;
            this.uid = uid;
            this.gid = gid;
            this.realName = realName;
            this.homeDirectory = homeDirectory;
            this.shellProgram = shellProgram;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getUid() {
            return uid;
        }

        public int getGid() {
            return gid;
        }

        public String getRealName() {
            return realName;
        }

        public String getHomeDirectory() {
            return homeDirectory;
        }

        public String getShellProgram() {
            return shellProgram;
        }

        @Override
        public String toString() {
            return "passwd{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", uid=" + uid +
                    ", gid=" + gid +
                    ", realName='" + realName + '\'' +
                    ", homeDirectory='" + homeDirectory + '\'' +
                    ", shellProgram='" + shellProgram + '\'' +
                    '}';
        }
    }

    protected static class Group {
        private final String name;              /*  char *gr_name; Group name.  */
        private final String password;            /*  char *gr_passwd; Password.    */
        private final int groupId;             /* __gid_t gr_gid; Group ID.    */
        //String[] gr_mem;              /* char **gr_mem; Member list. */

        public Group(String name, String password, int groupId) {
            this.name = name;
            this.password = password;
            this.groupId = groupId;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public int getGroupId() {
            return groupId;
        }
    }
}
