package com.trubuzz.trubuzz.test;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by king on 16/10/4.
 */
@RunWith(JUnitParamsRunner.class)
public class T1 {

    @Test
    @Parameters({"17, false",
            "22, true" })
    public void personIsAdult(int age, boolean valid) throws Exception {
        assertThat(age >= 18 ,is(valid));
    }


    @Test
    @Parameters({"19, false",
            "2, true" })
//    @Ignore
    public void personIsNotAdult(int age, boolean valid) throws Exception {
        assertThat(age >= 18 , is(valid));
    }
}
