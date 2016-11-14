package com.trubuzz.trubuzz.test;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_account_or_pwd;
import static com.trubuzz.trubuzz.elements.EBrokerChoose.ibBrokerTitle;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.test.Wish.logout;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.CoreMatchers.containsString;

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
    public ActivityElement expect;

    @Rule
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    private ALogin login = new ALogin();

    @NonNull
    @Parameterized.Parameters
    public static Collection dLogin(){
        return Arrays.asList(new Object[][]{
                    {"abc@abc.com","aA123321",incorrect_account_or_pwd},
//                {"abc@abc.com","123321","未开户"},
//                {"zhao.deng@jucaidao.com","aA123456","成功登录"}
        });
    }

//    @Test
    public void invalid_login() throws Exception {
        Wish.login(user,pwd);

        if ("失败".equals(expect)){
//            given(loginToast()).check(matches(isDisplayed()));
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
    }

    @Test
    public void cleanPwdTest(){
        given(login.pwd_input)
                .perform(replaceText("123456"));
        given(login.clean_pwd_image)
                .perform(click());
        given(login.pwd_input)
                .check(matches(withHint(getString("输入密码",input_password))));
    }

}
