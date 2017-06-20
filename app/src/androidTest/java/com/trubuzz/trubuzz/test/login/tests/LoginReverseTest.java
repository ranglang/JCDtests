package com.trubuzz.trubuzz.test.login.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;
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
import junitparams.Parameters;

import static com.trubuzz.trubuzz.test.common.CommonAction.check_current_activity;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 2017/6/16.
 */
@RunWith(JUnitParamsRunner.class)
public class LoginReverseTest extends BaseTest {
    private final LoginService la = new LoginAction();
    private final LoginView.Toast lt = new LoginView.Toast();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] invalid_input_login_data(){
        return new Object[]{
                create_invalid_input_login_data("", "sS123321", lt.account_format_toast)
        };
    }
    private Object[] sql_inject_login_data(){
        return new Object[]{
                create_invalid_input_login_data("' or 1=1--", "sS123321", lt.account_format_toast)
        };
    }
    private Object[] not_verify_7days_data(){
        return new Object[]{
                create_not_verify_7days_data("star006@abc.com","aA123321",false)
        };
    }

    /****/


    @Before
    public void logout(){
        Wish.wantNotLogin();
    }

    /**
     * 可测试所以无效的登录, 变化全看data的设计
     * @param username
     * @param password
     * @param expect
     */
    @Test
    @Parameters(method = "invalid_input_login_data")
    public void invalid_input_login(String username , String password , ToastElement expect){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        check_toast_msg(expect);
        check_current_activity(AName.LOGIN);
    }
    private Object[] create_invalid_input_login_data(String username , String password , ToastElement expect){
        return new Object[]{username, password, expect};
    }

    /**
     * sql 注入攻击方式登录测试
     * 步骤同无效输入登录{@link #invalid_input_login(String, String, ToastElement)} ,故
     *      共享其data创建方法
     * @param username
     * @param password
     * @param expect
     */
    @Test
    @Parameters(method = "sql_inject_login_data")
    public void sql_inject_login(String username , String password , ToastElement expect){
        this.invalid_input_login(username ,password ,expect);
    }

    /**
     * 验证 7 天未登录的邮箱账号
     * @param username
     * @param password
     * @param sendEmail
     */
    @Test
    @Parameters(method = "not_verify_7days_data")
    public void not_verify_7days(String username , String password ,boolean sendEmail){
        la.type_username(username);
        la.type_password(password);
        la.click_login_button();
        la.check_resend_mail_alert();
        la.resend_mail(sendEmail);
        check_current_activity(AName.LOGIN);
    }
    private Object[] create_not_verify_7days_data(String username , String password ,boolean sendEmail){
        return new Object[]{username ,password ,sendEmail};
    }


}
