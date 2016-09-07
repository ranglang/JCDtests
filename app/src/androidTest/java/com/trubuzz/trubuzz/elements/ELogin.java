package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;

/**
 * Created by king on 2016/8/23.
 */
public class ELogin {
    private ViewInteraction v_user = null ;
    private ViewInteraction v_pwd = null;
    private ViewInteraction v_submit = null;


    private final String ID_USER = "account";
    private final String ID_PWD = "password";
    private final String ID_SUBMIT = "submit";


    public ViewInteraction user() {
        if (v_user == null){
            this.v_user = onView(withResourceName(ID_USER));
        }
        return v_user;
    }

    public ViewInteraction password() {
        if (v_pwd == null){
            this.v_pwd =  onView(withResourceName(ID_PWD));
        }
        return v_pwd;
    }

    public ViewInteraction submit() {
        if (v_submit == null){
            this.v_submit =  onView(withResourceName(ID_SUBMIT));
        }
        return v_submit;
    }



}
