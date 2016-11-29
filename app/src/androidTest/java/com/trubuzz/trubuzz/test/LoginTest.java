package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.elements.EBrokerChoose;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.shell.beautify.AtomElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_account_or_pwd_toast;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by king on 2016/8/24.
 * at 11/18 真机测试跑通
 */
@RunWith(JUnitParamsRunner.class)
public class LoginTest extends BaseTest{

    @Rule
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    private ALogin login = new ALogin();


    private Object[] dLogin(){
        return new Object[]{
                    new Object[]{"abc@abc.com","aA123321", incorrect_account_or_pwd_toast},
//                new Object[]{"abc@abc.com","123321","未开户"},
//                new Object[]{"zhao.deng@jucaidao.com","aA123456","成功登录"}
        };
    }

    @Test
    @Parameters(method = "dLogin")
    public void login(@Var("user") String user , @Var("pwd") String pwd ,
                              @Var("expect") Element expect ){
        Wish.wantNotLogin();
        Wish.login(user,pwd);

        given(expect).check(matches(isDisplayed()));

        if(expect instanceof AtomElement){
            DoIt.regIdlingResource(new SomeActivityIdlingResource(AName.MAIN,getInstrumentation().getContext(),true));
            webGiven()
                    .withElement(expect)
                    .check(webMatches(getText(), containsString(EBrokerChoose.ib_broker_title_text)));

            DoIt.unAllRegIdlingResource();
        }

        Wish.wantNotLogin();

//        if ("失败".equals(expect)){
//            given(expect).check(matches(isDisplayed()));
//        }else {
//            DoIt.regIdlingResource(new SomeActivityIdlingResource(AName.MAIN,getInstrumentation().getContext(),true));
//
//            if ("成功登录".equals(expect)){
//                logout();  //退出登录
//
//            }else if ("未开户".equals(expect)) {
//                onWebView()
//                        .withElement(ib_broker_title)
//                        .check(webMatches(getText(), containsString("Interactive Brokers")));
//
//                logout();
//            }
//            DoIt.unAllRegIdlingResource();
//        }
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
