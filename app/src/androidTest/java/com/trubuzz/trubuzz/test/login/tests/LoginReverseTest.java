package com.trubuzz.trubuzz.test.login.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.login.LoginAction;
import com.trubuzz.trubuzz.test.login.LoginService;
import com.trubuzz.trubuzz.test.login.LoginView;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

/**
 * Created by king on 2017/6/16.
 */
@RunWith(JUnitParamsRunner.class)
public class LoginReverseTest extends BaseTest {
    private final LoginService la = new LoginAction();
    private final LoginView.Toast lt = new LoginView.Toast();
    @YamlFileName
    private final static String ymlFileName = "doLogin.yml";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    @Before
    public void logout(){
        Wish.wantNotLogin();
    }

    /**
     * 可测试所以无效的登录, 变化全看data的设计
     * @param username
     * @param password
     */
    @Test
    @YmlParameter
    public void invalid_input_login(@Var("username") String username , @Var("password") String password ,
                                    @Var("usernameIsFormatted") String usernameIsFormatted){
        boolean _usernameIsFormatted = Boolean.valueOf(usernameIsFormatted);
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_invalid_login(_usernameIsFormatted, password);
        la.check_still_in_login_page();
    }

    /**
     * sql 注入攻击方式登录测试
     * 步骤同无效输入登录{@link #invalid_input_login(String, String, String)} ,故
     *      共享其data创建方法
     * @param username
     * @param password
     * @param usernameIsFormatted
     */
    @Test
    @YmlParameter
    public void sql_inject_login(@Var("username") String username , @Var("password") String password ,
                                 @Var("usernameIsFormatted") String usernameIsFormatted){
        this.invalid_input_login(username ,password ,usernameIsFormatted);
    }

    /**
     * 验证 7 天未登录的邮箱账号
     * @param username
     * @param password
     * @param sendEmail 是否点击重发验证邮件 . true : 发送
     */
    @Test
    @YmlParameter
    public void not_verify_7days(@Var("username") String username , @Var("password") String password ,
                                 @Var("sendEmail") String sendEmail){
        boolean  _sendEmail = Boolean.valueOf(sendEmail);
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_resend_mail_alert();
        la.resend_mail(_sendEmail);
        la.check_still_in_login_page();
    }

}
