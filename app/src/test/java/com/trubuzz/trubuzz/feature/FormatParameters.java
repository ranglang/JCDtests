package com.trubuzz.trubuzz.feature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by king on 2016/10/8.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FormatParameters {
    String[] value() default {};
    Class<?> source() ;
    String method() default "";
    String name() default "{index}";
}
