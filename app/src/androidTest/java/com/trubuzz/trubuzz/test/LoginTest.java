package com.trubuzz.trubuzz.test;

import android.support.annotation.NonNull;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.data.AName;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.elements.ALogin.account;
import static com.trubuzz.trubuzz.elements.ALogin.password;
import static com.trubuzz.trubuzz.elements.ALogin.submit;
import static com.trubuzz.trubuzz.elements.ASettings.leftButton;
import static com.trubuzz.trubuzz.elements.ASettings.logoutButton;
import static com.trubuzz.trubuzz.elements.ASettings.settingsButton;
import static com.trubuzz.trubuzz.elements.EBrokerChoose.ibBrokerTitle;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.perform;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/8/24.
 */
@RunWith(Parameterized.class)
public class LoginTest extends BaseTest{

    @Parameterized.Parameter(0)
    public String user;
    @Parameterized.Parameter(1)
    public String pwd;
    @Parameterized.Parameter(2)
    public String expect;

//    public LoginTest(String user, String pwd , String expect) {
//        this.user = user;
//        this.pwd = pwd;
//        this.expect = expect;
//    }
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

    @Test
    public void testLogin() throws Exception {
        perform(account() , replaceText(user));
        perform(password() , replaceText(pwd));
        perform(submit() , click());

        if ("失败".equals(expect)){
//            check(loginToast(),matches(isDisplayed()));
            onView(withText("123")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                    .check(matches(isDisplayed()));
        }else {
            DoIt.regIdlingResource(new SomeActivityIdlingResource(AName.MAIN,getInstrumentation().getContext(),true));

            if ("成功登录".equals(expect)){
                logout();  //退出登录

            }else if ("未开户".equals(expect)) {
                onWebView()
                        .withElement(ibBrokerTitle())
                        .check(webMatches(getText(), containsString("Interactive Brokers")));

                logout();
            }
            DoIt.unAllRegIdlingResource();
        }
        succeeded();
    }

    private void logout() throws InterruptedException {
        perform(leftButton() , click(ViewActions.pressBack()));
        perform(settingsButton() , click());
        perform(logoutButton() , click());
    }


}
