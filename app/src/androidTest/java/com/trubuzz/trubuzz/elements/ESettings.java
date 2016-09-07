package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by king on 2016/9/6.
 */
public class ESettings {
    private ViewInteraction v_left = null;
    private ViewInteraction v_setting = null ;
    private ViewInteraction v_privateSet = null ;
    private ViewInteraction v_logout = null ;



    private final String ID_LEFT = "action_drawer";
//    private final String ID_SETTING = "drawer_layout";
    private final String TEXT_SETTING = "设置";
    private final String ID_PRIVATE = "private_mode";
    private final String TEXT_LOGOUT = "退出";



    public ViewInteraction leftButton() {
        if (v_left == null){
            this.v_left =  onView(withResourceName(ID_LEFT));
        }
        return v_left;
    }

    public ViewInteraction settingsButton() {
        if (v_setting == null){
//            this.v_setting =  onView(withResourceName(ID_SETTING));
            this.v_setting =  onView(withText(TEXT_SETTING));
        }
        return v_setting;
    }

    public ViewInteraction privateButton() {
        if (v_privateSet == null){
            this.v_privateSet =  onView(withResourceName(ID_PRIVATE));
        }
        return v_privateSet;
    }

    public ViewInteraction logoutButton() {
        if (v_logout == null){
            this.v_logout =  onView(withText(TEXT_LOGOUT));
        }
        return v_logout;
    }
}
