package com.trubuzz.trubuzz.test.signUp.actions;


import android.util.Log;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.shell.UserName;
import com.trubuzz.trubuzz.test.signUp.SignUpService;
import com.trubuzz.trubuzz.test.signUp.tests.SignUpReverseTest;
import com.trubuzz.trubuzz.test.signUp.views.SignUpToast;
import com.trubuzz.trubuzz.test.signUp.views.SignUpView;
import com.trubuzz.trubuzz.utils.AdminUtil;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Judge;


import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.AName.WEB_VIEW;
import static com.trubuzz.trubuzz.constant.Config.CURRENT_IMAGE_STRATEGY;
import static com.trubuzz.trubuzz.constant.Env.instrumentation;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomViewAction.doCheck;
import static com.trubuzz.trubuzz.feature.custom.assertors.CustomWebAssert.customWebMatches;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static com.trubuzz.trubuzz.test.R.string.terms_content;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_auto_login_successful;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2017/7/3.
 */

public class SignUpAction implements SignUpService {
    private SignUpView sv = new SignUpView();
    private SignUpToast st = new SignUpToast();

    @Override
    public SignUpToast theToast() {
        return st;
    }

    @Override
    public void into_sign_up_page() {
        given(sv.sign_up_link).perform(click());
        Log.i(Env.TAG, "into_sign_up_page . ");
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
    public void type_email_address(UserName email) {
        given(sv.email_input).perform(replaceText(email.getUserName()))
                .check(matches(withText(email.getUserName())));
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
    public String type_image_verify_code(String imageCode) {
        if (imageCode == null) {
            imageCode = CURRENT_IMAGE_STRATEGY.getImageCode();
        }
        given(sv.image_captcha_input).perform(replaceText(imageCode));
        return imageCode;
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
    public void type_phone_number(UserName phone) {
        given(sv.phone_input).perform(replaceText(phone.getUserName()))
                .check(matches(withText(phone.getUserName())));
    }

    @Override
    public void do_get_sms_code() {
        given(sv.get_sms_button).perform(click());
    }

    @Override
    public String type_sms_code(String phone, String sms_code) {
        if (sms_code == null) {
            sms_code = "123456";
            sms_code = new AdminUtil().getCurrentSmsCode(phone);
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

    @Override
    public void check_invalid_email_sign_up(UserName email, SignUpReverseTest signUpReverseTest) {
        String _email = email.getUserName();
        if (Judge.isMatched(_email, Env.emailRegex)) {
            check_image_verify_code_show();
            String imageVerifyCode = type_image_verify_code(null);
            // 讲获取的图像验证码put
            signUpReverseTest.runTimeData("imageCaptcha" ,imageVerifyCode);
            confirm_image_verify_code_input();
            check_toast_msg(st.invalid_email_toast);
            return;
        }
        if("".equals(_email.trim())){
            check_toast_msg(st.email_empty_toast);
            return;
        }
        check_toast_msg(st.incorrect_email_format_toast);
    }

    @Override
    public void check_invalid_password_sign_up(String password, String confirmPassword, boolean isFormat) {
        if (!isFormat) {
            check_toast_msg(st.incorrect_password_format_toast);
            return;
        }
        if (!password.equals(confirmPassword)) {
            check_toast_msg(st.incorrect_password_confirm_toast);
        } else {
            Log.e(Env.TAG, String.format("check_invalid_password_sign_up: " +
                    "数据设计错误 ,反向用例设计了正向的数据  : \n " +
                    "password : %s ; password confirm : %s", password, confirmPassword));
        }
    }

    @Override
    public void check_invalid_phone_sign_up(UserName phone, boolean isFormat) {
        if ("".equals(phone.getUserName())) {
            check_toast_msg(st.sign_up_phone_hint_toast);
            return;
        }
        if (!isFormat) {
            check_toast_msg(st.incorrect_phone_format_toast);
            return;
        }
        // 反向用例 >> 格式正确则只验证 用户已存在的情况 ( 用户不存在 == 新用户 ,当在正向用例中验证 )
        this.type_image_verify_code(null);
        this.confirm_image_verify_code_input();
        check_toast_msg(st.error_phone_duplicated_toast);
    }

    @Override
    public void into_terms_page() {
        given(sv.service_terms).perform(click());
    }

    @Override
    public void check_terms_content() {
        DoIt.regIdlingResource(new ActivityIdlingResource(WEB_VIEW,instrumentation.getContext() ,true));
        webGiven()
                .withElement(sv.terms_content)
                .check(customWebMatches(getText() , containsString(getString(terms_content))));
        DoIt.unRegIdlingResource();
    }

}
