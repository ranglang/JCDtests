package com.trubuzz.trubuzz.feature.custom.parameters;

import com.trubuzz.trubuzz.feature.custom.parameters.provider.PickParameterProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import junitparams.custom.CustomParameters;

/**
 * Created by king on 2017/7/7.
 */
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = PickParameterProvider.class)
public @interface PickParameter {

    String[] value() default {};

    String[] arrange() default {"0:DEV","1:CN","2:GLOBAL"};
}
