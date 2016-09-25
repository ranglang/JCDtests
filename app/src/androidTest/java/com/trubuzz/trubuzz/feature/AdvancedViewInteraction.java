package com.trubuzz.trubuzz.feature;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.util.Log;

import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Registor;

/**
 * Created by king on 2016/9/5.
 */
public class AdvancedViewInteraction {
    private final static String TAG = "jcd_AdViewInteraction";

    public static ViewInteraction perform(final ViewInteraction v , final ViewAction... viewActions) {
        String key = AdvancedViewInteraction.class.toString();

        for(int i=1 ;i<=5;i++){
            try {
                ViewInteraction viewInteraction =  v.perform(viewActions);
                Registor.unReg(key);
                return viewInteraction;
            } catch (NoMatchingViewException exception){
                Log.w(TAG,"第"+i+"次未匹配到元素:"+v.toString());
                if (i == 1){
                    Registor.reg(key
                            , DoIt.takeBitmap(God.getCurrentActivity(InstrumentationRegistry.getInstrumentation()))
                    );
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        ViewInteraction viewInteraction =  v.perform(viewActions);
        Registor.unReg(key);
        return viewInteraction;
    }

    public static ViewInteraction check(final ViewInteraction v,final ViewAssertion viewAssert){
        for(int i=1 ;i<=5;i++){
            try {
                return v.check(viewAssert);
            } catch (NoMatchingViewException exception){
                Log.w(TAG,"第"+i+"次未匹配到元素:"+v.toString());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return v.check(viewAssert);
    }
}
