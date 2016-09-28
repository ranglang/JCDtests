package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import com.trubuzz.trubuzz.utils.God;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;

/**
 * Created by king on 2016/8/23.
 */
public class ALogin {
    private static ViewInteraction v_user = null ;
    private static ViewInteraction v_pwd = null;
    private static ViewInteraction v_submit = null;
    private static ViewInteraction v_signUp = null;
    private static ViewInteraction s_loginToast = null;



    private final static String ID_USER = "account";
    private final static String ID_PWD = "password1";
    private final static String ID_SUBMIT = "submit";
    private final static String ID_SIGNUP = "signup";


    public static ViewInteraction account() {
        if (v_user == null){
            v_user = onView(withResourceName(ID_USER));
        }
        return v_user;
    }

    public static ViewInteraction password() {
        if (v_pwd == null){
            v_pwd =  onView(withResourceName(ID_PWD));
        }
        return v_pwd;
    }

    public static ViewInteraction submit() {
        if (v_submit == null){
            v_submit =  onView(withResourceName(ID_SUBMIT));
        }
        return v_submit;
    }

    public static ViewInteraction signUp() {
        if (v_signUp == null){
            v_submit =  onView(withResourceName(ID_SIGNUP));
        }
        return v_signUp;
    }

    public static String loginToast(){
        return God.getString(com.trubuzz.trubuzz.test.R.string.login_failed);
    }
}
