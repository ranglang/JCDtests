package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.util.Log;

import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Registor;

/**
 * Created by king on 16/10/24.
 */

public class AdViewInteraction {
    private static final String TAG = "jcd_AdViewInteraction";
    private ViewInteraction viewInteraction;
    private static String key = AdViewInteraction.class.toString();

    public AdViewInteraction(ViewInteraction viewInteraction) {
        this.viewInteraction = viewInteraction;
    }
    public AdViewInteraction(){}
    /**
     * 改良后的perform , 加入等待与错误截图
     * @param times
     * @param viewActions
     * @return
     */
    public AdViewInteraction perform(final int times ,final ViewAction... viewActions) {
        for(int i = 1; i<times ; i++){

            Log.i(TAG, "perform: 开始第"+i+"次匹配.....");
            if(canPerform(this.viewInteraction, viewActions)){
                DoIt.delFile((String) Registor.unReg(key));
                return this;
            }else if(i==2 ){
                //执行截图并保存文件名
                Registor.reg( key , ((BaseTest)Registor.peekReg(BaseTest.class.toString())).takeScreenshot());
            }
            Log.w(TAG,"perform: 第"+i+"次未匹配到元素:"+this.viewInteraction.toString());
        }
        this.viewInteraction.perform(viewActions);
        DoIt.delFile((String)Registor.unReg(key));
        return this;
    }
    public AdViewInteraction perform(final ViewAction... viewActions) {
        return this.perform(5 ,viewActions);
    }

    /**
     * 判断操作是否成功
     * @param v
     * @param viewActions
     * @return
     */
    public static boolean canPerform(final ViewInteraction v , final ViewAction... viewActions ) {
        try {
            v.perform(viewActions);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 改良后的check , 加入等待与错误截图
     * @param times
     * @param viewAssert
     * @return
     */
    public AdViewInteraction check(final int times ,final ViewAssertion viewAssert){
        for(int i=1; i<times ; i++){
            Log.i(TAG, "check: 开始第"+i+"次匹配.....");
            if(checkRight(viewAssert)){
                DoIt.delFile((String)Registor.unReg(key));
                return this;
            }else if(i==2 ){
                //执行截图并保存文件名
                Registor.reg( key , ((BaseTest)Registor.peekReg(BaseTest.class.toString())).takeScreenshot());
            }
            Log.w(TAG,"check: 第"+i+"次未匹配到元素:"+this.viewInteraction.toString());
        }
        this.viewInteraction.check(viewAssert);
        DoIt.delFile((String)Registor.unReg(key));
        return this;
    }
    /**
     * 默认匹配 5 次
     * @param viewAssert
     * @return
     */
    public AdViewInteraction check(final ViewAssertion viewAssert){
        return check(5 , viewAssert);
    }


    /**
     * 判断断言是否成功
     * @param viewAssert
     * @return
     */
    public boolean checkRight(final ViewAssertion viewAssert){
        try {
            this.viewInteraction.check(viewAssert);
            return true;
        } catch (Error e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 暂停time毫秒
     * @param time
     * @return
     */
    public AdViewInteraction pause(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }
}
