package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import com.trubuzz.trubuzz.utils.God;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by king on 2016/9/6.
 */
public class ASettings {
    private static ViewInteraction v_left = null;
    private static ViewInteraction v_setting = null ;
    private static ViewInteraction v_privateSet = null ;
    private static ViewInteraction v_logout = null ;



    private final static String ID_LEFT = "action_drawer";
    private final static String TEXT_SETTING = "设置";
    private final static String ID_PRIVATE = "private_mode";
    private final static String TEXT_LOGOUT = "退出";



    public static ViewInteraction leftButton() {
        if (v_left == null){
            v_left =  onView(withResourceName(ID_LEFT));
        }
        return v_left;
    }

    public static ViewInteraction settingsButton() {
        if (v_setting == null){
            v_setting =  onView(withText(God.getString(TEXT_SETTING ,com.trubuzz.trubuzz.test.R.string.preference)));
        }
        return v_setting;
    }

    public static ViewInteraction privateButton() {
        if (v_privateSet == null){
            v_privateSet =  onView(withResourceName(ID_PRIVATE));
        }
        return v_privateSet;
    }

    public static ViewInteraction logoutButton() {
        if (v_logout == null){
            v_logout =  onView(withText( God.getString(TEXT_LOGOUT ,com.trubuzz.trubuzz.test.R.string.logout)));
        }
        return v_logout;
    }
}
