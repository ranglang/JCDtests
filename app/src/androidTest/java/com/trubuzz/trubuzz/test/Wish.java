package com.trubuzz.trubuzz.test;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Config;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.utils.God;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.trubuzz.trubuzz.elements.ALogin.account;
import static com.trubuzz.trubuzz.elements.ALogin.password;
import static com.trubuzz.trubuzz.elements.ALogin.submit;
import static com.trubuzz.trubuzz.elements.ASettings.leftButton;
import static com.trubuzz.trubuzz.elements.ASettings.logoutButton;
import static com.trubuzz.trubuzz.elements.ASettings.settingsButton;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.check;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.perform;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 2016/10/19.
 */

public class Wish {

    private static final String TAG = "jcd_Wish";

    /**
     * 判断是否登录
     * 通过当前activity来判断 , 拥有5秒的加载时间
     * if 已登录则返回 true
     * @param atr
     * @return
     */
    public static boolean isLogin( ActivityTestRule<?> atr){
        for(int i=0; i<5 ;i++){
            if(God.getTopActivityName(atr.getActivity()).equals(AName.LOGIN)){
                return false;
            }
            if(isVisible(ASettings.leftButton())) return true;
            sleep(1000);
        }
        return true;
    }

    public static boolean hasBroker(){
        for(int i=0; i<5; i++){

        }
        try {
            check(AAsset.ID_TEXT_net_worth , matches((isDisplayed())));
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isVisible(ViewInteraction v ){
        try {
            v.check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 单纯的登出操作
     */
    public static void logout() {
        perform(leftButton() , click(ViewActions.pressBack()));
        perform(settingsButton() , click());
        perform(logoutButton() , click());
    }

    /**
     * 单纯的登录操作
     * @param user
     * @param pwd
     */
    public static void login(String user , String pwd){
        perform(account() , replaceText(user));
        perform(password() , replaceText(pwd));
        perform(submit() , click());
    }

    /**
     * 期望使用已开户用户登录
     * @param atr
     * @param user
     * @param pwd
     */
    public static void wantBrokerLogin(ActivityTestRule<?> atr ,String user , String pwd){
        if(! isLogin(atr)){
            login(user,pwd);
        }else if(! hasBroker()){
            logout();
            login(user,pwd);
        }else
            Log.i(TAG, "wantBrokerLogin: 已经是已开户用户登录");
    }
    public static void wantBrokerLogin(ActivityTestRule<?> atr ){
        wantBrokerLogin(atr , Config.hasBrokerUser , Config.hasBrokerPwd);
    }

    /**
     * 期望使用未开户用户登录
     * @param atr
     * @param user
     * @param pwd
     */
    public static void wantNotBrokerLogin(ActivityTestRule<?> atr ,String user , String pwd){
        if(! isLogin(atr)){
            login(user,pwd);
        }else if( hasBroker()){
            logout();
            login(user,pwd);
        }else
            Log.i(TAG, "wantNotBrokerLogin: 已经是未开户用户登录");
    }
    public static void wantNotBrokerLogin(ActivityTestRule<?> atr ){
        wantNotBrokerLogin(atr ,Config.notBrokerUser , Config.notBrokerPwd);
    }
}
