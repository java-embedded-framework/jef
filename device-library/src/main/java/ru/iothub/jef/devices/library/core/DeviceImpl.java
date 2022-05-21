 

package ru.iothub.jef.devices.library.core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class DeviceImpl implements Device {
    Map<String, Property> systemProperties = new LinkedHashMap<>();
    Map<String, Property> userProperties = new LinkedHashMap<>();

    public DeviceImpl() {
        parseProperties();
        buildProperties();
        sortProperties();
    }

    private void sortProperties() {
        sortProperties(systemProperties);
        sortProperties(userProperties);
        systemProperties = Collections.unmodifiableMap(systemProperties);
        userProperties = Collections.unmodifiableMap(userProperties);
    }

    private void sortProperties(Map<String, Property> map) {
        List<Property> list = new ArrayList<>(map.values());
        list.sort(Comparator.comparing(Property::getOrder));
        final Map<String, Property> collect = list.stream().collect(Collectors.toMap(Property::name, item -> item));
        map.clear();
        map.putAll(collect);
    }

    private void parseProperties() {
        Class<?> clazz = this.getClass();
        while (clazz != Object.class) {
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(ReadProperty.class)) {
                    setReadMethod(method);
                } else if (method.isAnnotationPresent(WriteProperty.class)) {
                    setWriteMethod(method);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private void buildProperties() {
        for(Property p : systemProperties.values()) {
            lockProperty((PropertyImpl)p);
        }
        for(Property p : userProperties.values()) {
            lockProperty((PropertyImpl)p);
        }
    }

    private void lockProperty(PropertyImpl property) {
        property.lock();
    }

    private void setReadMethod(Method method) {
        ReadProperty annotation = method.getAnnotation(ReadProperty.class);
        boolean sys = annotation.system();

        Map<String, Property> map = sys ? systemProperties : userProperties;
        String name = annotation.name();
        PropertyImpl property = (PropertyImpl)map.get(name);
        if (property == null) {
            property = new PropertyImpl(name, this);
            map.put(name, property);
        }
        property.setReadMethod(method);
    }

    private void setWriteMethod(Method method) {
        WriteProperty annotation = method.getAnnotation(WriteProperty.class);
        boolean sys = annotation.system();
        Map<String, Property> map = sys ? systemProperties : userProperties;
        String name = annotation.name();
        PropertyImpl property = (PropertyImpl)map.get(name);
        if (property == null) {
            property = new PropertyImpl(name, this);
            map.put(name, property);
        }

        property.setWriteMethod(method);
    }


    @Override
    public Map<String, Property> getUserProperties() {
        return userProperties;
    }

    @Override
    public Map<String, Property> getSystemProperties() {
        return systemProperties;
    }

    public void dumpProperties() {
        System.out.println("System properties:");
        for(Property p : systemProperties.values()) {
            System.out.println("p = " + p);
        }
        System.out.println("\nUser properties:");
        for(Property p : userProperties.values()) {
            System.out.println("p = " + p);
        }
    }
}
