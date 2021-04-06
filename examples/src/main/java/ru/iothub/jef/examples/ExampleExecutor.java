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

package ru.iothub.jef.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for execution
 */
public class ExampleExecutor {
    /**
     * Exec method
     * @param args not used
     * @throws Exception if something happen
     */
    public static void main(String[] args) throws Exception {
        boolean debug = "true".equals(System.getProperty("debug"));
        if(debug) {
            System.out.println("Examples runned in DEBUG mode");
        }

        for (; ; ) {
            cls();
            System.out.println("******************** Examples ********************");

            int key = 1;
            for (ExampleGroup group : ExampleManager.getExamples()) {
                System.out.println("--------------- " + group.getName()+ " ---------------");
                for (Example example : group.getItems()) {
                    System.out.println((key) + ". " + example.getName());
                    key++;
                }
            }

            System.out.println("**************************************************");
            System.out.println("Press a key for selecting example or 'q' to exit.");

            String line = readLine();
            if ("q".equalsIgnoreCase(line)) {
                //System.out.println("Exit from application");
                System.exit(0);
            }

            try {
                int index = Integer.parseInt(line) - 1;
                cls();
                ExampleManager.execute(index);
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
            }

        }

    }

    private static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Read line from console
     * @return readed string
     * @throws IOException if some I/O error happen
     */
    public static String readLine() throws IOException {
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
