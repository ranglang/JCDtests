package com.trubuzz.trubuzz.test.login.tests;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;
import com.trubuzz.trubuzz.shell.Password;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.login.LoginAction;
import com.trubuzz.trubuzz.test.login.LoginService;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.jar.Attributes;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 17/6/9.
 */
@RunWith(JUnitParamsRunner.class)
public class LoginTest extends BaseTest{
    private final LoginService la = new LoginAction();
    @YamlFileName
    private final static String ymlFileName = "login.yml";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.SplashActivity));


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
    @YmlParameter
    public void has_broker_login(@Var("username") String username , @Var("password") Password password){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();

        la.check_broker();
    }

    /**
     * 未开户用户登录
     * @param username
     * @param password
     */
//    @Test
    @YmlParameter
    public void has_not_broker_login(@Var("username") String username , @Var("password") Password password){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
//        Intent intent = new Intent(matr.getActivity() ,God.getFixedClass(AName.MAIN));
//        matr.getActivity().startActivity(intent);
        la.check_not_broker();
    }
    /**
     * 测试清除密码的小功能
     */
    @Test
    public void clean_password_test(){
        la.type_password(new Password("380275024"));
        la.clean_password();
        la.check_password_input_default_show();
    }
}
