package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Registor;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;

/**
 * Created by king on 16/10/24.
 */

public class AdViewInteractionC {
    private static final String TAG = "jcd_AdViewInteraction";
    private static String key = AdViewInteractionC.class.toString();
    private final Element<Matcher<View>> viewElement;
    private final ViewInteraction viewInteraction;


    public AdViewInteractionC(Element<Matcher<View>> viewElement){
        this.viewElement = viewElement;
        this.viewInteraction = onView(viewElement.interactionWay());
    }
    /**
     * 改良后的perform , 加入等待与错误截图
     * @param times
     * @param viewActions
     * @return
     */
    public AdViewInteractionC perform(final int times , final ViewAction... viewActions) {
        for(int i = 1; i<times ; i++){
            Log.i(TAG, String.format("perform: 开始第 %s 次匹配 ' %s ' .",i,viewElement.toString()));
            if(canPerform(this.viewInteraction, viewActions)){
                DoIt.delFile((String) Registor.unReg(key));
                return this;
            }else if(i==2 ){
                //执行截图并保存文件名
                Registor.reg( key , ((BaseTest)Registor.peekReg(BaseTest.class.toString())).takeScreenshot());
            }
            Log.w(TAG,String.format("perform: 第 %s 次未匹配到元素 ' %s ' .",i ,viewElement.toString()));
        }
        this.viewInteraction.perform(viewActions);
        DoIt.delFile((String)Registor.unReg(key));
        return this;
    }
    public AdViewInteractionC perform(final ViewAction... viewActions) {
        return this.perform(5 ,viewActions);
    }
    public AdViewInteractionC perform(boolean isTry , final ViewAction... viewActions) {
        if(isTry){
            try {
                return perform(viewActions);
            }catch (Throwable e ){
                return this;
            }
        }else{
            return perform(viewActions);
        }
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
    public AdViewInteractionC check(final int times , final ViewAssertion viewAssert){
        for(int i=1; i<times ; i++){
            Log.i(TAG, String.format("check: 开始第 %s 次匹配 ' %s ' .",i,viewElement.toString()));
            if(checkRight(viewAssert)){
                DoIt.delFile((String)Registor.unReg(key));
                return this;
            }else if(i==2 ){
                //执行截图并保存文件名
                Registor.reg( key , ((BaseTest)Registor.peekReg(BaseTest.class.toString())).takeScreenshot());
            }
            Log.w(TAG,String.format("check: 第 %s 次未匹配到元素 ' %s ' .",i ,viewElement.toString()));
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
    public AdViewInteractionC check(final ViewAssertion viewAssert){
        return check(5 , viewAssert);
    }

    public AdViewInteractionC check(boolean isTry , final ViewAssertion viewAssert){
        if(isTry){
            try {
                return check(viewAssert);
            }catch (Throwable e ){
                return this;
            }
        }else
            return check(viewAssert);
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
    public AdViewInteractionC pause(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

}
