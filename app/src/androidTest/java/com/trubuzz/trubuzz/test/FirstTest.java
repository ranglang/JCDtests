package com.trubuzz.trubuzz.test;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        ALogin aLogin = new ALogin();
        Thread.sleep(2000);
        aLogin.user().perform(replaceText("abc@abc.com"));
        aLogin.password().perform(replaceText("aA123321111"));
//        aLogin.submit().perform(click());
        Context ctx = InstrumentationRegistry.getContext();
        Resources res = ctx.getResources();
        String s = res.getString(com.trubuzz.trubuzz.test.R.string.accept);
        Log.i("jcd", "testLogin1: "+s);
    }

    @After
    public void tearDown(){
        DoIt.takeScreenshot(God.getCurrentActivity(InstrumentationRegistry.getInstrumentation()),"ccc");
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
