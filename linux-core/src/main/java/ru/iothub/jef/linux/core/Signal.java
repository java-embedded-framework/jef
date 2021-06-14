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

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Signal implements NativeSupport {
    public static final int SIGHUP = 1;
    public static final int SIGINT = 2;
    public static final int SIGQUIT = 3;
    public static final int SIGILL = 4;
    public static final int SIGTRAP = 5;
    public static final int SIGABRT = 6;
    public static final int SIGIOT = 6;
    public static final int SIGBUS = 7;
    public static final int SIGFPE = 8;
    public static final int SIGKILL = 9;
    public static final int SIGUSR1 = 10;
    public static final int SIGSEGV = 11;
    public static final int SIGUSR2 = 12;
    public static final int SIGPIPE = 13;
    public static final int SIGALRM = 14;
    public static final int SIGTERM = 15;
    public static final int SIGSTKFLT = 16;
    public static final int SIGCHLD = 17;
    public static final int SIGCONT = 18;
    public static final int SIGSTOP = 19;
    public static final int SIGTSTP = 20;
    public static final int SIGTTIN = 21;
    public static final int SIGTTOU = 22;
    public static final int SIGURG = 23;
    public static final int SIGXCPU = 24;
    public static final int SIGXFSZ = 25;
    public static final int SIGVTALRM = 26;
    public static final int SIGPROF = 27;
    public static final int SIGWINCH = 28;
    public static final int SIGIO = 29;
    public static final int SIGPOLL = SIGIO;
    /*
    public static final int  SIGLOST         29
    */
    public static final int SIGPWR = 30;
    public static final int SIGSYS = 31;
    public static final int SIGUNUSED = 31;


    /*
     * SA_FLAGS values:
     *
     * SA_ONSTACK indicates that a registered stack_t will be used.
     * SA_RESTART flag to get restarting signals (which were the default long ago)
     * SA_NOCLDSTOP flag to turn off SIGCHLD when children stop.
     * SA_RESETHAND clears the handler when the signal is delivered.
     * SA_NOCLDWAIT flag on SIGCHLD to inhibit zombies.
     * SA_NODEFER prevents the current signal from being masked in the handler.
     *
     * SA_ONESHOT and SA_NOMASK are the historical Linux names for the Single
     * Unix names RESETHAND and NODEFER respectively.
     */
    public static final int SA_NOCLDSTOP = 0x00000001;
    public static final int SA_NOCLDWAIT = 0x00000002;
    public static final int SA_SIGINFO = 0x00000004;
    public static final int SA_ONSTACK = 0x08000000;
    public static final int SA_RESTART = 0x10000000;
    public static final int SA_NODEFER = 0x40000000;
    public static final int SA_RESETHAND = 0x80000000;

    public static final int SA_NOMASK = SA_NODEFER;
    public static final int SA_ONESHOT = SA_RESETHAND;


    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static Signal instance = null;

    public static Signal getInstance() {
        if (instance == null && !initialized.get()) {
            synchronized (Signal.class) {
                if (instance == null && !initialized.get()) {
                    instance = NativeBeanLoader.createContent(Signal.class);
                    initialized.set(true);
                }
            }
        }
        return instance;
    }

    public abstract int sigaction(int sigint, Sigaction sa, Sigaction old);

    /*
    struct sigaction {
            __sighandler_t sa_handler;
            unsigned long sa_flags;
    #ifdef SA_RESTORER
            __sigrestore_t sa_restorer;
    #endif
            sigset_t sa_mask;
    };

    typedef void __signalfn_t(int);
    typedef __signalfn_t *__sighandler_t;

    typedef void __restorefn_t(void);
    typedef __restorefn_t *__sigrestore_t;

    public static final int  SIG_DFL ((__sighandler_t)0)     // default signal handling
    public static final int  SIG_IGN ((__sighandler_t)1)     // ignore signal
    public static final int  SIG_ERR ((__sighandler_t)-1)    // error return from signal

     */
    public interface SignalHandler {
        void handle(int value);
    }

    public static class Sigaction {
        private final SignalHandler handler;
        private final long saFlags;

        public Sigaction(SignalHandler handler, long saFlags) {
            this.handler = handler;
            this.saFlags = saFlags;
        }

        public SignalHandler getHandler() {
            return handler;
        }

        public long getSaFlags() {
            return saFlags;
        }
    }
}
