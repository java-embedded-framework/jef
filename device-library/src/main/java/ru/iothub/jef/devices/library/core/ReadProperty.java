

package ru.iothub.jef.devices.library.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReadProperty {
    String name();
    String description() default "";
    int order() default 0;
    //PropertyType type();
    boolean system() default false;
}
