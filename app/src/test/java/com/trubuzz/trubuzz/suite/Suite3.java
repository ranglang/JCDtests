package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.PT2;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PT2.class})
public class Suite3 {
    @BeforeClass
    public static void suite1setup(){
        System.out.println(Suite3.class.toString() + " before");
    }

    @AfterClass
    public static void teardown(){
        System.out.println(Suite3.class.toString() + " after");
    }
}
