package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.SignUpTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//        LoginTest.class,
        SignUpTest.class
})
public class SuiteA extends BaseSuite{
    private static final String suiteDesc = "login test ";
    private static final Class suiteClass = SuiteA.class;


    @BeforeClass
    public static void setup(){
        BaseSuite.init(suiteDesc  , suiteClass);
        BaseSuite.baseSetup();
    }
}
