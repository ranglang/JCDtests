package com.trubuzz.trubuzz.test.login.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
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

import static com.trubuzz.trubuzz.test.common.CommonAction.*;

/**
 * Created by king on 2017/6/20.
 */
@RunWith(JUnitParamsRunner.class)
public class ForgetPwdReverseTest extends BaseTest {
    private LoginService la = new LoginAction();
    private LoginView.Toast lt = new LoginView.Toast();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] retrieve_password_use_invalid_mail_data(){
        return new Object[]{
                create_retrieve_password_use_invalid_mail_data("star003@abc.com")
        };
    }
    private Object[] retrieve_password_use_phone_data(){
        return new Object[]{
                create_retrieve_password_use_phone_data("11811110001","aA123321")
        };
    }

    @Before
    public void setup(){
        Wish.wantNotLogin();
    }

    /**
     * 使用无效的邮箱找回密码
     * 目前只做到发送成功消息提示
     * @param mail
     */
    @Test
    @Parameters(method = "retrieve_password_use_invalid_mail_data")
    public void retrieve_password_use_invalid_mail(String mail){
        la.into_forget_password_page();
        la.select_mail_retrieve();

        la.type_mail_address(mail);

        la.submit_mail_address();
        la.check_invalid_mail_retrieve(mail);
    }
    private Object[] create_retrieve_password_use_invalid_mail_data(String mail){
        return new Object[]{mail};
    }

    // 无效格式的手机号获取验证码

    /**
     * 无效的手机号获取验证码
     * @param phone 手机号
     * @param format 格式是否正确 . true:正确
     */
    @Test
    @Parameters(method = "")
    public void invalid_format_phone_get_sms_code(String phone ,boolean format) {
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);
        la.get_sms_code();

        if (format) check_toast_msg(lt.user_not_exist_toast);
        else check_toast_msg(lt.incorrect_phone_format_toast);
    }

    ////--
    /**
     * 使用手机号找回密码
     * @param phone
     * @param password
     */
//    @Test
    @Parameters(method = "retrieve_password_use_phone_data")
    public void retrieve_password_use_phone(String phone, String password) {
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);

        la.get_sms_code();
        check_toast_msg(lt.sms_code_sent_toast);

        la.type_sms_code(phone ,null);
        la.type_new_password(password);
        la.type_confirm_password(password);
        la.submit_phone_retrieve();
        la.check_retrieve_use_phone_successful();
    }
    private Object[] create_retrieve_password_use_phone_data(String phone, String password) {
        return new Object[]{phone ,password};
    }


}
