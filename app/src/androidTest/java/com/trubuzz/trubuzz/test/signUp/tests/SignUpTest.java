package com.trubuzz.trubuzz.test.signUp.tests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.signUp.actions.SignUpAction;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

import static com.trubuzz.trubuzz.constant.Env.condition;
import static com.trubuzz.trubuzz.constant.enumerate.Account.PHONE;
import static com.trubuzz.trubuzz.constant.enumerate.Condition.*;

/**
 * Created by king on 2017/7/3.
 */
@RunWith(JUnitParamsRunner.class)
public class SignUpTest extends BaseTest {
    private SignUpService ss = new SignUpAction();

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
     * 邮箱注册流程
     * @param email
     * @param password
     */
    @Test
    @YmlParameter
    public void sign_up_with_email_flow(@Var("email") String email, @Var("password") String password){
        ss.verify_email_sign_up_default_show();

        ss.type_email_address(email);
        ss.type_password(password);
        ss.type_confirm_password(password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.submit_email_sign_up();

        ss.check_image_verify_code_show();
        ss.type_image_verify_code(null);
        ss.confirm_image_verify_code_input();

        ss.check_sign_up_successful();
    }

    /**
     * 手机注册流程
     * @param country_code
     * @param phone
     * @param password
     */
    @Test
    @YmlParameter
    public void sign_up_with_phone_flow(@Var("country_code") String country_code ,@Var("phone") String phone ,
                                        @Var("password") String password){
        ss.select_way_for_sign_up(PHONE);
        ss.verify_phone_sign_up_default_show();

        if (condition != CN) ss.type_country_code(country_code);

        ss.type_phone_number(phone);
        ss.type_password(password);
        ss.type_confirm_password(password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.do_get_sms_code();

        ss.check_image_verify_code_show();
        ss.type_image_verify_code(null);
        ss.confirm_image_verify_code_input();

        CommonAction.check_toast_msg(ss.theToast().sign_up_sms_auth_sent_toast);
        String sms_code = ss.type_sms_code(phone ,null);
        this.runTimeData("sms_code",sms_code);
        Espresso.closeSoftKeyboard();

        ss.submit_phone_sign_up();
        ss.check_sign_up_successful();
    }

    // 国别码挑选测试


}
