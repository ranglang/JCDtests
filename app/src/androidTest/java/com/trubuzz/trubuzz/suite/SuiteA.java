package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.LoginTest;
import com.trubuzz.trubuzz.test.login.tests.ForgetPwdReverseTest;
import com.trubuzz.trubuzz.test.login.tests.LoginReverseTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginReverseTest.class,
        ForgetPwdReverseTest.class
//        SignUpTest.class
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
