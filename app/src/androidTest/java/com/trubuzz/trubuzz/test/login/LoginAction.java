package com.trubuzz.trubuzz.test.login;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.utils.DoHttp;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.HtmlParser;

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
import static com.trubuzz.trubuzz.elements.Global.assets_radio;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;
import static com.trubuzz.trubuzz.utils.God.getString;
import static com.trubuzz.trubuzz.utils.Judge.isExist;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by king on 17/5/25.
 */

public class LoginAction implements LoginService{
    private LoginView lv = new LoginView();

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
        given(lv.get_sms_button).perform(click());
    }

    @Override
    public void type_sms_code(String smsCode) {
        if (smsCode == null) {
            ////--
//            DoHttp doHttp = new DoHttp();
//            doHttp.doGet(Conf.ad_sms_log_url);
            HtmlParser hp = new HtmlParser();
//            hp.doGet(Conf.ad_sms_log_url);
        }
        given(lv.sms_code_input).perform(replaceText(smsCode));
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
        given(lv.phone_submit_button).perform(click());
    }

    @Override
    public void check_retrieve_use_phone_successful() {
        DoIt.regIdlingResource(new ActivityIdlingResource(AName.MAIN, instrumentation.getContext(), true));
        CommonAction.check_current_activity(AName.MAIN);
        unRegIdlingResource();
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
}
