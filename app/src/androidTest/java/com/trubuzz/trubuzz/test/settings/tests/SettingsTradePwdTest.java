package com.trubuzz.trubuzz.test.settings.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.feature.custom.parameters.YamlFileName;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.settings.SettingsService;
import com.trubuzz.trubuzz.test.settings.actions.SettingsAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

import static com.trubuzz.trubuzz.test.Wish.doLogin;

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
    public void verify_trade_password_display(String userName , String password ,boolean hasBroker){
        // 取出实时密码
        password = this.theCurrent(userName, password);
        doLogin(userName, password);
        ss.spread_left_drawer();
        ss.into_settings_page();

        ss.check_trade_password_display(hasBroker);
    }
    // 未开户用户 没有修改交易密码

    //

}
