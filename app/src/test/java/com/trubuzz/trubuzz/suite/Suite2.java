package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.PT1;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PT1.class })
public class Suite2 extends Su{
    static {
        init(Suite2.class.toString());
    }
    @BeforeClass
    public static void aa(){
        init(Suite2.class.toString());
        Su.aa();
    }

}
