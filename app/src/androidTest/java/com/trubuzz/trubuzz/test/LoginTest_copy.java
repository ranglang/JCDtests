package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.shell.ActivityElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_account_or_pwd;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/8/24.
 */
@RunWith(JUnitParamsRunner.class)
public class LoginTest_copy extends BaseTest{

    @Rule
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    private ALogin login = new ALogin();


    private Object[] dLogin(){
        return new Object[]{
                    new Object[]{"abc@abc.com","aA123321",incorrect_account_or_pwd},
//                new Object[]{"abc@abc.com","123321","未开户"},
//                new Object[]{"zhao.deng@jucaidao.com","aA123456","成功登录"}
        };
    }

    @Test
    @Parameters(method = "dLogin")
    public void invalid_login(String user , String pwd , ActivityElement expect){
        DoIt.sleep(1000);
//        this.putData(new HashMap(){{
//            put("user" ,user);
//            put("pwd" ,pwd);
//            put("expect" ,expect);
//        }});

//        Wish.login(user,pwd);

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
//                        .withElement(ibBrokerTitle())
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
