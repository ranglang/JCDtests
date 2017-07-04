package com.trubuzz.trubuzz.test.signUp.actions;

import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.test.signUp.views.SignUpView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomViewAction.doCheck;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.shell.Park.given;

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
        given(sv.email_accept_service_check).check(matches(isNotChecked()));
        given(sv.email_terms).check(matches(hasLinks()));
    }

    @Override
    public void verify_phone_sign_up_default_show() {

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
    public void type_email_password(String password) {
        given(sv.email_reg_pwd).perform(replaceText(password))
                .check(matches(isPassword()));
    }

    @Override
    public void type_email_confirm_password(String confirmPassword) {
        given(sv.email_reg_pwd_confirm).perform(replaceText(confirmPassword))
                .check(matches(isPassword()));
    }

    @Override
    public void agree_with_the_terms(boolean agree) {
        given(sv.email_accept_service_check).perform(doCheck(agree));
    }

    @Override
    public void submit_email_sign_up() {
        given(sv.email_reg_submit).perform(click());
    }

    @Override
    public void check_image_verify_code_show() {

    }

    @Override
    public void type_image_verify_code(String imageCode) {

    }

    @Override
    public void confirm_image_verify_code_input() {

    }

    @Override
    public void check_sign_up_successful() {

    }
}
