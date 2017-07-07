package com.trubuzz.trubuzz.feature.custom.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import junitparams.custom.CustomParameters;

/**
 * Created by king on 2017/7/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = GatherParameterProvider.class)
public @interface ValuesParameter {
    String[] value() default {};
}
