package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ASignUp;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.elements.ALogin.signUp;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.email;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.emailPwd;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.emailPwdConfirm;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.emailReg;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.register;
import static com.trubuzz.trubuzz.elements.ASignUp.RegPhone.getSms;
import static com.trubuzz.trubuzz.elements.ASignUp.RegPhone.phoneNumber;
import static com.trubuzz.trubuzz.elements.ASignUp.RegPhone.phonePwd;
import static com.trubuzz.trubuzz.elements.ASignUp.RegPhone.phonePwdConfirm;
import static com.trubuzz.trubuzz.elements.ASignUp.RegPhone.phoneReg;
import static com.trubuzz.trubuzz.elements.ASignUp.captcha_edit;
import static com.trubuzz.trubuzz.elements.ASignUp.captcha_frame;
import static com.trubuzz.trubuzz.elements.ASignUp.captcha_ok;
import static com.trubuzz.trubuzz.elements.WithAny.getToast;
import static com.trubuzz.trubuzz.shell.Park.given;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/18.
 */
@RunWith(JUnitParamsRunner.class)
public class SignUpTest extends BaseTest{



    private Object[] emailSignUp(){
        return new Object[]{
            new Object[]{"espressoTest001@abc.com","aA123456","aA12345",true ,null ,"确认密码输入不一致"},
//            new Object[]{"espressoTest001@abc.com","aA123456","aA123456",true ,""  ,"验证码为六个数字"},
        };
    }
    private Object[] phoneSignUp(){
        return new Object[]{
            new Object[]{"11199990001","aA123456","aA12345",true ,null,"确认密码输入不一致"}
        };
    }

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Test
    public void signUpDefaultCheck(){
        given(signUp()).perform(click());                    //点击立即注册进入注册页面
        given(emailReg()).check(matches(isSelected()));       //检查"邮箱注册"默认被选中
        given(phoneReg())
                .check(matches(not(isSelected())))      //检查"手机注册"未选中
                .perform(click())                       //点击"手机注册"
                .check(matches(isSelected()));          //检查已被选中
        given(emailReg()).check(matches(not(isSelected()))); //检查"邮箱注册"处于未选中
        succeeded();
    }
    @Test
    @Parameters( method = "emailSignUp")
    public void invalid_sign_up_with_email(String emailAddress , String pwd , String pwdConfirm ,
                   boolean acceptTerms ,String captcha , String except){
        this.putData("email",emailAddress,"pwd",pwd,"pwdConfirm",pwdConfirm ,
                "acceptTerms",acceptTerms,"register_captcha",captcha,"except" ,except);

        given(signUp()).perform(click());                    //点击立即注册进入注册页面
        given(emailReg()).check(matches(isSelected()));       //检查"邮箱注册"默认被选中
        given(email())
                .perform(replaceText(emailAddress))
                .check(matches(withText(emailAddress)));       //输入邮箱地址并检查
        given(emailPwd()).perform(replaceText(pwd));                                                    //输入密码
        given(emailPwdConfirm()).perform(replaceText(pwdConfirm));                                   //确认密码

        Espresso.closeSoftKeyboard();                   //关闭软键盘

        if(acceptTerms) {                               //是否同意条款
            given(ASignUp.acceptCheck())
                    .perform(click())
                    .check(matches(isChecked()));
        }
        given(register()).perform(click());                  //点击"注册"

        if(captcha != null){                            //如果预计需输入验证码,则输入
            given( captcha_frame() ).check(matches(isDisplayed()));
            given( captcha_edit()).perform(replaceText(captcha));
            given( captcha_ok()).perform(click());
        }
        given( getToast(except , matr)).check(matches(isDisplayed()));              //检查预期结果

        succeeded();
    }

    @Test
    @Parameters(method = "phoneSignUp")
    public void invalid_sign_up_with_phone(String phoneNumber ,String pwd , String pwdConfirm ,
                                           boolean acceptTerms ,String captcha , String except){
        putData(new HashMap<String,Object>(){{
            put("phoneNumber",phoneNumber);
            put("pwd",pwd);
            put("acceptTerms",acceptTerms);
            put("captcha",captcha);
            put("except",except);
        }});

        given(signUp()).perform(click());                    //点击立即注册进入注册页面
        given( phoneReg())
                .perform(click())
                .check(matches(isSelected()));              //点击并检查选中
        given( phoneNumber())
                .perform(replaceText(phoneNumber))
                .check(matches(withText(phoneNumber)));
        given( phonePwd()).perform(replaceText(pwd));
        given( phonePwdConfirm()).perform(replaceText(pwdConfirm));
        Espresso.closeSoftKeyboard();                   //关闭软键盘

        if(acceptTerms) {                               //是否同意条款
            given(ASignUp.acceptCheck())
                    .perform(click())
                    .check(matches(isChecked()));        //选中同意服务条款
        }
        given( getSms()).perform(click());                   //点击检查"获取验证码"
        given( getToast(except , matr)).check(matches(isDisplayed()));

        this.succeeded();

    }
}
