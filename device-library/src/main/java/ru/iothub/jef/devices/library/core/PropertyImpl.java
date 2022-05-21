 

package ru.iothub.jef.devices.library.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

class PropertyImpl implements Property {
    private final String name;
    //private final PropertyValueImpl value;
    private final Device object;
    private PropertyType propertyType;
    private TYPE type;
    private Method readMethod;
    private Method writeMethod;
    private int order;


    public PropertyImpl(String name, DeviceImpl device) {
        this.name = name;
        //this.type = type;
        this.object = device;
        //this.value = new PropertyValueImpl(device, type);
        construct();
    }

    private void construct() {
        Class<?> clazz = object.getClass();
        while (clazz != Object.class) {
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(ReadProperty.class)) {
                    ReadProperty annotation = method.getAnnotation(ReadProperty.class);
                    if (annotation.name().equals(name)) {
                        readMethod = method;
                        order = annotation.order();
                    }

                } else if (method.isAnnotationPresent(WriteProperty.class)) {
                    WriteProperty annotation = method.getAnnotation(WriteProperty.class);
                    if (annotation.name().equals(name)) {
                        writeMethod = method;
                        order = annotation.order();
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public PropertyType type() {
        return propertyType;
    }

    @Override
    public Object value() {
        try {
            if(type == TYPE.OUT) {
                throw new RuntimeException("Property '"+name+"' is write only");
            }
            return readMethod.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValue(Object value) {
        if(type == TYPE.IN) {
            throw new RuntimeException("Property '"+name+"' is read only");
        }
        if(value != null) {
            checkAcceptedTypes(value.getClass());
        }

        try {
            writeMethod.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isReadOnly() {
        return type == TYPE.IN;
    }

    @Override
    public boolean isWriteOnly() {
        return type == TYPE.OUT;
    }

    @Override
    public boolean isReadWrite() {
        return type == TYPE.IN_OUT;
    }

    public void setReadMethod(Method method) {
        if (method.getParameterCount() > 0) {
            throw new RuntimeException("Read method must have 0 parameters");
        }
        final Class<?> returnType = method.getReturnType();
        if (returnType.equals(Void.TYPE)) {
            throw new RuntimeException("Read method must return value");
        }
        checkAcceptedTypes(returnType);
        readMethod = method;
        //value.setReadMethod(method);
    }

    public void setWriteMethod(Method method) {
        if (method.getParameterCount() != 1) {
            throw new RuntimeException("Write method must have only 1 parameter");
        }
        final Class<?> returnType = method.getReturnType();
        if (!returnType.equals(Void.TYPE)) {
            throw new RuntimeException("Write method must be void type");
        }
        checkAcceptedTypes(method.getParameterTypes()[0]);
        writeMethod = method;
        //value.setWriteMethod(method);
    }

    private void checkAcceptedTypes(Class<?> classType) {
        propertyType = PropertyType.UNKNOWN;

        if (classType.equals(String.class)) {
            propertyType = PropertyType.STRING;
        } else if (classType.equals(Byte.TYPE) || classType.equals(byte.class)) {
            propertyType = PropertyType.BYTE;
        } else if (classType.equals(Short.TYPE) || classType.equals(short.class)) {
            propertyType = PropertyType.SHORT;
        } else if (classType.equals(Integer.TYPE) || classType.equals(int.class)) {
            propertyType = PropertyType.INTEGER;
        } else if (classType.equals(Long.TYPE) || classType.equals(long.class)) {
            propertyType = PropertyType.LONG;
        } else if (classType.equals(Double.TYPE) || classType.equals(double.class)) {
            propertyType = PropertyType.DOUBLE;
        } else if (classType.equals(Float.TYPE) || classType.equals(float.class)) {
            propertyType = PropertyType.FLOAT;
        } else if (classType.equals(Boolean.TYPE) || classType.equals(boolean.class)) {
            propertyType = PropertyType.BOOLEAN;
        } else if (classType.isEnum()) {
            propertyType = PropertyType.ENUM;
        } else {
            throw new RuntimeException("Class classType '" + classType.getName() + "' not supported for '" + name() + "'");
        }
        System.out.println("Class classType for " + name() + " is ok");
    }

    @Override
    public String toString() {
        return "PropertyImpl{" +
                "name='" + name + '\'' +
                ", order=" + order +
                ", type=" + propertyType +
                ", value=" + value() +
                ", obj=" + object +
                ", readMethod=" + readMethod +
                ", writeMethod=" + writeMethod +
                '}';
    }

    public void lock() {
        if (readMethod != null && writeMethod != null) {
            System.out.println("property '" + name() + "' is in/out");
            final Class<?> readReturn = readMethod.getReturnType();
            final Class<?> writeReturn = writeMethod.getParameterTypes()[0];
            if (!readReturn.equals(writeReturn)) {
                throw new RuntimeException("Read and write method for '" + name() + "' have different type of parameters ");
            }
            type = TYPE.IN_OUT;
        } else if (readMethod != null) {
            System.out.println("property '" + name() + "' is in");
            type = TYPE.IN;
        } else if (writeMethod != null) {
            System.out.println("property '" + name() + "' is out");
            type = TYPE.OUT;
        } else {
            throw new RuntimeException("Read/Write methods not set for '" + name() + "'");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyImpl property = (PropertyImpl) o;
        return name.equals(property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private enum TYPE {
        IN,
        OUT,
        IN_OUT
    }
}
