package com.trubuzz.trubuzz.test.login.tests;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.login.LoginAction;
import com.trubuzz.trubuzz.test.login.LoginService;
import com.trubuzz.trubuzz.test.login.LoginView;
import com.trubuzz.trubuzz.utils.AdminUtil;
import com.trubuzz.trubuzz.utils.FileRw;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Kvp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.trubuzz.trubuzz.constant.enumerate.Account.MAIL;
import static com.trubuzz.trubuzz.constant.enumerate.Account.PHONE;

/**
 * Created by king on 2017/6/20.
 */
@RunWith(JUnitParamsRunner.class)
public class ForgetPasswordTest extends BaseTest {
    private LoginService la = new LoginAction();
    private LoginView.Toast lt = new LoginView.Toast();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] forget_password_account_follow_data(){
        return new Object[]{
                create_forget_password_account_follow_data("star003@abc.com" ,MAIL )
        };
    }
    private Object[] forget_password_use_mail_data(){
        return new Object[]{
                create_forget_password_use_mail_data("star003@abc.com")
        };
    }
    private Object[] forget_password_use_phone_data(){
        return new Object[]{
                create_forget_password_use_phone_data("11811110001","aA123321")
        };
    }

    @Before
    public void setup(){
        Wish.wantNotLogin();
    }

    /**
     * 检查找回密码 账号默认带入 , 并执行找回流程
     * @param account
     * @param accountType
     */
//    @Test
    @Parameters(method = "forget_password_account_follow_data")
    public void forget_password_account_follow(String account , Account accountType){
        la.type_username(account);

        la.into_forget_password_page();
        la.check_account_follow(accountType ,account);

        // 默认带入将不再执行密码重置操作
//        this.retrieve_password(accountType ,pwd);
    }
    private Object[] create_forget_password_account_follow_data(String account , Account accountType ){
        return new Object[]{account ,accountType };
    }

    /**
     * 使用邮箱找回密码 , 正向用例
     * @param mail
     */
//    @Test
    @Parameters(method = "forget_password_use_mail_data")
    public void forget_password_use_mail(String mail){
        la.into_forget_password_page();
        la.select_mail_retrieve();

        la.type_mail_address(mail);

        la.submit_mail_address();
        CommonAction.check_toast_msg(lt.reset_password_mail_sent_toast);
    }
    private Object[] create_forget_password_use_mail_data(String mail){
        return new Object[]{mail};
    }

    /**
     * 使用手机号找回密码 , 正向用例
     * @param phone
     * @param password
     */
    @Test
    @Parameters(method = "forget_password_use_phone_data")
    public void forget_password_use_phone(String phone, String password) {
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);

        la.get_sms_code();
        CommonAction.check_toast_msg(lt.sign_up_sms_auth_sent_toast);

        la.type_sms_code(phone ,null);
        la.type_new_password(password);
        la.type_confirm_password(password);
        la.submit_phone_retrieve();
        la.check_retrieve_use_phone_successful();
    }
    private Object[] create_forget_password_use_phone_data(String phone, String password) {
        return new Object[]{phone ,password};
    }


}