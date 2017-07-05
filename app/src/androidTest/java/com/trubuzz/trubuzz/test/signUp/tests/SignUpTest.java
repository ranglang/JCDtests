package com.trubuzz.trubuzz.test.signUp.tests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.constant.enumerate.Condition;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.signUp.actions.SignUpAction;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.test.signUp.views.SignUpToast;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.trubuzz.trubuzz.constant.Conf.condition;
import static com.trubuzz.trubuzz.constant.enumerate.Account.PHONE;
import static com.trubuzz.trubuzz.constant.enumerate.Condition.*;

/**
 * Created by king on 2017/7/3.
 */
@RunWith(JUnitParamsRunner.class)
public class SignUpTest extends BaseTest {
    private SignUpService ss = new SignUpAction();
    private SignUpToast st = new SignUpToast();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void into_sign_up(){
        Wish.wantNotLogin();
        ss.into_sign_up_page();
    }

//    @Test
    @Parameters({"star000@gg.com,aA111222"})
    public void sign_up_with_email_flow(String email,String password){
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

    @Test
    @Parameters({
            "86,12122226666,aA11112222",
    })
    public void sign_up_with_phone_flow(String country_code ,String phone ,String password){
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

        CommonAction.check_toast_msg(st.sign_up_sms_auth_sent_toast);
        String sms_code = ss.type_sms_code(phone ,null);
        this.runTimeData("sms_code",sms_code);
        Espresso.closeSoftKeyboard();

        ss.submit_phone_sign_up();
        ss.check_sign_up_successful();
    }

    // 国别码挑选测试
}
