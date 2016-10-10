package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.data.AName;
import com.trubuzz.trubuzz.elements.ASignUp;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.elements.ALogin.signUp;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.email;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.emailReg;
import static com.trubuzz.trubuzz.elements.ASignUp.RegEmail.register;
import static com.trubuzz.trubuzz.elements.ASignUp.RegPhone.phoneReg;
import static com.trubuzz.trubuzz.elements.ASignUp.incorrect_pwd_confirm_toast;
import static com.trubuzz.trubuzz.elements.ASignUp.password;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.check;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.perform;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/18.
 */
@RunWith(JUnitParamsRunner.class)
public class SignUp extends BaseTest{



    private Object[] emailSignUp(){
        return new Object[]{
            new Object[]{"espressoTest001@abc.com","aA123456","aA12345",incorrect_pwd_confirm_toast() ,"确认密码输入不一致"},
        };
    }

    @Rule
    public ActivityTestRule<?> mActivityTestRule = new ActivityTestRule(God.getFixedClass(AName.MAIN));

//    @Test
    public void signUpDefaultCheck(){
        perform(signUp() , click());                    //点击立即注册进入注册页面
        check(emailReg(), matches(isSelected()));       //检查"邮箱注册"默认被选中
        check(phoneReg() , matches(not(isSelected())))  //检查"手机注册"未选中
                .perform(click())                       //点击"手机注册"
                .check(matches(isSelected()));          //检查已被选中
        check(emailReg() , matches(not(isSelected()))); //检查"邮箱注册"处于未选中
    }
    @Test
    @Parameters( method = "emailSignUp")
    public void sign_up_with_email(String emailAddress , String pwd , String pwdConfirm , ViewInteraction except , String desc){
        perform(signUp() , click());                    //点击立即注册进入注册页面
        check(emailReg(), matches(isSelected()));       //检查"邮箱注册"默认被选中
        perform(email() , typeText(emailAddress)).check(matches(withText(emailAddress)));
        perform(password() , typeText(pwd));
        perform(ASignUp.pwdConfirm() , typeText(pwdConfirm));

        Espresso.closeSoftKeyboard();                   //关闭软键盘

        perform(ASignUp.acceptCheck() , click()).check(matches(isChecked()));
        perform(register() , click());
        check(except , matches(isDisplayed()));
    }
}
