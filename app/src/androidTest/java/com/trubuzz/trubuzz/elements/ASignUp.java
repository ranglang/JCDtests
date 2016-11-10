package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.ActivityElement;

import static android.R.string.cancel;
import static com.trubuzz.trubuzz.test.R.string.accept;
import static com.trubuzz.trubuzz.test.R.string.country_code_default;
import static com.trubuzz.trubuzz.test.R.string.get_sms;
import static com.trubuzz.trubuzz.test.R.string.input_captcha;
import static com.trubuzz.trubuzz.test.R.string.ok;
import static com.trubuzz.trubuzz.test.R.string.pickup;
import static com.trubuzz.trubuzz.test.R.string.reload_captcha;
import static com.trubuzz.trubuzz.test.R.string.sign_up;
import static com.trubuzz.trubuzz.test.R.string.sign_up_confirm_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_email;
import static com.trubuzz.trubuzz.test.R.string.sign_up_email_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_password_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone_hint;
import static com.trubuzz.trubuzz.test.R.string.terms_of_service;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/9/18.
 */
public class ASignUp {

    public final ActivityElement captcha_frame = new ActivityElement().setChildren(
            new ActivityElement().setId("title").setText(getString("请输入验证码",input_captcha)) ,
            new ActivityElement().setId("content")
    );
    public final ActivityElement captcha_input = new ActivityElement().setId("captcha");
    public final ActivityElement captcha_change = new ActivityElement().setText(getString("看不清? 换一张" ,reload_captcha));
    public final ActivityElement captcha_ok_button = new ActivityElement().setId("ok").setText(getString("确定" ,ok));
    public final ActivityElement captcha_cancel_button = new ActivityElement().setId("cancel").setText(getString("取消" ,cancel));

    protected final ActivityElement reg_pwd = new ActivityElement().setId("password").setHint(getString("请输入密码" ,sign_up_password_hint));
    protected final ActivityElement reg_pwd_confirm = new ActivityElement().setId("confirm").setHint(getString("请再次输入密码" ,sign_up_confirm_hint));
    protected final ActivityElement accept_service_check = new ActivityElement().setId("accept").setText(getString("同意" ,accept));



    public static class RegEmail extends ASignUp{
        public final ActivityElement use_email_reg = new ActivityElement().setChildren(new ActivityElement().setChildren(
           new ActivityElement().setId("text1").setText(getString("邮箱注册" ,sign_up_email))
        ));

        public final ActivityElement email_input = new ActivityElement().setId("email")
                .setHint(getString("请输入您的邮箱地址" ,sign_up_email_hint));

        public final ActivityElement email_reg_pwd = reg_pwd.setUncle(new ActivityElement().setChildren(email_input));

        public final ActivityElement email_reg_pwd_confirm = reg_pwd_confirm.setCousinry(email_input);
//                new ActivityElement().setId("confirm").setHint(getString("请再次输入密码" ,sign_up_confirm_hint)).setUncle(new ActivityElement().setChildren(email_input));

        public final ActivityElement email_reg_submit = new ActivityElement().setId("submit")
                .setText(getString("注册" ,sign_up))
                .setSibling(new ActivityElement().setChildren(email_input));

        public final ActivityElement email_terms = new ActivityElement().setId("service")
                .setText(getString("服务条款",terms_of_service))
                .setUncle(new ActivityElement().setChildren(email_input));

        public final ActivityElement email_accept_service_check = accept_service_check.setCousinry(email_input);
    }

    public static class RegPhone extends ASignUp{

        public final ActivityElement use_phone_reg = new ActivityElement().setChildren(new ActivityElement().setChildren(
                new ActivityElement().setId("text1").setText(getString("手机注册" ,sign_up_phone))
        ));
        public final ActivityElement pickup_country_code_button = new ActivityElement().setId("pickup_country_code")
                .setText(getString("挑选" ,pickup));

        public final ActivityElement country_code_input = new ActivityElement().setId("country_code").setText(getString("86" ,country_code_default));

        public final ActivityElement phone_input = new ActivityElement().setId("phone").setHint(getString("请输入手机号" ,sign_up_phone_hint));

        public final ActivityElement phone_reg_pwd = reg_pwd.setCousinry(phone_input);

        public final ActivityElement phone_reg_pwd_confirm = reg_pwd_confirm.setCousinry(phone_input);

        public final ActivityElement get_sms_button = new ActivityElement().setId("btn_sms").setText(getString("获取验证码" ,get_sms));

        public final ActivityElement phone_accept_service_check = accept_service_check.setCousinry(phone_input);

    }
}
