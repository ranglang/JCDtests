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
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 17/6/9.
 */
@RunWith(JUnitParamsRunner.class)
public class LoginTest extends BaseTest{
    private final LoginService la = new LoginAction();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] has_broker_login_data(){
        return new Object[]{
                create_has_broker_login_data("zhao.deng@jucaidao.com" ,"aA123456")
        };
    }

    private Object[] has_not_broker_login_data(){
        return new Object[]{
                create_has_not_broker_login_data("11811110001" ,"aA123321")
        };
    }

    @Before
    public void logout(){
        Wish.wantNotLogin();
    }

    /**
     * 已开户用户登录
     * @param username
     * @param password
     */
    @Test
    @Parameters(method = "has_broker_login_data")
    public void has_broker_login(String username ,String password){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_broker();
        sleep(30000);
    }
    private Object[] create_has_broker_login_data(String username ,String password){
        return new Object[]{username ,password};
    }

    /**
     * 未开户用户登录
     * @param username
     * @param password
     */
    @Test
    @Parameters(method = "has_not_broker_login_data")
    public void has_not_broker_login(String username ,String password){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_not_broker();
    }
    private Object[] create_has_not_broker_login_data(String username ,String password){
        return new Object[]{username ,password};
    }

    /**
     * 测试清除密码的小功能
     */
    @Test
    public void clean_password_test(){
        la.type_password("380275024");
        la.clean_password();
        la.check_password_input_default_show();
    }
}
