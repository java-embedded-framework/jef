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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents group of example and used for combining examples to some ageas
 */
class ExampleGroup {
    private final String name;
    private final List<Example> items;

    /**
     * Allocates new instance
     * @param name name of grour
     */
    public ExampleGroup(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    /**
     * Gets name of example group
     * @return name of example group
     */
    public String getName() {
        return name;
    }

    /**
     * Adding example to this group
     * @param e instance of example
     */
    public void add(Example e) {
        items.add(e);
    }

    public List<Example> getItems() {
        return Collections.unmodifiableList(items);
    }
}
