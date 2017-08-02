package com.trubuzz.trubuzz.test.settings.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.UserStore;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;
import com.trubuzz.trubuzz.shell.Password;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.settings.SettingsService;
import com.trubuzz.trubuzz.test.settings.actions.SettingsAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

import static com.trubuzz.trubuzz.test.Wish.doLogin;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 2017/7/20.
 */
@RunWith(JUnitParamsRunner.class)
public class SettingsLoginPwdTest extends BaseTest {
    private SettingsService ss = new SettingsAction();

    @YamlFileName
    private final static String ymlFileName = "settings.yml";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void logout(){
        Wish.wantNotLogin();
    }

    /**
     * 正向登录密码修改流程
     *      正向场景: 手机用户 , 邮箱用户 , 已开户用户 , 未开户用户
     * @param userName
     * @param password
     * @param newPassword
     */
    @Test
    @YmlParameter
    public void login_password_reset_flow(@Var("userName") String userName , @Var(OL_PIN) Password password ,
                                          @Var(NL_PIN) Password newPassword){
        // 取出实时密码
//        this.theCurrent(userName, password);
//        this.theRandom(newPassword);

        doLogin(userName ,password);
        ss.spread_left_drawer();
        ss.into_settings_page();
        ss.into_login_password_reset_page();
        ss.type_old_password(password);
        ss.type_new_password(newPassword);
        ss.type_new_confirm_password(newPassword);
        ss.click_submit_button();
        check_toast_msg(ss.theToast().change_login_password_success_toast);

        ss.check_new_password_login_successful(userName ,newPassword);

        // 更新实时密码
        UserStore.updateLoginPassword(userName ,newPassword);
    }

    /**
     * 更改登录密码反向用例 -- 无效旧密码
     *      无效输入场景 :    无效旧密码 , 错误格式的旧密码 , 空旧密码
     * @param userName
     * @param errorOldPassword
     * @param newPassword
     */
    public void invalid_old_password_reset(@Var("userName") String userName ,
                                           @Var("errorOldPassword") Password errorOldPassword ,
                                           @Var(NL_PIN) Password newPassword){
        // 取出实时密码
        String oldPassword = UserStore.getLoginPassword(userName);

        doLogin(userName ,oldPassword);
        ss.spread_left_drawer();
        ss.into_settings_page();
        ss.into_login_password_reset_page();
        ss.type_old_password(errorOldPassword);
        ss.type_new_password(newPassword);
        ss.type_new_confirm_password(newPassword);
        ss.click_submit_button();

        ss.check_invalid_old_password_msg(oldPassword);
        ss.check_invalid_reset_can_not_login(userName, newPassword);
    }

    /**
     * 使用无效的新密码更改
     *      无效场景 : 空值 , 格式不正确 , 两次密码不一致 , 与旧密码一致
     * @param userName
     * @param oldPassword
     * @param newPassword
     * @param isFormat
     */
    public void invalid_new_password_reset(@Var("userName") String userName , @Var(OL_PIN) Password oldPassword ,
                                           @Var(NL_PIN) Password newPassword ,@Var("newPasswordConfirm") Password newPasswordConfirm ,
                                           @Var("isFormat") boolean isFormat) {
        // 使用实时密码
        this.theCurrent(userName, oldPassword);

        doLogin(userName ,oldPassword);
        ss.spread_left_drawer();
        ss.into_settings_page();
        ss.into_login_password_reset_page();
        ss.type_old_password(oldPassword);
        ss.type_new_password(newPassword);
        ss.type_new_confirm_password(newPasswordConfirm);
        ss.click_submit_button();

        ss.check_invalid_new_password_reset(oldPassword, newPassword ,newPasswordConfirm, isFormat);
    }
}
