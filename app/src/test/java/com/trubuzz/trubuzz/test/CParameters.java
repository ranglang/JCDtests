package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.bean.ImageS;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import junitparams.custom.CustomParameters;

import static com.trubuzz.trubuzz.bean.ImageS.FIX;

/**
 * Created by king on 2017/6/30.
 */
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = CT1.class)
public @interface CParameters {

    String[] value() default {};

    ImageS em() default FIX ;
}
