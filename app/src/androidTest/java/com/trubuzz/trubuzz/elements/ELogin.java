package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import com.trubuzz.trubuzz.utils.Find;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by king on 2016/8/23.
 */
public class ELogin {
    private ViewInteraction user = null ;
    private ViewInteraction pwd = null;
    private ViewInteraction submit = null;
    private ViewInteraction left = null;

    private final String PATH_USER = "com.trubuzz.trubuzz:id/account";
    private final String PATH_PWD = "com.trubuzz.trubuzz:id/password";
    private final String PATH_SUBMIT = "com.trubuzz.trubuzz:id/submit";
    private final String PATH_LEFT = "com.trubuzz.trubuzz:id/action_drawer";

    public ViewInteraction user() {
        if (user == null){
            this.user =  onView(withId(Find.id(PATH_USER)));
        }
        return user;
    }

    public ViewInteraction password() {
        if (pwd == null){
            this.pwd =  onView(withId(Find.id(PATH_PWD)));
        }
        return pwd;
    }

    public ViewInteraction submit() {
        if (submit == null){
            this.submit =  onView(withId(Find.id(PATH_SUBMIT)));
        }
        return submit;
    }

    public ViewInteraction leftButton() {
        if (left == null){
            this.left =  onView(withId(Find.id(PATH_LEFT)));
        }
        return left;
    }

}
