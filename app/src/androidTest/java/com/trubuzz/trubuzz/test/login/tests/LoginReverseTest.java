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
    private final static String ymlFileName = "login.yml";
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
//    @Parameters({
//            " , , false",           // 全部为空
//            " , sS123321, false",           // 空用户名
//            "star003@abc.com, , true",      //  空密码
//            "star003@abc.com, 111222, true",      // 密码错误
//    })
    @YmlParameter
    public void invalid_input_login(@Var("username") String username , @Var("password") String password ,
                                    @Var("usernameIsFormatted") String usernameIsFormatted){
        boolean _usernameIsFormatted = Boolean.valueOf(usernameIsFormatted);
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_invalid_login(_usernameIsFormatted, password);
//        check_current_activity(AName.LOGIN);
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
//    @Parameters({
//            "' or 1=1-- , sS123321, false"
//    })
    @YmlParameter
    public void sql_inject_login(@Var("username") String username , @Var("password") String password ,
                                 @Var("usernameIsFormatted") String usernameIsFormatted){
        String dir = "test_data/dev/";


        this.invalid_input_login(username ,password ,usernameIsFormatted);
    }

    /**
     * 验证 7 天未登录的邮箱账号
     * @param username
     * @param password
     * @param sendEmail 是否点击重发验证邮件 . true : 发送
     */
//    @Test
//    @Parameters({
//            "star006@abc.com, aA123321, false"
//    })
    @YmlParameter
    public void not_verify_7days(@Var("username") String username , @Var("password") String password ,
                                 @Var("sendEmail") String sendEmail){
        boolean  _sendEmail = Boolean.valueOf(sendEmail);
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_resend_mail_alert();
        la.resend_mail(_sendEmail);
//        check_current_activity(AName.LOGIN);
    }

}
