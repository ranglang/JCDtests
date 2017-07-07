package com.trubuzz.trubuzz.test.signUp.tests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.feature.custom.parameters.GatherParameter;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.test.signUp.actions.SignUpAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import junitparams.Parameters;

import static com.trubuzz.trubuzz.constant.Conf.p_s;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 2017/7/6.
 */

public class SignUpReverseTest extends BaseTest {
    private SignUpService ss = new SignUpAction();
    private final String key_pwd = "password";
    private final String key_pwd_confirm = "password_confirm";
    private final String key_format = "isFormat";
    private final String _phone = "11811110001";
    private final String _email = "aabbcc@123.com";
    private final String _password = "qQ123456";
    private final String invalid_password_padding = p_s + key_pwd + "," + key_pwd_confirm + "," + key_format;

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
    @Parameters({
            "star009@abc.com",      // 已注册
            "star009abc.com",      // 格式不正确
            "abc001@123.com",      // 邮箱地址不存在
            "",                     // 空输入
    })
    public void invalid_email_address_sign_up(@Var("email") String email) {
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
     * 使用无效的密码注册
     *      无效场景: 格式不正确 , 两次输入不一致 , 空输入
     * @param invalidPasswords
     */
    @Test
    @GatherParameter({
            "{%s:'qQ123456',%s:'Ww123456',%s:true}" + invalid_password_padding,  // 两次密码不一致
            "{%s:'qq123456',%s:'qq123456',%s:false}" + invalid_password_padding,  // 格式错误
            "{%s:'',%s:'',%s:false}" + invalid_password_padding // 空值
    })
    public void invalid_password_sign_up_with_email_sign_up(@Var("invalidPasswords") ArrayList<HashMap> invalidPasswords) {
        this.runTimeData("email",_email);

        ss.type_email_address(_email);
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
    public void do_not_accept_the_terms_with_email_sign_up(){
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
     * 邮箱注册使用错误的图像验证码
     */
    @Test
    public void error_image_captcha_with_email_sign_up(){
        String imageCaptcha = "000000";
        String currentCaptcha = Conf.CURRENT_IMAGE_STRATEGY.getImageCode();
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
}
