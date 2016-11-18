package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.NounsTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//        SignUpTest.class,
//        ForgetPwdTest.class,
        NounsTest.class
})
public class SuiteB extends BaseSuite{
    private static final String suiteDesc = "sign up test";
    private static final Class suiteClass = SuiteB.class;


    @BeforeClass
    public static void setup(){
        BaseSuite.init(suiteDesc  , suiteClass);
        BaseSuite.baseSetup();
    }
}
