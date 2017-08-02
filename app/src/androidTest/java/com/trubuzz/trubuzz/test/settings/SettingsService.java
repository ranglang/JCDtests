package com.trubuzz.trubuzz.test.settings;

import com.trubuzz.trubuzz.shell.Password;
import com.trubuzz.trubuzz.test.common.TestsService;
import com.trubuzz.trubuzz.test.settings.views.SettingsToast;

/**
 * Created by king on 2017/7/21.
 */

public interface SettingsService extends TestsService<SettingsToast>{

    /**
     * 展开左侧抽屉
     */
    void spread_left_drawer();

    /**
     * 进入设置页面
     */
    void into_settings_page();

    /**
     * 进入登录密码修改页面
     */
    void into_login_password_reset_page();

    /**
     * 输入旧密码
     * @param password
     */
    void type_old_password(Password password);

    /**
     * 输入新密码
     * @param newPassword
     */
    void type_new_password(Password newPassword);

    /**
     * 再次输入新密码 ( 确认密码 )
     * @param newPasswordConfirm
     */
    void type_new_confirm_password(Password newPasswordConfirm);

    /**
     * 点击确定(提交)按钮
     */
    void click_submit_button();

    /**
     * 点击退出按钮
     */
    void click_logout_button();

    /**
     * 检查修改密码是否可成功登录
     * @param userName
     * @param newPassword
     */
    void check_new_password_login_successful(String userName, Password newPassword);

    /**
     * 检查无效旧密码的错误提示
     * @param oldPassword
     */
    void check_invalid_old_password_msg(String oldPassword);

    /**
     * 更改登录密码未成功 , 检查不可登录
     * @param userName
     * @param newPassword
     */
    void check_invalid_reset_can_not_login(String userName, Password newPassword);

    /**
     * 验证使用无效的新密码更改密码
     * @param oldPassword
     * @param newPassword
     * @param newPasswordConfirm
     * @param isFormat
     */
    void check_invalid_new_password_reset(Password oldPassword, Password newPassword, Password newPasswordConfirm, boolean isFormat);

    /**
     * 检查交易密码修改栏目是否出现
     *      已开户则出现, 未开户则不出现
     * @param hasBroker
     */
    void check_trade_password_display(boolean hasBroker);

    /**
     * 进入交易密码修改页面
     */
    void into_trade_password_reset_page();

    /**
     * 检查使用新的交易密码可成功交易
     * @param newTradePwd
     */
    void check_new_password_trade_successful(Password newTradePwd);
}
