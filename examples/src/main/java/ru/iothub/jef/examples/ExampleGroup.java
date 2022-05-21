

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
