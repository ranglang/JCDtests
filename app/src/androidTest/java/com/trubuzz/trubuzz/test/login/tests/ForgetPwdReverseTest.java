package com.trubuzz.trubuzz.test.login.tests;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.feature.custom.parameters.GatherParameter;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.login.LoginAction;
import com.trubuzz.trubuzz.test.login.LoginService;
import com.trubuzz.trubuzz.test.login.LoginView;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Judge;
import com.trubuzz.trubuzz.utils.Kvp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.trubuzz.trubuzz.constant.Conf.p_s;
import static com.trubuzz.trubuzz.test.common.CommonAction.*;

/**
 * Created by king on 2017/6/20.
 */
@RunWith(JUnitParamsRunner.class)
public class ForgetPwdReverseTest extends BaseTest {
    private LoginService la = new LoginAction();
    private LoginView.Toast lt = new LoginView.Toast();
    private final String _phone = "11811110001";
    private final String _password = "qQ123321";
    private final String key_pwd = "password";
    private final String key_pwd_confirm = "password_confirm";
    private final String key_format = "isFormat";
    private final String invalid_password_padding = p_s + key_pwd + "," + key_pwd_confirm + "," + key_format;


    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void not_login(){
        Wish.wantNotLogin();
    }

    /**
     * 使用无效的邮箱找回密码
     * 目前只做到发送成功消息提示
     * @param mail
     */
    @Test
    @Parameters({"star1003@abc.com"})
    public void retrieve_password_use_invalid_mail(@Var("mail") String mail){
        la.into_forget_password_page();
        la.select_mail_retrieve();

        la.type_mail_address(mail);

        la.submit_mail_address();
        la.check_invalid_mail_retrieve(mail);
    }

    /**
     * 无效的手机号获取验证码
     * @param phone 手机号
     * @param isFormatted 格式是否正确 . true:正确
     */
    @Test
    @Parameters({
            "11800001111,true",
            "00001111,false",
    })
    public void invalid_phone_get_sms_code(@Var("phone") String phone ,@Var("isFormatted") String isFormatted) {
        boolean _isFormatted = Boolean.valueOf(isFormatted);
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);
        la.get_sms_code();

        if (_isFormatted) check_toast_msg(lt.user_not_exist_toast);
        else check_toast_msg(lt.incorrect_phone_format_toast);
    }


    /**
     * 使用无效的短信验证码重置密码
     *      空输入 , 长度无效 , 格式正确但错误( 当前格式: 不少于6位数字 )
     * @param smsCodes 错误的格式 或 错误的数字
     */
    @Test
    @GatherParameter(
            "['','123','0000000']"
    )
    public void invalid_sms_code_reset_password(@Var("smsCodes") ArrayList<String> smsCodes){
        this.runTimeData("phone",_phone,"password",_password);
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(_phone);
        la.get_sms_code();
        check_toast_msg(lt.sms_code_sent_toast);

        for(String smsCode : smsCodes) {
            la.type_sms_code(_phone, smsCode);
            la.type_new_password(_password);
            la.type_confirm_password(_password);
            la.submit_phone_retrieve();

            if (Judge.isMatched(smsCode, "^\\d{6,}")) check_toast_msg(lt.sms_code_error_toast);
            else check_toast_msg(lt.incorrect_sms_format_toast);
        }
    }


    /**
     * 重置密码时使用无效的密码
     * 两次不一致 , 格式错误 , 空值
     * 为避免多次获取验证码 , 提升测试效率 :
     * 1. 使用固定的手机号
     * 2. 在填写好验证码后 , 使用 for 控制多次循环验证
     * @param invalidNewPasswords 将使用自定义参数化方式来注入参数
     *          key_pwd : 新密码
     *          key_pwd_confirm : 确认新密码
     *          key_format : 是否是格式化的 ( 正确的格式 : true )
     */
    @Test
    @GatherParameter({
            "{%s:'qQ123456',%s:'Ww123456',%s:true}" + invalid_password_padding,  // 两次密码不一致
            "{%s:'qq123456',%s:'qq123456',%s:false}" + invalid_password_padding,  // 格式错误
            "{%s:'',%s:'',%s:false}" + invalid_password_padding // 空值
    })
    public void invalid_new_password_retrieve_with_phone(@Var("invalidNewPasswords") ArrayList<HashMap> invalidNewPasswords) {
        la.into_forget_password_page();
        la.select_phone_retrieve();

        this.runTimeData("phone",_phone);
        la.type_phone_number(_phone);
        la.get_sms_code();
        check_toast_msg(lt.sms_code_sent_toast);

        String smsCode = la.type_sms_code(_phone, null);
        this.runTimeData("smsCode", smsCode);

        for (Map<String, Object> kvp : invalidNewPasswords) {
            String pwd = (String) kvp.get(key_pwd);
            String pwd_c = (String) kvp.get(key_pwd_confirm);

            la.type_new_password(pwd);
            la.type_confirm_password(pwd_c);
            la.submit_phone_retrieve();

            if (!(boolean) kvp.get(key_format)) {
                check_toast_msg(lt.incorrect_password_format_toast);
            } else if (!pwd.equals(pwd_c)) {
                check_toast_msg(lt.incorrect_password_confirm_toast);
            } else {
                Log.e(TAG, String.format("invalid_new_password_retrieve_with_phone: " +
                        "数据设计错误 ,反向用例设计了正向的数据  : \n " +
                        "password : %s ; password confirm : %s", pwd, pwd_c));
            }

        }
    }


}
