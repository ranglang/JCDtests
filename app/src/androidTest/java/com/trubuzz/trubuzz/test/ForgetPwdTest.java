package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AForgetPwd;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.shell.ActivityElement;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.constant.ToastInfo.email_success_except;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_email_format_toast;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_password_format_toast;
import static com.trubuzz.trubuzz.constant.ToastInfo.sms_sent_toast;
import static com.trubuzz.trubuzz.constant.ToastInfo.user_not_exist_toast;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 16/11/1.
 */
@RunWith(JUnitParamsRunner.class)
public class ForgetPwdTest extends BaseTest{
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private AForgetPwd forgetPwd = new AForgetPwd();

    private Object[] email_found_data(){
        return new Object[]{
                new Object[]{"123@321.com" , user_not_exist_toast },
                new Object[]{"2343253543432" , incorrect_email_format_toast},
                new Object[]{"abc@abc.com1" , email_success_except}
        };
    }

    private Object[] phone_found_data(){
        return new Object[]{
//                new Object[]{"123" ,incorrect_phone_format_toast ,false , null ,null ,null , null },
//                new Object[]{"175000000000000000000000000000000000" ,incorrect_phone_format_toast ,false , null ,null ,null , null },
//                new Object[]{"17511110000" , sms_sent_toast ,true , null ,null ,null ,incorrect_sms_format_toast },
                new Object[]{"17511110000" , sms_sent_toast ,true , "9999999999999999999999999999" ,null ,null ,incorrect_password_format_toast },
                new Object[]{"17511110000" , sms_sent_toast ,true , "666666" ,null ,null ,incorrect_password_format_toast },
//                new Object[]{"17511110000" , sms_sent_toast ,true , "666666" ,"12345678" ,"12345678" ,incorrect_password_format_toast },
//                new Object[]{"17511110000" , sms_sent_toast ,true , "666666" ,"aA123456789012345" ,"aA123456789012345" ,incorrect_password_format_toast },
//                new Object[]{"17511110000" , sms_sent_toast ,true , "666666" ,"123321aA" ,"123321aA" ,error_captcha_code_toast },
        };
    }

    @Before
    public void into_password_found(){
        given(ALogin.forget_pwd_button).perform(click());
    }
    @Test
    public void default_show(){
        given(forgetPwd.use_email_found).check(matches(isSelected()));
        given(forgetPwd.use_phone_found)
                .check(matches(not(isSelected())))
                .perform(click())
                .check(matches(isSelected()));
        given(forgetPwd.use_email_found).check(matches(not(isSelected())));
    }

    @Test
    @Parameters(method = "email_found_data")
    public void use_email_found(String email , ActivityElement except){
        this.putData(new HashMap(){{
            put("email" ,email);
            put("except" , except.toString());
        }});

        given(forgetPwd.email_input).perform(replaceText(email));
        given(forgetPwd.email_submit_button).perform(click());
        given(except).check(matches(isDisplayed()));

        if(except.equals(email_success_except)){
            given(ALogin.account_input).check(matches(withText(email)));    //重置邮件发送成功后,会自动将Email填写只account输入框
        }
    }
    @Test
    @Parameters(method = "phone_found_data")
    public void use_phone_found(String phone , ActivityElement get_sms_except , boolean has_sms , String sms_code , String pwd , String confirm_pwd , ActivityElement submit_except){
        this.putData(new HashMap(){{
            put("phone" ,phone);        put("get_sms_except" ,get_sms_except.toString());
            put("has_sms" ,has_sms);    put("sms_code" ,sms_code);
            put("pwd" , pwd);           put("confirm_pwd" ,confirm_pwd);
            put("submit_except" ,submit_except.toString() );
        }});

        given(forgetPwd.use_phone_found).perform(click()).check(matches(isSelected()));
        given(forgetPwd.use_email_found).check(matches(not(isSelected())));
        given(forgetPwd.phone_input).perform(replaceText(phone)).check(matches(withText(phone)));
        given(forgetPwd.get_sms_button).perform(click());
        given(get_sms_except).check(matches(isDisplayed()));
        if(has_sms){
            given(forgetPwd.sms_input).check(matches(isDisplayed()));
            if(sms_code != null)
                given(forgetPwd.sms_input).perform(replaceText(sms_code));
                            // 目前没有做截取机制
//                        .check(matches(withText( sms_code.length()<6 ? sms_code : sms_code.substring(0 ,6 ))));    //检查自动截取6位验证码(小于6取自身)
            if(pwd != null)
                given(forgetPwd.password_input).perform(replaceText(pwd))
                        .check(matches(isPassword()));
            if(confirm_pwd != null)
                given(forgetPwd.password_confirm).perform(replaceText(confirm_pwd))
                        .check(matches(isPassword()));

            sleep(2000);        // 等待两秒到 上一个toast 消失
            given(forgetPwd.phone_submit_button)
                    .check(matches(isDisplayed()))
                    .perform(click());
            given(submit_except).check(matches(isDisplayed()));

        }


    }
}
