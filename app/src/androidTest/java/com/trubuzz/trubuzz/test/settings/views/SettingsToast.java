package com.trubuzz.trubuzz.test.settings.views;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;
import com.trubuzz.trubuzz.utils.God;

import static com.trubuzz.trubuzz.constant.Env.instrumentation;
import static com.trubuzz.trubuzz.test.R.string.change_login_password_success;
import static com.trubuzz.trubuzz.test.R.string.change_trade_password_success;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_confirm;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_same;
import static com.trubuzz.trubuzz.test.R.string.user_oldpass_incorrect;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2017/7/21.
 */

public class SettingsToast {

    // 工具在点击左侧抽屉菜单时会出现长按的现象(这是将会展示出app name 的toast )
    public final ToastElement app_name_toast = new ToastElement(God.getAppName(God.getCurrentActivity(instrumentation)));

    public final ToastElement change_login_password_success_toast = new ToastElement(getString("修改登入密码成功" ,change_login_password_success));
    public final ToastElement change_trade_password_success_toast = new ToastElement(getString("修改交易密码成功" ,change_trade_password_success));


    // 在旧密码为空的时候提交当出现 ( 目前没有该toast的国际化 )
    public final ToastElement input_old_password_toast = new ToastElement(getString("请输入旧密码" ));
    public final ToastElement user_old_pwd_incorrect_toast = new ToastElement(getString("原始密码错误" ,user_oldpass_incorrect));
    public final ToastElement incorrect_password_confirm_toast = new ToastElement(getString("确认密码输入不一致" ,incorrect_password_confirm));
    public final ToastElement incorrect_password_same_toast = new ToastElement(getString("新旧密码不可相同" ,incorrect_password_same));

    public final ToastElement incorrect_password_format_toast = new ToastElement(
            getString("请输入6–16字符的大小写字母和数字组合", incorrect_password_format));
}
