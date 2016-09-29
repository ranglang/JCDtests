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

    private final static String ID_LEFT = "action_drawer";
    private final static String TEXT_SETTING = "设置";
    private final static String ID_PRIVATE = "private_mode";
    private final static String TEXT_LOGOUT = "退出";

    public static ViewInteraction leftButton() {
        return onView(withResourceName(ID_LEFT));
    }

    public static ViewInteraction settingsButton() {
       return onView(withText(God.getString(TEXT_SETTING ,com.trubuzz.trubuzz.test.R.string.preference)));
    }

    public static ViewInteraction privateButton() {
        return onView(withResourceName(ID_PRIVATE));
    }

    public static ViewInteraction logoutButton() {
        return onView(withText( God.getString(TEXT_LOGOUT ,com.trubuzz.trubuzz.test.R.string.logout)));
    }
}
