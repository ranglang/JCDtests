package com.trubuzz.trubuzz.test.login;

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
    void type_password(String pwd);

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
}
