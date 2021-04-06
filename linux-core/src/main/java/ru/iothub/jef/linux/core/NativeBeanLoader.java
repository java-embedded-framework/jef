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


import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

class NativeBeanLoader<S> {
    private static final Logger log = Logger.getLogger(NativeBeanLoader.class.getName());

    static <S extends NativeSupport> S createContent(Class<S> service, boolean checkPermissions) {
        if(checkPermissions) {
            RuntimePermissionsChecker.check();
        }

        log.info(() ->
                String.format(
                        "Initialize %s linux access for %s type of application\n",
                        service.getName(),
                        LinuxUtils.isNative() ? "native" : "java")
        );

        ServiceLoader<S> loader = ServiceLoader.load(service);
        Iterator<S> iterator = loader.iterator();
        while (iterator.hasNext()) {
            try {
                S obj = iterator.next();
                log.log(Level.FINEST,
                        () -> String.format(
                                "Checking %s bean %s",
                                service.getName(), obj.getClass().getName()
                        )
                );
                if (LinuxUtils.isNative() == obj.isNativeSupported()) {
                    log.info(
                            () -> String.format(
                                    "Assign bean %s as %s provider",
                                    obj.getClass().getName(), service.getName()
                            )
                    );
                    return obj;
                }
            } catch (ServiceConfigurationError error) {
                log.warning("loadder warn: " + error.getMessage());
                error.printStackTrace();
            }
        }

        log.log(Level.WARNING,
                () -> String.format(
                        "Provider bean for %s linux access not found. Exit from application",
                        service.getName()
                )
        );
        System.exit(-1);
        return null;
    }

    static <S extends NativeSupport> S createContent(Class<S> service) {
        return createContent(service, true);
    }
}
