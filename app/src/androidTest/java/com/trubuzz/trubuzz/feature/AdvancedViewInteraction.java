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
 * 自定义操作和检查等事件 , 实现间隔等待和截图
 */
public class AdvancedViewInteraction {
    private final static String TAG = "jcd_AdViewInteraction";
    private final static String key = AdvancedViewInteraction.class.toString();

    /**
     * 匹配指定次数
     * @param times
     * @param v
     * @param viewActions
     * @return
     */
    public static ViewInteraction perform(final int times ,final ViewInteraction v , final ViewAction... viewActions ) {
        ViewInteraction viewInteraction = null;
        int i;
        for(i=1 ;i<times;i++){
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
                    // 2 秒未出现则进行截图
                    Registor.reg( key , ((BaseTest)Registor.peekReg(BaseTest.class.toString())).takeScreenshot());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (i == times-1){
                    Log.i(TAG, "perform: 开始第"+(i+1)+"次匹配.....");
                    viewInteraction =  v.perform(viewActions);
                    String filePath = (String)Registor.unReg(key);
                    if ( filePath != null){
                        new File(filePath).delete();
                        Log.i(TAG, "perform:  删除临时截图");
                    }
                }
            }
        }
        return viewInteraction;
    }

    /**
     * 默认匹配 5 次
     * @param v
     * @param viewActions
     * @return
     */
    public static ViewInteraction perform(final ViewInteraction v , final ViewAction... viewActions ) {
        return perform(5 ,v , viewActions);
    }

    /**
     * 匹配指定的次数
     * @param times
     * @param v
     * @param viewAssert
     * @return
     */
    public static ViewInteraction check(final int times ,final ViewInteraction v,final ViewAssertion viewAssert){
        ViewInteraction viewInteraction = null;
        int i;
        for(i=1 ;i<times ;i++){
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
                    Registor.reg( key , ((BaseTest)Registor.peekReg(BaseTest.class.toString())).takeScreenshot());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                //在这里进行第 times 次匹配
                if (i == times-1){
                    Log.i(TAG, "perform: 开始第"+(i+1)+"次匹配.....");
                    viewInteraction =  v.check(viewAssert);
                    String filePath = (String)Registor.unReg(key);
                    if ( filePath != null){
                        new File(filePath).delete();
                        Log.i(TAG, "perform:  删除临时截图");
                    }
                }
            }
        }
        return viewInteraction;
    }

    /**
     * 默认匹配 5 次
     * @param v
     * @param viewAssert
     * @return
     */
    public static ViewInteraction check(final ViewInteraction v,final ViewAssertion viewAssert){
        return check(5 , v, viewAssert);
    }
}
