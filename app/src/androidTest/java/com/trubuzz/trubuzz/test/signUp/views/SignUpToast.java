package com.trubuzz.trubuzz.test.signUp.views;

import com.trubuzz.trubuzz.shell.beautify.ToastElement;

import static com.trubuzz.trubuzz.test.R.string.accept_terms_of_service_hint;
import static com.trubuzz.trubuzz.test.R.string.error_captcha_code;
import static com.trubuzz.trubuzz.test.R.string.error_invalid_email;
import static com.trubuzz.trubuzz.test.R.string.error_phone_duplicated;
import static com.trubuzz.trubuzz.test.R.string.incorrect_email_empty;
import static com.trubuzz.trubuzz.test.R.string.incorrect_email_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_confirm;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_phone_format;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_auth_sent;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2017/7/4.
 */

public class SignUpToast {

    public ToastElement invalid_email_toast = new ToastElement(
            getString("邮箱地址不存在或已被注册", error_invalid_email));

    public ToastElement sign_up_sms_auth_sent_toast = new ToastElement(
            getString("短信验证码已发送", sign_up_sms_auth_sent));

    public ToastElement incorrect_email_format_toast = new ToastElement(
            getString("邮箱地址格式不正确", incorrect_email_format));

    public ToastElement email_empty_toast = new ToastElement(
            getString("请输入邮箱地址", incorrect_email_empty));

    public final ToastElement incorrect_password_confirm_toast = new ToastElement(
            getString("确认密码输入不一致", incorrect_password_confirm));

    public final ToastElement incorrect_password_format_toast = new ToastElement(
            getString("请输入6–16字符的大小写字母和数字组合", incorrect_password_format));

    public final ToastElement accept_terms_of_service_toast = new ToastElement(
            getString("请阅读并勾选同意服务条款以注册账号", accept_terms_of_service_hint));

    public final ToastElement error_captcha_code_toast = new ToastElement(
            getString("验证码错误", error_captcha_code));

    public final ToastElement sign_up_phone_hint_toast = new ToastElement(
            getString("请输入手机号", sign_up_phone_hint));

    public final ToastElement incorrect_phone_format_toast = new ToastElement(
            getString("手机号格式不正确", incorrect_phone_format));

    public final ToastElement error_phone_duplicated_toast = new ToastElement(
            getString("电话号码已注册", error_phone_duplicated));


}
