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

import java.util.List;
import java.util.Map;

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
    private final String _phone = "11811110001";
    private final String key_pwd = "password";
    private final String key_pwd_confirm = "password_confirm";
    private final String key_format = "isFormat";

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

    /**
     * 无效的手机号获取验证码
     * @param phone 手机号
     * @param format 格式是否正确 . true:正确
     */
    @Test
    @Parameters(method = "")
    public void invalid_phone_get_sms_code(String phone ,boolean format) {
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);
        la.get_sms_code();

        if (format) check_toast_msg(lt.user_not_exist_toast);
        else check_toast_msg(lt.incorrect_phone_format_toast);
    }


    // 无效验证码重置

    /**
     * 使用无效的验证码重置密码
     *      空输入 , 长度无效 , 格式正确但错误( 当前格式: 不少于6位数字 )
     * @param phone
     * @param code 错误的格式 或 错误的数字
     */
    @Test
    @Parameters(method = "")
    public void invalid_sms_code_reset_password(String phone ,String code ,String password){
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(phone);
        la.get_sms_code();
        check_toast_msg(lt.sms_code_sent_toast);

        la.type_sms_code(phone ,code);
        la.type_new_password(password);
        la.type_confirm_password(password);
        la.submit_phone_retrieve();

        if (Judge.isMatched(code, "^\\d{6,}")) check_toast_msg(lt.sms_code_error_toast);
        else check_toast_msg(lt.incorrect_sms_format_toast);
    }


    /**
     * 重置密码时使用无效的密码
     *      两次不一致 , 格式错误 , 空值
     * 为避免多次获取验证码 , 提升测试效率 :
     *      1. 使用固定的手机号
     *      2. 在填写好验证码后 , 使用 for 控制多次循环验证
     * @param p
     */
    public void invalid_new_password_retrieve_with_phone(List<Map<String,Object>> p){
        la.into_forget_password_page();
        la.select_phone_retrieve();

        la.type_phone_number(_phone);
        la.get_sms_code();
        check_toast_msg(lt.sms_code_sent_toast);

        la.type_sms_code(_phone ,null);
        for (Map<String,Object> kvp : p) {
            String pwd = (String) kvp.get(key_pwd);
            String pwd_c = (String) kvp.get(key_pwd_confirm);

            la.type_new_password(pwd);
            la.type_confirm_password(pwd_c);
            la.submit_phone_retrieve();

            if (!(boolean) kvp.get(key_format)) {
                check_toast_msg(lt.incorrect_password_format_toast);
            }else
            if (!pwd.equals(pwd_c)) {
                check_toast_msg(lt.incorrect_password_confirm_toast);
            } else {
                Log.e(TAG, String.format("invalid_new_password_retrieve_with_phone: " +
                        "数据设计错误 ,反向用例设计了正向的数据  : \n " +
                        "password : %s ; password confirm : %s",pwd ,pwd_c ));
            }

        }
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
