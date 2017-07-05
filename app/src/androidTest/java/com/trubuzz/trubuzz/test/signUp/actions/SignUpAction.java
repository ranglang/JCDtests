package com.trubuzz.trubuzz.test.signUp.actions;

import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.idlingResource.ElementExistIR;
import com.trubuzz.trubuzz.idlingResource.HasViewIdlingResource;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.idlingResource.ViewMatcherIdlingResource;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.test.signUp.views.SignUpView;
import com.trubuzz.trubuzz.utils.DoIt;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomViewAction.doCheck;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_auto_login_successful;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2017/7/3.
 */

public class SignUpAction implements SignUpService {
    SignUpView sv = new SignUpView();

    @Override
    public void into_sign_up_page() {
        given(sv.sign_up_link).perform(click());
    }

    @Override
    public void verify_email_sign_up_default_show() {
        given(sv.use_email_reg).check(matches(isSelected()));       //检查"邮箱注册"默认被选中
        given(sv.email_label).check(matches(isDisplayed()));
        given(sv.pwd_label).check(matches(isDisplayed()));
        given(sv.pwd_confirm_label).check(matches(isDisplayed()));
        given(sv.accept_service_check).check(matches(isNotChecked()));
        given(sv.service_terms).check(matches(isClickable()));
    }

    @Override
    public void verify_phone_sign_up_default_show() {
        given(sv.use_phone_reg).check(matches(isSelected()));
        given(sv.phone_label).check(matches(isDisplayed()));
        given(sv.pwd_label).check(matches(isDisplayed()));
        given(sv.pwd_confirm_label).check(matches(isDisplayed()));
        given(sv.sms_captcha_label).check(matches(isDisplayed()));
        given(sv.get_sms_button).check(matches(isDisplayed()));
        given(sv.accept_service_check).check(matches(isNotChecked()));
        given(sv.service_terms).check(matches(isClickable()));
        given(sv.phone_reg_submit.setDis(false)).check(matches(not(isDisplayed())));
    }

    @Override
    public void select_way_for_sign_up(Account account) {
        switch (account) {
            case MAIL:
                given(sv.use_email_reg).perform(click());
                break;
            case PHONE:
                given(sv.use_phone_reg).perform(click());
                break;
        }
    }

    @Override
    public void type_email_address(String email) {
        given(sv.email_input).perform(replaceText(email))
                .check(matches(withText(email)));
    }

    @Override
    public void type_password(String password) {
        given(sv.reg_pwd).perform(replaceText(password))
                .check(matches(isPassword()));
    }

    @Override
    public void type_confirm_password(String confirmPassword) {
        given(sv.reg_pwd_confirm).perform(replaceText(confirmPassword))
                .check(matches(isPassword()));
    }

    @Override
    public void agree_with_the_terms(boolean agree) {
        given(sv.accept_service_check).perform(doCheck(agree));
    }

    @Override
    public void submit_email_sign_up() {
        given(sv.email_reg_submit).perform(click());
    }

    @Override
    public void check_image_verify_code_show() {
        given(sv.image_captcha_frame).check(matches(isDisplayed()));
        given(sv.captcha_image).check(matches(isDisplayed()));
        given(sv.image_captcha_change).check(matches(isClickable()));
    }

    @Override
    public void type_image_verify_code(String imageCode) {
        if (imageCode == null) {
            imageCode = Conf.CURRENT_IMAGE_STRATEGY.getImageCode();
        }
        given(sv.image_captcha_input).perform(replaceText(imageCode));
    }

    @Override
    public void confirm_image_verify_code_input() {
        given(sv.image_captcha_ok_button).perform(click());
    }

    @Override
    public void check_sign_up_successful() {
        check_auto_login_successful();
    }

    @Override
    public void type_country_code(String country_code) {
        given(sv.country_code_input).perform(replaceText(country_code))
                .check(matches(withText(country_code)));
    }

    @Override
    public void type_phone_number(String phone) {
        given(sv.phone_input).perform(replaceText(phone))
                .check(matches(withText(phone)));
    }

    @Override
    public void do_get_sms_code() {
        given(sv.get_sms_button).perform(click());
    }

    @Override
    public String type_sms_code(String phone, String sms_code) {
        if (sms_code == null) {
            sms_code = "123456";
////---            sms_code = new AdminUtil().getCurrentSmsCode(phone);
        }
        DoIt.regIdlingResource(new ViewIdlingResource(getView(sv.sms_captcha_input)));
        given(sv.sms_captcha_input).perform(replaceText(sms_code));
        DoIt.unRegIdlingResource();
        return sms_code;
    }

    @Override
    public void submit_phone_sign_up() {
        given(sv.phone_reg_submit).perform(click());
    }
}
