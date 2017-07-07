package com.trubuzz.trubuzz.test.login;

import android.support.test.espresso.Espresso;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.common.GlobalView;
import com.trubuzz.trubuzz.utils.AdminUtil;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Judge;

import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.trubuzz.trubuzz.constant.Env.TAG;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.Env.instrumentation;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_auto_login_successful;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;
import static com.trubuzz.trubuzz.test.common.GlobalView.assets_radio;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;
import static com.trubuzz.trubuzz.utils.God.getString;
import static com.trubuzz.trubuzz.utils.Judge.isExist;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 17/5/25.
 */

public class LoginAction implements LoginService{
    private LoginView lv = new LoginView();
    private AdminUtil au = new AdminUtil();
    private LoginView.Toast lt = new LoginView.Toast();

    @Override
    public void browse_tutorial(){
        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_1_title_text)));
        given(lv.tutorial_1_content).check(matches(withText(lv.tutorial_1_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_2_title_text)));
        given(lv.tutorial_1_content).check(matches(withText(lv.tutorial_2_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(lv.tutorial_1_title).check(matches(withText(lv.tutorial_3_title_text)));
        given(lv.tutorial_1_content).check(matches(withText(lv.tutorial_3_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(lv.tutorial_1_title).check(matches(withText(String.format(lv.tutorial_4_title_text,
                God.getAppName(God.getCurrentActivity(instrumentation))))));
        given(lv.tutorial_start_button).check(matches(isDisplayed()))
                .perform(click());
    }

    @Override
    public void type_username(String username) {
        given(lv.username_input).perform(clearText())
                .perform(replaceText(username))
                .check(matches(withText(username)));
    }

    @Override
    public void type_password(String pwd) {
        given(lv.login_pwd_input).perform(clearText())
                .perform(replaceText(pwd))
                .check(matches(isPassword()));
    }

    @Override
    public void click_login_button() {
        closeSoftKeyboard();
        given(lv.login_button).perform(click());
    }

    @Override
    public void check_broker() {
        given(assets_radio).perform(click());
        given(AAsset.net_worth_view).check( matches((isDisplayed())));
    }

    @Override
    public void check_not_broker() {
        DoIt.regIdlingResource(new ActivityIdlingResource(AName.MAIN, instrumentation.getContext(), true));
        // 首次登录会出现交谈对话框 , 这时将其关闭
        if (isExist(lv.intercom_layout)) {
            given(lv.intercom_close).perform(click());
        }
        webGiven()
                .withElement(lv.ib_broker_title)
                .check(webMatches(getText(), equalTo(lv.ib_broker_title_text)));
        unRegIdlingResource();
    }

    @Override
    public void clean_password() {
        given(lv.clean_pwd_image).perform(click());
    }

    @Override
    public void check_password_input_default_show() {
        given(lv.login_pwd_input).check(matches(withHint(lv.login_pwd_input_hint)));
    }

    @Override
    public void resend_mail(boolean sendEmail) {
        if (sendEmail) {
            given(lv.confirm_send).perform(click());
        }else{
            given(lv.cancel_send).perform(click());
        }
    }

    @Override
    public void check_resend_mail_alert() {
        given(lv.not_verify_7days_layout).check(matches(isDisplayed()));
        given(lv.not_verify_7days_title).check(matches(withText(lv.not_verify_7days_title_text)));
        given(lv.not_verify_7days_content).check(matches(withText(lv.not_verify_7days_content_text)));
    }

    @Override
    public void type_mail_address(String mail) {
        given(lv.mail_address_input).perform(clearText())
                .check(matches(withHint(lv.mail_input_hint_text)))
                .perform(replaceText(mail));
    }

    @Override
    public void submit_mail_address() {
        Espresso.closeSoftKeyboard();
        given(lv.mail_submit_button).perform(click());
    }

    @Override
    public void into_forget_password_page() {
        given(lv.forget_pwd_button).perform(click());
    }

    @Override
    public void check_account_follow(Account accountType, String account) {
        switch (accountType) {
            case MAIL:
                given(lv.mail_retrieve_tab).check(matches(isSelected()));
                given(lv.mail_address_input).check(matches(withText(account)));
                break;
            case PHONE:
                given(lv.phone_retrieve_tab).check(matches(isSelected()));
                given(lv.phone_number_input).check(matches(withText(account)));
                break;
        }
    }

    @Override
    public void type_phone_number(String phone) {
        given(lv.phone_number_input).perform(clearText())
                .check(matches(withHint(lv.phone_input_hint_text)))
                .perform(replaceText(phone));
    }

    @Override
    public void get_sms_code() {
        // 获取验证码之前没有提交按钮
        given(lv.phone_submit_button.setDis(false)).check(matches(not(isDisplayed())));
        given(lv.get_sms_button).perform(click());
    }

    @Override
    public String type_sms_code(String phone ,String smsCode) {
        if (smsCode == null) {
            smsCode = au.getCurrentSmsCode(phone);
            Log.i(TAG, String.format("type_sms_code: current sms code = %s",smsCode ));
        }
        given(lv.sms_code_input).perform(replaceText(smsCode));
        return smsCode;
    }

    @Override
    public void type_new_password(String password) {
        given(lv.new_password_input).perform(replaceText(password));
    }

    @Override
    public void type_confirm_password(String confirmPwd) {
        given(lv.password_confirm).perform(replaceText(confirmPwd));
    }

    @Override
    public void submit_phone_retrieve() {
        Espresso.closeSoftKeyboard();
        given(lv.phone_submit_button).perform(click());
    }

    @Override
    public void check_retrieve_use_phone_successful() {
        check_auto_login_successful();
    }

    @Override
    public void select_mail_retrieve() {
        given(lv.mail_retrieve_tab).perform(click())
                .check(matches(isSelected()));
    }

    @Override
    public void select_phone_retrieve() {
        given(lv.phone_retrieve_tab).perform(click())
                .check(matches(isSelected()));
    }

    @Override
    public void check_invalid_mail_retrieve(String mail) {
        if (Judge.isMatched(mail, Env.emailRegex)) {
            CommonAction.check_toast_msg(lt.incorrect_email_format_toast);
        } else {
            CommonAction.check_toast_msg(lt.user_not_exist_toast);
        }
    }

    @Override
    public void check_get_sms_code_successful() {
        CommonAction.check_toast_msg(lt.sms_code_sent_toast);
        given(lv.sms_code_input).check(matches(isDisplayed()));
        given(lv.phone_submit_button).check(matches(isDisplayed()));
    }

    @Override
    public void check_invalid_login(boolean usernameIsFormatted, String password) {
        if (!usernameIsFormatted) {
            check_toast_msg(lt.account_format_toast);
        } else if ("".equals(password)) {
            check_toast_msg(lt.password_empty_toast);
        } else {
            check_toast_msg(lt.login_failed_toast);
        }
    }

}
