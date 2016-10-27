package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.PT1;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PT1.class })
public class Suite2 {
    private String string = "h";
    @BeforeClass
    public static void suite1setup(){
        System.out.println(Suite2.class.toString() + " before");
    }

    @AfterClass
    public static void teardown(){
        System.out.println(Suite2.class.toString() + " after");
    }

}
