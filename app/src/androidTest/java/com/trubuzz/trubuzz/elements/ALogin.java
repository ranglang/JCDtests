package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static com.trubuzz.trubuzz.elements.WithAny.getToast;
import static com.trubuzz.trubuzz.test.R.string.login_failed;
import static com.trubuzz.trubuzz.test.R.string.sign_up_description;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/8/23.
 */
public class ALogin {

    private final static String ID_USER = "account";
    private final static String ID_PWD = "password";
    private final static String ID_SUBMIT = "submit";
    private final static String[] ID_TEXT_SIGNUP = {"signup",
            getString("请邮箱或手机快速注册登录", sign_up_description)};
    private final static String TEXT_loginToast = getString(login_failed);


    public static ViewInteraction account() {
        return onView(withResourceName(ID_USER));
    }

    public static ViewInteraction password() {
        return  onView(withResourceName(ID_PWD));
    }

    public static ViewInteraction submit() {
        return onView(withResourceName(ID_SUBMIT));
    }

    public static ViewInteraction signUp() {
//        return onView(allOf(
//                withResourceName(ID_TEXT_SIGNUP[0]),
//                withText(God.getString(ID_TEXT_SIGNUP[1], com.trubuzz.trubuzz.test.R.string.sign_up_description))
//        ));
        // 这是上述实现的封装实现
        return WithAny.getViewInteraction(ID_TEXT_SIGNUP);
    }

    public static ViewInteraction loginToast(){
        return getToast(TEXT_loginToast);
    }
}
