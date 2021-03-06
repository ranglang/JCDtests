package com.trubuzz.trubuzz.test.login.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
        ;
import com.trubuzz.trubuzz.constant.UserStore;
import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;
import com.trubuzz.trubuzz.shell.Password;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.common.CommonAction;
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
 * Created by king on 2017/6/20.
 */
@RunWith(JUnitParamsRunner.class)
public class ForgetPasswordTest extends BaseTest {
    private LoginService la = new LoginAction();
    private LoginView.Toast lt = new LoginView.Toast();

    @YamlFileName
    private final static String ymlFileName = "doLogin.yml";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    @Before
    public void not_login(){
        Wish.wantNotLogin();
    }

    /**
     * 检查找回密码 账号默认带入 , 并执行找回流程
     * @param account
     * @param accountType
     */
    @Test
//    @GenreParameter(value = {
//            "star003@abc.com ,MAIL",
//            "18111110001 ,PHONE"
//    },type = {String.class ,Account.class})
    @YmlParameter
    public void forget_password_account_follow(@Var("account") String account , @Var("accountType") Account accountType){
        la.type_username(account);

        la.into_forget_password_page();
        la.check_account_follow(accountType ,account);

        // 默认带入将不再执行密码重置操作
//        this.retrieve_password(accountType ,pwd);
    }

    /**
     * 使用邮箱找回密码 , 正向用例
     * 目前只做到发送成功消息提示
     * @param mail
     */
    @Test
//    @Parameters({"star003@abc.com"})
    @YmlParameter
    public void retrieve_password_use_mail(@Var("mail") String mail){
        la.into_forget_password_page();
        la.select_mail_retrieve();

        la.type_mail_address(mail);

        la.submit_mail_address();
        CommonAction.check_toast_msg(lt.reset_password_mail_sent_toast);

    }

    /**
     * 使用手机号找回密码 , 正向用例
     * @param phone
     * @param newPassword
     */
    @Test
//    @Parameters({
//            "11811110001, aA123321"
//    })
    @YmlParameter
    public void retrieve_password_use_phone(@Var("phone") String phone, @Var("newPassword") Password newPassword) {
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);

        la.get_sms_code();
        la.check_get_sms_code_successful();

        String smsCode = la.type_sms_code(phone, null);
        this.runTimeData("smsCode",smsCode);

        la.type_new_password(newPassword);
        la.type_confirm_password(newPassword);
        la.submit_phone_retrieve();
        la.check_retrieve_use_phone_successful();

        UserStore.updateLoginPassword(phone ,newPassword);
    }

}
