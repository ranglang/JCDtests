package com.trubuzz.trubuzz.test.settings.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.UserStore;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.shell.Password;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.settings.SettingsService;
import com.trubuzz.trubuzz.test.settings.actions.SettingsAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.util.List;

import junitparams.JUnitParamsRunner;

import static com.trubuzz.trubuzz.test.Wish.doLogin;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 2017/7/26.
 */
@RunWith(JUnitParamsRunner.class)
public class SettingsTradePwdTest extends BaseTest {
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
     * 验证交易密码修改条目的展示
     *      未开户用户将不展示该栏目
     * @param userName
     * @param password
     * @param hasBroker
     */
    public void verify_trade_password_display(String userName , Password password , boolean hasBroker){

        doLogin(userName, password);
        ss.spread_left_drawer();
        ss.into_settings_page();

        ss.check_trade_password_display(hasBroker);
    }

    /**
     * 正向交易密码修改流程
     * @param userName
     * @param loginPassword
     * @param oldTradePwd
     * @param newTradePwd
     */
    public void trade_password_update_flow(@Var("userName") String userName ,
                                           @Var("loginPassword") Password loginPassword ,
                                           @Var("oldTradePwd") Password oldTradePwd ,
                                           @Var("newTradePwd") Password newTradePwd){

        doLogin(userName, loginPassword);
        ss.spread_left_drawer();
        ss.into_settings_page();
        ss.into_trade_password_reset_page();

        ss.type_old_password(oldTradePwd);
        ss.type_new_password(newTradePwd);
        ss.type_new_confirm_password(newTradePwd);
        ss.click_submit_button();

        check_toast_msg(ss.theToast().change_trade_password_success_toast);
        ss.check_new_password_trade_successful(newTradePwd);

        // 更新交易密码
        UserStore.updateTradePassword(userName ,newTradePwd);
    }

}
