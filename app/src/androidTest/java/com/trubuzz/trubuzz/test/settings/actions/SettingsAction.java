package com.trubuzz.trubuzz.test.settings.actions;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.common.GlobalView;
import com.trubuzz.trubuzz.test.login.LoginAction;
import com.trubuzz.trubuzz.test.settings.SettingsService;
import com.trubuzz.trubuzz.test.settings.views.SettingsToast;
import com.trubuzz.trubuzz.test.settings.views.SettingsView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.Wish.doLogin;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_auto_login_successful;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_current_activity;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;
import static com.trubuzz.trubuzz.utils.Judge.hasToast;

/**
 * Created by king on 2017/7/21.
 */

public class SettingsAction implements SettingsService {
    private SettingsView sv = new SettingsView();
    private SettingsToast st = new SettingsToast();
    private LoginAction la = new LoginAction();

    @Override
    public SettingsToast theToast() {
        return st;
    }

    @Override
    public void spread_left_drawer() {
        given(GlobalView.assets_radio).perform(click());
        given(sv.left_drawer_ioc).perform(click());
        if (hasToast(st.app_name_toast)) {
            given(sv.left_drawer_ioc).perform(click());
        }
        given(sv.drawer_layout).check(matches(isDisplayed()));
    }

    @Override
    public void into_settings_page() {
        given(sv.setting).perform(click());
        check_current_activity(AName.SETTINGS);
    }

    @Override
    public void into_login_password_reset_page() {
        given(sv.change_login_pwd).perform(click());
    }

    @Override
    public void type_old_password(String password) {
        given(sv.old_password_input).perform(replaceText(password))
                .check(matches(isPassword()));
    }

    @Override
    public void type_new_password(String newPassword) {
        given(sv.new_password_input).perform(replaceText(newPassword))
                .check(matches(isPassword()));
    }

    @Override
    public void type_new_confirm_password(String newPasswordConfirm) {
        given(sv.confirm_new_password).perform(replaceText(newPasswordConfirm))
                .check(matches(isPassword()));
    }

    @Override
    public void click_submit_button() {
        given(sv.submit_button).perform(click());
    }

    @Override
    public void click_logout_button() {
        given(sv.logout_button).perform(click());
    }

    @Override
    public void check_new_password_login_successful(String userName, String newPassword) {
        this.click_logout_button();
        doLogin(userName, newPassword);
        check_auto_login_successful();
    }

    @Override
    public void check_invalid_old_password_msg(String oldPassword) {
        if ("".equals(oldPassword)) {
            given(st.input_old_password_toast).check(matches(isDisplayed()));
        } else {
            given(st.user_old_pwd_incorrect_toast).check(matches(isDisplayed()));
        }
    }

    @Override
    public void check_invalid_reset_can_not_login(String userName, String newPassword) {
        given(GlobalView.back_up).perform(click());
        this.click_logout_button();
        doLogin(userName, newPassword);
        la.check_invalid_login(true, newPassword);
        la.check_still_in_login_page();
    }

    @Override
    public void check_invalid_new_password_reset(String oldPassword, String newPassword, String newPasswordConfirm, boolean isFormat) {
        if (!isFormat) {
            check_toast_msg(st.incorrect_password_format_toast);
            return;
        }
        if (oldPassword.equals(newPassword)) {
            check_toast_msg(st.incorrect_password_same_toast);
            return;
        }
        if (!newPassword.equals(newPasswordConfirm)) {
            check_toast_msg(st.incorrect_password_confirm_toast);
        }
    }

    @Override
    public void check_trade_password_display(boolean hasBroker) {
        if (hasBroker) {
            given(sv.change_trade_pwd_view).check(matches(isDisplayed()));
        } else {
            given(sv.change_trade_pwd_view).check(doesNotExist());
        }
    }
}
