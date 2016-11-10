package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.forget_password;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.test.R.string.login;
import static com.trubuzz.trubuzz.test.R.string.login_account_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_description;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/8/23.
 */
public class ALogin {

    public static final ActivityElement account_input = new ActivityElement().setId("account").setHint(getString("请输入您的邮箱或手机号" ,login_account_hint));

    public static final ActivityElement forget_pwd_button = new ActivityElement().setText(getString("忘记密码",forget_password));

    public static final ActivityElement sign_up_link = new ActivityElement().setId("signup")
            .setText(getString("请邮箱或手机快速注册登录", sign_up_description));

    public final ActivityElement pwd_input = new ActivityElement().setId("password").setHint(getString("输入密码",input_password));

    public final ActivityElement login_button = new ActivityElement().setId("submit").setText(getString("登入" ,login));

    public final ActivityElement clean_pwd_image = new ActivityElement().setSibling(pwd_input , forget_pwd_button);

}
