package com.trubuzz.trubuzz.test.login.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.login.LoginAction;
import com.trubuzz.trubuzz.test.login.LoginService;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 17/6/9.
 */

public class LoginTest extends BaseTest{
    private final LoginService la = new LoginAction();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void logout(){
        Wish.logout();
    }

    @Test
    public void has_broker_login(String username ,String password){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_broker();
        sleep(30000);
    }

    @Test
    public void has_not_broker_login(String username ,String password){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_not_broker();
    }
}
