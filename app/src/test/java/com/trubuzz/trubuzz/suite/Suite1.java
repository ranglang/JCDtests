package com.trubuzz.trubuzz.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Suite2.class,
        Suite3.class
})
public class Suite1 {
    @BeforeClass
    public static void suite1setup(){
        System.out.println(Suite1.class.toString() + " before");
    }

    @AfterClass
    public static void teardown(){
        System.out.println(Suite1.class.toString() + " after");
    }
}
