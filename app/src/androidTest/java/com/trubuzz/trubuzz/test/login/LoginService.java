package com.trubuzz.trubuzz.test.login;

/**
 * Created by king on 17/6/9.
 */

public interface LoginService {
    /**
     * 浏览用户指南
     */
    void browse_tutorial();

    void type_username(String username);

    void type_password(String pwd);

    void click_login_button();

    void check_broker();

    void check_not_broker();
}
