package com.trubuzz.trubuzz.test.login;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.enumerate.Account;
import com.trubuzz.trubuzz.shell.Password;

/**
 * Created by king on 17/6/9.
 */

public interface LoginService {
    /**
     * 浏览用户指南
     */
    void browse_tutorial();

    /**
     * 输入用户名
     * @param username
     */
    void type_username(String username);

    /**
     * 输入密码
     * @param pwd
     */
    void type_password(Password pwd);

    /**
     * 点击登录按钮
     */
    void click_login_button();

    /**
     * 检查登录用户已开户
     */
    void check_broker();

    /**
     * 检查登录用户未开户
     */
    void check_not_broker();

    /**
     * 清楚密码 ( 点击清除密码图标 )
     */
    void clean_password();

    /**
     * 检查密码输入框默认展示
     */
    void check_password_input_default_show();

    /**
     * 重发验证邮件
     * @param sendEmail true:发送 ; false:不发送
     */
    void resend_mail(boolean sendEmail);

    /**
     * 检查7天未验证邮箱提示
     */
    void check_resend_mail_alert();

    /**
     * 输入邮件地址 ( 找回密码 )
     * @param mail
     */
    void type_mail_address(String mail);

    /**
     * 提交邮件地址
     */
    void submit_mail_address();

    /**
     * 进入忘记密码页面
     */
    void into_forget_password_page();

    /**
     * 检查账号默认带入
     * @param accountType
     * @param account
     */
    void check_account_follow(Account accountType, String account);

    /**
     * 输入手机号码
     * @param phone
     */
    void type_phone_number(String phone);

    /**
     * 点击获取短信验证按钮
     */
    void get_sms_code();

    /**
     * 输入短信验证码
     * @param phone
     * @param smsCode == null 则实时获取code进行输入 ,
     */
    String type_sms_code(String phone, String smsCode);

    /**
     * 输入新密码
     * @param password
     */
    void type_new_password(Password password);

    /**
     * 输入确认密码
     * @param confirmPwd
     */
    void type_confirm_password(Password confirmPwd);

    /**
     * 点击 提交按钮 ( 手机找回页面 )
     */
    void submit_phone_retrieve();

    /**
     * 检查使用手机找回密码成功 ,
     * 以成功登录为准
     */
    void check_retrieve_use_phone_successful();

    /**
     * 选择使用邮件找回 Tab
     */
    void select_mail_retrieve();

    /**
     * 选择使用手机找回 Tab
     */
    void select_phone_retrieve();

    /**
     * 检查使用无效邮箱找回时的提示
     * ( 格式错误 , 空值 , 不存在 )
     * @param mail
     */
    void check_invalid_mail_retrieve(String mail);

    /**
     * 检查短信验证码发送成功,
     *      toast提示 , 输入框出现 , 提交按钮出现
     *      重新获取倒计时功能尚未实现 ---/
     */
    void check_get_sms_code_successful();

    /**
     * 检查无效登录提示信息
     * @param usernameIsFormatted  true: 用户名格式正确
     * @param password
     */
    void check_invalid_login(boolean usernameIsFormatted, Password password);

    /**
     * 检查依然还在登录页面
     */
    void check_still_in_login_page();
}
