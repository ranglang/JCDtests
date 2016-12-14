package com.trubuzz.trubuzz.test;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Config;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.shell.AdViewInteraction;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by king on 2016/10/19.
 */

public class Wish {

    private static final String TAG = "jcd_Wish";
    private static ALogin aLogin = new ALogin();
    private static ASettings aSettings = new ASettings();

    /**
     * 判断是否登录
     * 通过当前activity来判断 , 拥有5秒的加载时间
     * if 已登录则返回 true
     * @param atr
     * @return
     */
    public static boolean isLogin( ActivityTestRule<?> atr){
        for(int i=0; i<5 ;i++){
            if(atr !=null && God.getTopActivityName(atr.getActivity()).equals(AName.LOGIN)){
                return false;
            }
            if(isVisible(ASettings.left_drawer)) return true;
            sleep(1000);
        }
        return false;
    }
    public static boolean isLogin(){
        return isLogin(null);
    }
    public static boolean hasBroker(){
        for(int i=0; i<5; i++){
            try {
                given(AAsset.net_worth_view).check( matches((isDisplayed())));
                return true;
            } catch (Exception e){
                e.printStackTrace();
                sleep(1000);
            }
        }
        return false;
    }

    /**
     * 单纯的登出操作
     */
    public static void logout() {
        given(ASettings.left_drawer).perform(click(ViewActions.pressBack()));
        given(aSettings.setting).perform(click());
        given(aSettings.logout_button).perform(click());
    }

    /**
     * 单纯的登录操作
     * @param user
     * @param pwd
     */
    public static void login(String user , String pwd){
        given(ALogin.account_input).perform(replaceText(user));
        given(aLogin.pwd_input).perform(replaceText(pwd));
        given(aLogin.login_button).perform( click());
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
    public static void wantBrokerLogin(){
        wantBrokerLogin(null);
    }
    /**
     * 希望登录 , 默认使用已开户的用户
     * @param atr
     * @param user
     * @param pwd
     */
    public static void wantLogin(ActivityTestRule<?> atr ,String user , String pwd){
        if(! isLogin(atr)) {
            login(user, pwd);
        }
    }
    public static void wantLogin(ActivityTestRule<?> atr){
        wantLogin(atr,Config.hasBrokerUser , Config.hasBrokerPwd);
    }
    public static void wantLogin(){
        wantLogin(null);
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
    public static void wantNotBrokerLogin(){
        wantNotBrokerLogin(null);
    }

    /**
     * 期望是没有登录的
     * @param atr
     */
    public static void wantNotLogin(ActivityTestRule<?> atr){
        if(isLogin(atr)){
            logout();
        }
    }
    public static void wantNotLogin(){
        wantNotLogin(null);
    }


    /**
     * 判断元素可见
     * @param v
     * @return
     */
    public static boolean isVisible(ViewInteraction v ){
        return isVisible(new AdViewInteraction(v));
    }
    public static boolean isVisible(Element v ){
        return isVisible(given(v));
    }
    public static boolean isVisible(Matcher<View> v ){
        return isVisible(new AdViewInteraction(onView(v)));
    }
    public static boolean isVisible(AdViewInteraction v ){
        try {
            v.check(matches((isDisplayingAtLeast(90))));
            return true;
        } catch (Throwable e ){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量检查元素个性
     * @param elements
     */
    public static void allDisplayed(Element ...elements){
        for(Element element : elements){
            given(element).check(matches(isDisplayed()));
        }
    }
    public static void allNotDisplayed(Element ...elements){
        for(Element element : elements){
            given(element).check(matches(not(isDisplayed())));
        }
    }
    public static void allEnabled(Element ...elements){
        for(Element element : elements){
            given(element).check(matches(isEnabled()));
        }
    }
    public static void allNotEnabled(Element ...elements){
        for(Element element : elements){
            given(element).check(matches(not(isEnabled())));
        }
    }
}
