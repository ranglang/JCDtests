package com.trubuzz.trubuzz.elements;

import android.view.View;

import com.trubuzz.trubuzz.shell.Element;

import static com.trubuzz.trubuzz.test.R.string.email;
import static com.trubuzz.trubuzz.test.R.string.forget_password_email;
import static com.trubuzz.trubuzz.test.R.string.forget_password_phone;
import static com.trubuzz.trubuzz.test.R.string.get_sms;
import static com.trubuzz.trubuzz.test.R.string.sign_up_confirm_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_email_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_password_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_hint;
import static com.trubuzz.trubuzz.test.R.string.submit;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/1.
 */

public class AForgetPwd {
    private static final Element<View> email_found = new Element<View>().setId("text1")
            .setText(getString("邮箱找回",forget_password_email));

    private static final Element<View> phone_found = new Element<View>().setId("text1")
            .setText(getString("手机找回",forget_password_phone));

    private static final Element<View> email_image = new Element<View>().setId("icon");
    private static final Element<View> phone_image = new Element<View>().setId("icon");

    private static final Element<View> email_text = new Element<View>().setText(getString("邮箱地址",email));


    public static final Element<View> sms_input = new Element<View>().setId("sms")
            .setHint(getString("请输入短信验证码",sign_up_sms_hint));

    public static final Element<View> password_input = new Element<View>().setId("password")
            .setHint(getString("请输入密码" ,sign_up_password_hint));

    public static final Element<View>password_confirm = new Element<View>().setId("confirm")
            .setHint(getString("请再次输入密码" ,sign_up_confirm_hint));

    public static final Element<View> get_mss_button = new Element<View>().setId("btn_sms")
            .setText(getString("获取验证码" ,get_sms));

    public static final Element<View> phone_input = new Element<View>().setId("phone")
            .setHint(getString("请输入手机号",sign_up_phone_hint));

    public static final Element<View> email_input = new Element<View>().setId("email")
            .setHint(getString("请输入您的邮箱地址",sign_up_email_hint))
            .setSibling(email_text);

    public static final Element<View> submit_button = new Element<View>().setId("submit")
            .setText(getString("提交",submit));

    public static final Element<View> use_email = new Element<View>()
            .setChildren(new Element<View>().setChildren(email_found , email_image))
            .setIndex(0);

    public static final Element<View> use_phone = new Element<View>()
            .setChildren(new Element<View>().setChildren(phone_found , phone_image))
            .setIndex(1);
}
