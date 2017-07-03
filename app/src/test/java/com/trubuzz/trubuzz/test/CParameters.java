package com.trubuzz.trubuzz.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import junitparams.custom.CustomParameters;

/**
 * Created by king on 2017/6/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = CT1.class)
public @interface CParameters {

    String[] value() default {};
}
