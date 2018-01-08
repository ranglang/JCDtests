package com.trubuzz.trubuzz.test.signUp.tests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;
import com.trubuzz.trubuzz.shell.UserName;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.test.signUp.actions.SignUpAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import junitparams.JUnitParamsRunner;

import static com.trubuzz.trubuzz.constant.Config.CURRENT_IMAGE_STRATEGY;
import static com.trubuzz.trubuzz.constant.Config.default_country_code;
import static com.trubuzz.trubuzz.constant.Env.condition;
import static com.trubuzz.trubuzz.constant.enumerate.Account.PHONE;
import static com.trubuzz.trubuzz.constant.enumerate.Condition.CN;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 2017/7/6.
 */
@RunWith(JUnitParamsRunner.class)
public class SignUpReverseTest extends BaseTest {
    private SignUpService ss = new SignUpAction();
    private final String key_pwd = "password";
    private final String key_pwd_confirm = "password_confirm";
    private final String key_format = "isFormat";
    private final UserName _phone = new UserName("11211110001");
    private final UserName _email = new UserName("aabbcc@123.com");
    private final String _password = "qQ123456";

    @YamlFileName
    private final static String ymlFileName = "signUp.yml";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void into_sign_up(){
        Wish.wantNotLogin();
        ss.into_sign_up_page();
    }

    /**
     * 无效的邮箱地址注册
     *      无效场景: 已注册 , 格式不正确 , 邮箱地址不存在 , 空输入
     * @param email
     */
    @Test
    @YmlParameter
    public void invalid_email_address_sign_up(@Var("email") UserName email) {
        this.runTimeData("password",_password);

        ss.type_email_address(email);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.submit_email_sign_up();

        ss.check_invalid_email_sign_up(email ,this);
    }

    /**
     * 邮箱注册使用无效的密码
     *      无效场景: 格式不正确 , 两次输入不一致 , 空输入
     * @param invalidPasswords
     */
    @Test
    @YmlParameter
    public void invalid_password_sign_up_with_email(@Var("email") UserName email,
                                                    @Var("invalidPasswords") ArrayList<HashMap> invalidPasswords) {
        ss.type_email_address(email);
        for(HashMap map : invalidPasswords) {
            String password = (String) map.get(key_pwd);
            String confirmPassword = (String) map.get(key_pwd_confirm);
            boolean isFormat = (boolean) map.get(key_format);

            ss.type_password(password);
            ss.type_confirm_password(confirmPassword);

            Espresso.closeSoftKeyboard();
            ss.agree_with_the_terms(true);
            ss.submit_email_sign_up();

            ss.check_invalid_password_sign_up(password, confirmPassword, isFormat);
        }
    }

    /**
     * 邮箱注册不同意服务条款
     */
    @Test
    public void do_not_accept_the_terms_sign_up_with_email(){
        this.runTimeData("email",_email);
        this.runTimeData("password",_password);

        ss.type_email_address(_email);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(false);
        ss.submit_email_sign_up();

        check_toast_msg(ss.theToast().accept_terms_of_service_toast);
    }

    /**
     * 邮箱注册使用无效的图像验证码
     *      无效场景: 空输入 , 错误的
     * @param imageCaptcha
     */
    @Test
    @YmlParameter
    public void error_image_captcha_sign_up_with_email(@Var("imageCaptcha") String imageCaptcha){
        String currentCaptcha = CURRENT_IMAGE_STRATEGY.getImageCode();
        this.runTimeData("usedImageCaptcha",imageCaptcha, "currentCaptcha",currentCaptcha);
        this.runTimeData("email",_email,"password",_password);

        ss.type_email_address(_email);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.submit_email_sign_up();

        ss.check_image_verify_code_show();
        ss.type_image_verify_code(imageCaptcha);
        ss.confirm_image_verify_code_input();

        check_toast_msg(ss.theToast().error_captcha_code_toast);
    }


    /**
     * 验证使用无效手机号注册
     *      无效场景 ; 空值 , 错误的格式 , 已存在
     * @param phone
     * @param isFormat
     */
    @Test
    @YmlParameter
    public void invalid_phone_sign_up(@Var("phone") UserName phone ,@Var("isFormat") boolean isFormat){
        this.runTimeData("password",_password);

        ss.select_way_for_sign_up(PHONE);
        ss.verify_phone_sign_up_default_show();

        if (condition != CN) ss.type_country_code(default_country_code);

        ss.type_phone_number(phone);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.do_get_sms_code();

        ss.check_invalid_phone_sign_up(phone ,isFormat);
    }

    /**
     * 使用无效的密码注册
     *      无效场景 : 空值 , 格式不正确 , 两次密码不一致
     * @param password
     * @param pwd_confirm
     * @param isFormat password 的格式正确否
     */
    @Test
    @YmlParameter
    public void invalid_password_sign_up_with_phone(@Var("password") String password ,@Var("pwd_confirm") String pwd_confirm ,
                                                    @Var("isFormat") boolean isFormat){
        ss.select_way_for_sign_up(PHONE);
        ss.verify_phone_sign_up_default_show();

        if (condition != CN) ss.type_country_code(default_country_code);

        ss.type_phone_number(_phone);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.do_get_sms_code();

        ss.check_invalid_password_sign_up(password ,pwd_confirm ,isFormat);
    }

    /**
     * 手机注册不同意服务条款
     */
    @Test
    public void do_not_accept_the_terms_sign_up_with_phone(){
        ss.select_way_for_sign_up(PHONE);
        ss.verify_phone_sign_up_default_show();

        if (condition != CN) ss.type_country_code(default_country_code);

        ss.type_phone_number(_phone);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(false);
        ss.do_get_sms_code();

        check_toast_msg(ss.theToast().accept_terms_of_service_toast);
    }

    /**
     * 手机注册使用无效图像验证码
     *      无效场景: 空输入 , 错误的
     * @param imageCaptcha
     */
    @Test
    @YmlParameter
    public void error_image_captcha_sign_up_with_phone(@Var("imageCaptcha") String imageCaptcha){
        ss.select_way_for_sign_up(PHONE);
        ss.verify_phone_sign_up_default_show();

        if (condition != CN) ss.type_country_code(default_country_code);

        ss.type_phone_number(_phone);
        ss.type_password(_password);
        ss.type_confirm_password(_password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.do_get_sms_code();

        ss.check_image_verify_code_show();
        ss.type_image_verify_code(imageCaptcha);
        ss.confirm_image_verify_code_input();

        check_toast_msg(ss.theToast().error_captcha_code_toast);
    }

}
