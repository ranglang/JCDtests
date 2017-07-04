package com.trubuzz.trubuzz.test.signUp.tests;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
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

import static com.trubuzz.trubuzz.constant.enumerate.Account.PHONE;

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

    @Test
    @Parameters({"star000@gg.com,aA111222"})
    public void sign_up_with_email_flow(String email,String password){
        ss.verify_email_sign_up_default_show();

        ss.type_email_address(email);
        ss.type_email_password(password);
        ss.type_email_confirm_password(password);

        Espresso.closeSoftKeyboard();
        ss.agree_with_the_terms(true);
        ss.submit_email_sign_up();

        ss.check_image_verify_code_show();
        ss.type_image_verify_code(null);
        ss.confirm_image_verify_code_input();

        ss.check_sign_up_successful();
    }

//    @Test
    public void sign_up_with_phone_flow(){
        ss.select_way_for_sign_up(PHONE);
        ss.verify_phone_sign_up_default_show();
    }
}
