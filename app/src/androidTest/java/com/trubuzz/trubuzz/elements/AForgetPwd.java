package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

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
    private final ActivityElement email_found = new ActivityElement().setId("text1")
            .setText(getString("邮箱找回",forget_password_email));

    private final ActivityElement phone_found = new ActivityElement().setId("text1")
            .setText(getString("手机找回",forget_password_phone));

    private final ActivityElement email_image = new ActivityElement().setId("icon");
    private final ActivityElement phone_image = new ActivityElement().setId("icon");

    private final ActivityElement email_text = new ActivityElement().setText(getString("邮箱地址",email));



    public final ActivityElement phone_submit_button = new ActivityElement().setId("submit")
            .setText(getString("提交",submit))
            .setIndex(8);

    public final ActivityElement sms_input = new ActivityElement().setId("sms")
            .setHint(getString("请输入短信验证码",sign_up_sms_hint));

    public final ActivityElement password_input = new ActivityElement().setId("password")
            .setHint(getString("请输入密码" ,sign_up_password_hint));

    public final ActivityElement password_confirm = new ActivityElement().setId("confirm")
            .setHint(getString("请再次输入密码" ,sign_up_confirm_hint));

    public final ActivityElement get_sms_button = new ActivityElement().setId("btn_sms")
            .setText(getString("获取验证码" ,get_sms));

    public final ActivityElement phone_input = new ActivityElement().setId("phone")
            .setHint(getString("请输入手机号",sign_up_phone_hint));

    public final ActivityElement email_input = new ActivityElement().setId("email")
            .setHint(getString("请输入您的邮箱地址",sign_up_email_hint))
            .setSiblings(email_text);

    public final ActivityElement email_submit_button = new ActivityElement().setId("submit")
            .setText(getString("提交",submit))
            .setIndex(2);

    public final ActivityElement use_email_found = new ActivityElement()
            .setChildren(new ActivityElement().setChildren(email_found , email_image))
            .setIndex(0);

    public final ActivityElement use_phone_found = new ActivityElement()
            .setChildren(new ActivityElement().setChildren(phone_found , phone_image))
            .setIndex(1);
}
