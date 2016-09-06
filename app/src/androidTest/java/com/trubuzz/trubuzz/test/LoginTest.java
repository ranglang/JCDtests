package com.trubuzz.trubuzz.test;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;

import com.trubuzz.trubuzz.data.AName;
import com.trubuzz.trubuzz.elements.ELogin;
import com.trubuzz.trubuzz.feature.BaseTest;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.utils.Find;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.check;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.perform;

/**
 * Created by king on 2016/8/24.
 */
@RunWith(Parameterized.class)
public class LoginTest extends BaseTest{

    private ELogin eLogin = new ELogin();
    private String user;
    private String pwd;
    private String expect;

    public LoginTest(String user, String pwd , String expect) {
        this.user = user;
        this.pwd = pwd;
        this.expect = expect;
    }

    @Before
    public void setup(){
        lunchActivity();
    }

    @NonNull
    @Parameterized.Parameters
    public static Collection dLogin(){
        return Arrays.asList(new Object[][]{
                {"abc@abc.com","aA123321","无效的账号或密码"},
                {"zhao.deng@jucaidao.com","aA123456","成功登录"}
        });
    }
    @Test
    public void testLogin() throws InterruptedException {

        perform(eLogin.user() , replaceText(user));
        perform(eLogin.password() , replaceText(pwd));
        perform(eLogin.submit() , click());
        
        if ("成功登录".equals(expect)){
            SomeActivityIdlingResource ltr = new SomeActivityIdlingResource(AName.SplashActivity,this.getContext());
            Espresso.registerIdlingResources(ltr);
            check(eLogin.leftButton() , matches(isDisplayed()));
            Espresso.unregisterIdlingResources(ltr);
        }else{
            boolean res = Find.isToast(onView(withText(expect)),getMActivityTestRule());
            Assert.assertTrue(res);
        }
    }
}
