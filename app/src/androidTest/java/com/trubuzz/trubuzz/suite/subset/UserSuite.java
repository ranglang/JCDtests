package com.trubuzz.trubuzz.suite.subset;

import com.trubuzz.trubuzz.suite.BaseSuite;
import com.trubuzz.trubuzz.test.login.tests.ForgetPasswordTest;
import com.trubuzz.trubuzz.test.login.tests.ForgetPwdReverseTest;
import com.trubuzz.trubuzz.test.login.tests.LoginReverseTest;
import com.trubuzz.trubuzz.test.login.tests.LoginTest;
import com.trubuzz.trubuzz.test.signUp.tests.SignUpReverseTest;
import com.trubuzz.trubuzz.test.signUp.tests.SignUpTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 2017/7/10.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
//        LoginTest.class,
        LoginReverseTest.class,
        ForgetPasswordTest.class,
        ForgetPwdReverseTest.class,
//        SignUpTest.class,
        SignUpReverseTest.class
})
public class UserSuite extends BaseSuite {
    private static final String suiteDesc = "user identity authentication test . ";
    private static final Class suiteClass = UserSuite.class;


    @BeforeClass
    public static void setup(){
        BaseSuite.init(suiteDesc  , suiteClass);
        BaseSuite.baseSetup();
    }
}
