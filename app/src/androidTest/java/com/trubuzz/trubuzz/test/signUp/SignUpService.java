package com.trubuzz.trubuzz.test.signUp;

import com.trubuzz.trubuzz.constant.enumerate.Account;

/**
 * Created by king on 2017/7/3.
 */

public interface SignUpService {

    /**
     * 进入注册页面
     */
    void into_sign_up_page();

    /**
     * 验证使用邮箱注册默认展示
     * 基本展示
     *      详尽展示 : 可细化到字段名称校验
     */
    void verify_email_sign_up_default_show();

    /**
     * 验证使用手机注册默认展示
     * 基本展示
     *      详尽展示 : 可细化到字段名称校验
     */
    void verify_phone_sign_up_default_show();

    /**
     * 选择注册方式 ( 邮箱 / 手机 )
     * @param account
     */
    void select_way_for_sign_up(Account account);

    /**
     * 输入邮箱地址
     * @param email
     */
    void type_email_address(String email);

    /**
     * 输入密码
     * @param password
     */
    void type_password(String password);

    /**
     * 输入确认密码
     * @param confirmPassword
     */
    void type_confirm_password(String confirmPassword);

    /**
     * 同意服务条款
     *      true : 同意 ; false : 不同意
     * @param agree
     */
    void agree_with_the_terms(boolean agree);

    /**
     * 点击注册按钮 ( email 注册 )
     */
    void submit_email_sign_up();

    /**
     * 检查图像验证码弹出层展示
     */
    void check_image_verify_code_show();

    /**
     * 输入图像验证码
     * @param imageCode null : 获取正确的验证码并输入
     *                  其他 : 原样输入
     */
    void type_image_verify_code(String imageCode);

    /**
     * 确认输入的图像验证码 ( 点击 ok/确定 按钮 )
     */
    void confirm_image_verify_code_input();

    /**
     * 检查注册成功
     *      需校验自动登录系统
     */
    void check_sign_up_successful();

    /**
     * 输入国别码
     * @param country_code
     */
    void type_country_code(String country_code);

    /**
     * 输入手机号
     * @param phone
     */
    void type_phone_number(String phone);

    /**
     * 点击获取验证码
     */
    void do_get_sms_code();

    /**
     * 输入短信验证码
     * @param phone
     * @param sms_code null : 自动化获取验证码
     */
    String type_sms_code(String phone, String sms_code);

    /**
     * 点击手机注册的提交按钮
     */
    void submit_phone_sign_up();
}