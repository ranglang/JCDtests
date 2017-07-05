package com.trubuzz.trubuzz.test.signUp.views;

import android.widget.TextView;

import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static android.R.string.cancel;
import static com.trubuzz.trubuzz.test.R.string.accept;
import static com.trubuzz.trubuzz.test.R.string.confirm_password;
import static com.trubuzz.trubuzz.test.R.string.country_code_default;
import static com.trubuzz.trubuzz.test.R.string.email;
import static com.trubuzz.trubuzz.test.R.string.get_sms;
import static com.trubuzz.trubuzz.test.R.string.input_captcha;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.test.R.string.input_sms;
import static com.trubuzz.trubuzz.test.R.string.ok;
import static com.trubuzz.trubuzz.test.R.string.phone_number;
import static com.trubuzz.trubuzz.test.R.string.pickup;
import static com.trubuzz.trubuzz.test.R.string.reload_captcha;
import static com.trubuzz.trubuzz.test.R.string.sign_up;
import static com.trubuzz.trubuzz.test.R.string.sign_up_confirm_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_description;
import static com.trubuzz.trubuzz.test.R.string.sign_up_email;
import static com.trubuzz.trubuzz.test.R.string.sign_up_email_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_password_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_hint;
import static com.trubuzz.trubuzz.test.R.string.terms_of_service;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2017/7/3.
 */

public class SignUpView {

    // 快速注册链接
    public final ActivityElement sign_up_link = new ActivityElement().setId("signup")
            .setText(getString("快速註冊登入", sign_up_description));

    /*** 邮箱注册 / 手机注册 公用元素 ***/
    // 输入密码
    public final ActivityElement reg_pwd = new ActivityElement().setId("password").setHint(getString("请输入密码" ,sign_up_password_hint));
    // 确认密码
    public final ActivityElement reg_pwd_confirm = new ActivityElement().setId("confirm").setHint(getString("请再次输入密码" ,sign_up_confirm_hint));
    // 邮箱注册服务条款链接
    public final ActivityElement service_terms = new ActivityElement().setId("service")
            .setText(getString("服务条款",terms_of_service));

    // 服务条款CheckBox
    public final ActivityElement accept_service_check = new ActivityElement().setId("accept").setText(getString("同意" ,accept));

    // 输入密码label
    public final ActivityElement pwd_label = new ActivityElement().setText(getString("输入密码", input_password))
            .setAssignableClass(TextView.class);
    // 确认密码label
    public final ActivityElement pwd_confirm_label = new ActivityElement().setText(getString("确认密码", confirm_password))
            .setAssignableClass(TextView.class);

    // 图像验证码 frame
    public final ActivityElement image_captcha_frame = new ActivityElement().setChildren(
            new ActivityElement().setId("title").setText(getString("请输入验证码",input_captcha)) ,
            new ActivityElement().setId("content")
    );
    // 验证码图像
    public final ActivityElement captcha_image = new ActivityElement().setId("image")
            .setAssignableClass(android.widget.ImageView.class);
    // 图像验证输入框
    public final ActivityElement image_captcha_input = new ActivityElement().setId("captcha");
    // 图像验证码 -- 更新
    public final ActivityElement image_captcha_change = new ActivityElement().setText(getString("看不清? 换一张" ,reload_captcha));
    // 图像验证码输入确认
    public final ActivityElement image_captcha_ok_button = new ActivityElement().setId("ok").setText(getString("确定" ,ok));
    // 图像验证码输入取消
    public final ActivityElement image_captcha_cancel_button = new ActivityElement().setId("cancel").setText(getString("取消" ,cancel));

    /*** 邮箱注册 ***/
    // 使用邮箱注册
    public final ActivityElement use_email_reg = new ActivityElement().setChildren(new ActivityElement().setChildren(
            new ActivityElement().setId("text1").setText(getString("邮箱注册" ,sign_up_email))
    ));

    // 邮箱地址输入框
    public final ActivityElement email_input = new ActivityElement().setId("email")
            .setHint(getString("请输入您的邮箱地址" ,sign_up_email_hint));
    // 邮箱地址label
    public final ActivityElement email_label = new ActivityElement().setText(getString("邮箱地址", email))
            .setAssignableClass(TextView.class);

    // 邮箱注册提交按钮
    public final ActivityElement email_reg_submit = new ActivityElement().setId("submit")
            .setText(getString("注册" ,sign_up))
            .setSiblings(new ActivityElement().setChildren(email_input));


    /*** 手机注册 ***/
    // 使用手机注册
    public final ActivityElement use_phone_reg = new ActivityElement().setChildren(new ActivityElement().setChildren(
            new ActivityElement().setId("text1").setText(getString("手机注册" ,sign_up_phone))
    ));

    // 挑选国别码 ( dev 和 global 版本用到 )
    public final ActivityElement pickup_country_code_button = new ActivityElement().setId("pickup_country_code")
            .setText(getString("挑选" ,pickup));

    // 国别码输入 ( dev 和 global 版本用到 )
    public final ActivityElement country_code_input = new ActivityElement().setId("country_code").setText(getString("86" ,country_code_default));

    // 手机号输入
    public final ActivityElement phone_input = new ActivityElement().setId("phone").setHint(getString("请输入手机号" ,sign_up_phone_hint));
    // 手机号label
    public final ActivityElement phone_label = new ActivityElement().setText(getString("手机号", phone_number))
            .setAssignableClass(android.widget.TextView.class);
    // 获取短信验证码按钮
    public final ActivityElement get_sms_button = new ActivityElement().setId("btn_sms").setText(getString("获取验证码" ,get_sms));

    // 短信验证码输入
    public final ActivityElement sms_captcha_input = new ActivityElement().setId("sms")
            .setHint(getString("请输入短信验证码" ,sign_up_sms_hint))
            .setDis(false);
    // 短信验证码label
    public final ActivityElement sms_captcha_label = new ActivityElement().setText(getString("输入短信验证码" ,input_sms))
            .setAssignableClass(android.widget.TextView.class);

    // 手机注册提交按钮
    public final ActivityElement phone_reg_submit = new ActivityElement().setId("submit")
            .setText(getString("注册" ,sign_up))
            .setSiblings(new ActivityElement().setChildren(phone_input));

}
