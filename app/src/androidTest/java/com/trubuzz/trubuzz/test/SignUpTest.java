package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ALogin;
import com.trubuzz.trubuzz.elements.ASignUp;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
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
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.AName.WEB_VIEW;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_password_confirm_toast;
import static com.trubuzz.trubuzz.feature.custom.CustomWebAssert.customWebMatches;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.terms_content;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/18.
 */
@RunWith(JUnitParamsRunner.class)
public class SignUpTest extends BaseTest{

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));
    private ASignUp.RegEmail regEmail = new ASignUp.RegEmail();
    private ASignUp.RegPhone regPhone = new ASignUp.RegPhone();


    private Object[] emailSignUp(){
        return new Object[]{
            new Object[]{"espressoTest001@abc.com","aA123456","aA12345",true ,null ,incorrect_password_confirm_toast},
//            new Object[]{"espressoTest001@abc.com","aA123456","aA123456",true ,""  ,"验证码为六个数字"},
        };
    }
    private Object[] phoneSignUp(){
        return new Object[]{
            new Object[]{"11199990001","aA123456","aA12345",true ,null,incorrect_password_confirm_toast}
        };
    }

    @Before
    public void intoSignUp(){
        given(ALogin.sign_up_link).perform(click());                    //点击立即注册进入注册页面
    }

    @Test
    public void signUpDefaultCheck(){
        given(regEmail.use_email_reg).check(matches(isSelected()));       //检查"邮箱注册"默认被选中
        given(regPhone.use_phone_reg)
                .check(matches(not(isSelected())))      //检查"手机注册"未选中
                .perform(click())                       //点击"手机注册"
                .check(matches(isSelected()));          //检查已被选中
        given(regEmail.use_email_reg).check(matches(not(isSelected()))); //检查"邮箱注册"处于未选中
        succeeded();
    }
    @Test
    @Parameters( method = "emailSignUp")
    public void invalid_sign_up_with_email(String emailAddress , String pwd , String pwdConfirm ,
                   boolean acceptTerms ,String captcha , Element except){
        this.putData("email",emailAddress,"pwd",pwd,"pwdConfirm",pwdConfirm ,
                "acceptTerms",acceptTerms,"register_captcha",captcha,"except" ,except);

        given(regEmail.use_email_reg).check(matches(isSelected()));       //检查"邮箱注册"默认被选中
        given(regEmail.email_input)
                .perform(replaceText(emailAddress))
                .check(matches(withText(emailAddress)));       //输入邮箱地址并检查
        given(regEmail.email_reg_pwd).perform(replaceText(pwd));                                                    //输入密码
        given(regEmail.email_reg_pwd_confirm).perform(replaceText(pwdConfirm));                                   //确认密码

        Espresso.closeSoftKeyboard();                   //关闭软键盘

        if(acceptTerms) {                               //是否同意条款
            given(regEmail.email_accept_service_check)
                    .perform(click())
                    .check(matches(isChecked()));
        }
        given(regEmail.email_reg_submit).perform(click());                  //点击"注册"

        if(captcha != null){                            //如果预计需输入验证码,则输入
            given(regEmail.captcha_frame).check(matches(isDisplayed()));
            given(regEmail.captcha_input).perform(replaceText(captcha));
            given(regEmail.captcha_ok_button).perform(click());
        }
        given( except).check(matches(isDisplayed()));              //检查预期结果

        succeeded();
    }

    @Test
    @Parameters(method = "phoneSignUp")
    public void invalid_sign_up_with_phone(String phoneNumber ,String pwd , String pwdConfirm ,
                                           boolean acceptTerms ,String captcha , Element except){
        putData(new HashMap<String,Object>(){{
            put("phoneNumber",phoneNumber);
            put("pwd",pwd);
            put("acceptTerms",acceptTerms);
            put("captcha",captcha);
            put("except",except);
        }});

        given(regPhone.use_phone_reg)
                .perform(click())
                .check(matches(isSelected()));              //点击并检查选中
        given(regPhone.phone_input)
                .perform(replaceText(phoneNumber))
                .check(matches(withText(phoneNumber)));
        given(regPhone.phone_reg_pwd).perform(replaceText(pwd));
        given(regPhone.phone_reg_pwd_confirm).perform(replaceText(pwdConfirm));
        Espresso.closeSoftKeyboard();                   //关闭软键盘

        if(acceptTerms) {                               //是否同意条款
            given(regPhone.phone_accept_service_check)
                    .perform(click())
                    .check(matches(isChecked()));        //选中同意服务条款
        }
        given(regPhone.get_sms_button).perform(click());                   //点击检查"获取验证码"
        given(except).check(matches(isDisplayed()));

        this.succeeded();

    }

//    @Test
    public void terms_content_test(){
        given(regEmail.email_terms).perform(click());
        DoIt.regIdlingResource(new SomeActivityIdlingResource(WEB_VIEW,this.matr.getActivity() ,true));
        onWebView()
                .withElement(findElement(Locator.CSS_SELECTOR , ".terms"))
                .check(customWebMatches(getText() , containsString(getString(terms_content))));
        DoIt.unRegIdlingResource();
    }
}
