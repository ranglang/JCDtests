package com.trubuzz.trubuzz.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.trubuzz.trubuzz.elements.ELogin;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;

/**
 * Created by king on 2016/9/5.
 */
@RunWith(AndroidJUnit4.class)
public class FirstTest {
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.trubuzz.roy.MainActivity";
    private static Class<?> launcherActivityClass;

    public static Intent intent;
//    static{
//        try {
//            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Rule
//    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(launcherActivityClass,true,true);
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(getLauncherActivityClass(),true,true);

    @Test
    public void testLogin1() throws InterruptedException {
        ELogin eLogin = new ELogin();
        Thread.sleep(2000);
        eLogin.user().perform(replaceText("abc@abc.com"));
        eLogin.password().perform(replaceText("aA123321"));
        eLogin.submit().perform(click());
    }

    public  Class<?> getLauncherActivityClass(){
        try {
            return Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
