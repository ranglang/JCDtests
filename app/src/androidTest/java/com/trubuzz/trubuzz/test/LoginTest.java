package com.trubuzz.trubuzz.test;

import android.support.annotation.NonNull;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.trubuzz.trubuzz.data.AName;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.elements.EBrokerChoose;
import com.trubuzz.trubuzz.feature.CustomMatcher;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.perform;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by king on 2016/8/24.
 */
@RunWith(Parameterized.class)
public class LoginTest extends BaseTest{

    private ALogin aLogin = new ALogin();
    private ASettings aSettings = new ASettings();
    private EBrokerChoose eBrokerChoose = new EBrokerChoose();
    private String user;
    private String pwd;
    private String expect;
    SomeActivityIdlingResource ltr1;

    public LoginTest(String user, String pwd , String expect) {
        this.user = user;
        this.pwd = pwd;
        this.expect = expect;
    }
    @Rule
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @NonNull
    @Parameterized.Parameters
    public static Collection dLogin(){
        return Arrays.asList(new Object[][]{
                    {"abc@abc.com","aA123321","失败"},
//                {"abc@abc.com","123321","未开户"},
//                {"zhao.deng@jucaidao.com","aA123456","成功登录"}
        });
    }
//    @After
    public void unReg(){

        DoIt.unAllRegIdlingResource();
    }
    @Test
    public void testLogin() throws Exception {
        Log.e("jcd", "testLogin: "+ JSON.toJSONString(this.user));
        perform(aLogin.user() , replaceText(user));
        perform(aLogin.password() , replaceText(pwd));
        perform(aLogin.submit() , click());

        if ("失败".equals(expect)){
            onView(withText(getString(R.string.login))).inRoot(CustomMatcher.isToast()).check(matches(isDisplayed()));

        }else {
        DoIt.regIdlingResource(new SomeActivityIdlingResource(AName.MAIN,getInstrumentation().getContext(),true));

            if ("成功登录".equals(expect)){
                logout();  //退出登录

            }else if ("未开户".equals(expect)) {
                onWebView()
                        .withElement(eBrokerChoose.ibBrokerTitle())
                        .check(webMatches(getText(), containsString("Interactive Brokers")));

                logout();
            }
        }
        isSucceeded = true;
    }

    //@Test
    public void logts(){
        int a = 5/0;
        isSucceeded = true;
    }
    private void logout() throws InterruptedException {
        perform(aSettings.leftButton() , click(ViewActions.pressBack()));
        perform(aSettings.settingsButton() , click());
        perform(aSettings.logoutButton() , click());
    }


}
