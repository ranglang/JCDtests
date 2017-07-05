package com.trubuzz.trubuzz.test.signUp.views;

import com.trubuzz.trubuzz.shell.beautify.ToastElement;

import static com.trubuzz.trubuzz.test.R.string.error_invalid_email;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_auth_sent;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2017/7/4.
 */

public class SignUpToast {

    public ToastElement invalid_email_toast = new ToastElement(getString("邮箱地址不存在或已被注册", error_invalid_email));
    public ToastElement sign_up_sms_auth_sent_toast = new ToastElement(getString("短信验证码已发送", sign_up_sms_auth_sent));
}
