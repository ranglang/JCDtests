package com.trubuzz.trubuzz.feature;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.util.Log;

import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.utils.Registor;

import java.io.File;

/**
 * Created by king on 2016/9/5.
 */
public class AdvancedViewInteraction {
    private final static String TAG = "jcd_AdViewInteraction";
    private final static String key = AdvancedViewInteraction.class.toString();

    public static ViewInteraction perform(final ViewInteraction v , final ViewAction... viewActions ) {
        ViewInteraction viewInteraction ;

        for(int i=1 ;i<=5;i++){
            try {
                viewInteraction =  v.perform(viewActions);
                String filePath = (String)Registor.unReg(key);
                if ( filePath != null){
                    new File(filePath).delete();
                    Log.i(TAG, "perform:  删除临时截图");
                }
                return viewInteraction;
            } catch (NoMatchingViewException exception){
                Log.w(TAG,"第"+i+"次未匹配到元素:"+v.toString());
                if (i == 2){
                    Registor.reg( key , ((BaseTest)Registor.unReg(BaseTest.class.toString())).takeScreenshot());
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        viewInteraction =  v.perform(viewActions);
        String filePath = (String)Registor.unReg(key);
        if ( filePath != null){
            new File(filePath).delete();
            Log.i(TAG, "perform:  删除临时截图");
        }
        return viewInteraction;
    }

    public static ViewInteraction check(final ViewInteraction v,final ViewAssertion viewAssert){
        ViewInteraction viewInteraction ;

        for(int i=1 ;i<=5;i++){
            try {
                viewInteraction =  v.check(viewAssert);
                String filePath = (String)Registor.unReg(key);
                if ( filePath != null){
                    new File(filePath).delete();
                    Log.i(TAG, "perform:  删除临时截图");
                }
                return viewInteraction;
            } catch (NoMatchingViewException exception){
                Log.w(TAG,"第"+i+"次未匹配到元素:"+v.toString());
                if (i == 2){
                    Registor.reg( key , ((BaseTest)Registor.unReg(BaseTest.class.toString())).takeScreenshot());
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        viewInteraction =  v.check(viewAssert);
        String filePath = (String)Registor.unReg(key);
        if ( filePath != null){
            new File(filePath).delete();
            Log.i(TAG, "perform:  删除临时截图");
        }
        return viewInteraction;
    }
}
