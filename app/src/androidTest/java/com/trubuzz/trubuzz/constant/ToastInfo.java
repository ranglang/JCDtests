package com.trubuzz.trubuzz.constant;

import com.trubuzz.trubuzz.shell.Element;

import static com.trubuzz.trubuzz.test.R.string.captcha_format_error;
import static com.trubuzz.trubuzz.test.R.string.error_captcha_code;
import static com.trubuzz.trubuzz.test.R.string.incorrect_email_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_confirm;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_phone_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_sms_format;
import static com.trubuzz.trubuzz.test.R.string.reset_password_mail_sent;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_auth_sent;
import static com.trubuzz.trubuzz.test.R.string.user_not_exist;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/4.
 */

public interface ToastInfo {
    Element incorrect_password_confirm_toast =
            new Element().setToastMsg(getString("确认密码输入不一致" ,incorrect_password_confirm));
    Element email_success_except =
            new Element().setToastMsg(getString("重设密码信件已发送到您的邮箱，请查收并重置密码。" , reset_password_mail_sent));
    Element incorrect_email_format_toast =
            new Element().setToastMsg(getString("邮箱地址格式不正确" ,incorrect_email_format)) ;
    Element sms_sent_toast =
            new Element().setToastMsg(getString("短信验证码已发送" ,sign_up_sms_auth_sent));
    Element incorrect_phone_format_toast =
            new Element().setToastMsg(getString("手机号格式不正确" ,incorrect_phone_format));
    Element user_not_exist_toast =
            new Element().setToastMsg(getString("该用户不存在" ,user_not_exist));
    Element incorrect_sms_format_toast =
            new Element().setToastMsg(getString("短信验证码格式不正确" ,incorrect_sms_format));
    Element incorrect_password_format_toast =
            new Element().setToastMsg(getString("请输入6–16字符的大小写字母和数字组合" ,incorrect_password_format));
    Element error_captcha_code_toast =
            new Element().setToastMsg(getString("验证码错误" ,error_captcha_code));
    Element captcha_format_error_toast =
            new Element().setToastMsg(getString("验证码为六个数字" , captcha_format_error));


}
