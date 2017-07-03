package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.login.LoginView;
import com.trubuzz.trubuzz.elements.ASignUp;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static com.trubuzz.trubuzz.constant.ToastInfo.captcha_format_error_toast;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_password_confirm_toast;
import static com.trubuzz.trubuzz.constant.ToastInfo.sms_sent_toast;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.thisObject;
import static com.trubuzz.trubuzz.feature.custom.assertors.CustomWebAssert.customWebMatches;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.terms_content;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private LoginView lv = new LoginView();


    private Object[] emailSignUp(){
        return new Object[]{
            new Object[]{"espressoTest001abc.com","aA123456","aA12345",true ,null ,incorrect_password_confirm_toast},
            new Object[]{"espressoTest001@abc.com","aA123456","aA123456",true ,""  ,captcha_format_error_toast},
        };
    }
    private Object[] phoneSignUp(){
        return new Object[]{
            new Object[]{"11199990001","aA123456","aA12345",true ,null ,incorrect_password_confirm_toast ,true ,null ,null,null}
        };
    }

    @Before
    public void intoSignUp(){
        given(lv.sign_up_link).perform(click());                    //点击立即注册进入注册页面
    }

    @Test
    public void signUpDefaultCheck(){
        given(regEmail.use_email_reg).check(matches(isSelected()));       //检查"邮箱注册"默认被选中
        given(regPhone.use_phone_reg)
                .check(matches(not(isSelected())))      //检查"手机注册"未选中
                .perform(click())                       //点击"手机注册"
                .check(matches(isSelected()));          //检查已被选中
        given(regEmail.use_email_reg).check(matches(not(isSelected()))); //检查"邮箱注册"处于未选中
    }
    @Test
    @Parameters( method = "emailSignUp")
    public void invalid_sign_up_with_email(@Var("emailAddress")String emailAddress , @Var("pwd") String pwd ,
                                           @Var("pwdConfirm") String pwdConfirm , @Var("acceptTerms") boolean acceptTerms ,
                                           @Var("imageCaptcha") String imageCaptcha , @Var("expect") Element expect){

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

        if(imageCaptcha != null){                            //如果预计需输入验证码,则输入
            given(regEmail.image_captcha_frame).check(matches(isDisplayed()));
            given(regEmail.image_captcha_input).perform(replaceText(imageCaptcha));     /* 未启用字符位数截取 */
            given(regEmail.image_captcha_ok_button).perform(click());
        }
        given( expect).check(matches(isDisplayed()));              //检查预期结果

    }

//    @Test
//    @Parameters(method = "phoneSignUp")
    public void invalid_sign_up_with_phone(@Var("phoneNumber") String phoneNumber ,@Var("pwd") String pwd ,
                                           @Var("pwdConfirm") String pwdConfirm , @Var("acceptTerms") boolean acceptTerms ,
                                           @Var("imageCaptcha") String imageCaptcha ,@Var("getSmsExpect")Element getSmsExpect ,
                                           @Var("confirmImageCaptcha")boolean confirmImageCaptcha ,
                                           @Var("enteredImageCaptchaExpect")Element enteredImageCaptchaExpect ,
                                           @Var("phoneCaptcha")String phoneCaptcha, @Var("expect") Element expect ){

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
        //若获取验证码时就异常 , 直接退出跳过后续操作 , 反之则继续
        if(getSmsExpect != null) {
            given(getSmsExpect).check(matches(isDisplayed()));      //检查获取验证码时的提示
            given(regPhone.image_captcha_frame).check(matches(not(isDisplayed())));
            return;
        }
        given(regPhone.image_captcha_frame).check(matches(isDisplayed()));  //检查出现图形验证码输入框
        if(imageCaptcha != null) {                           //无图形验证码则略过后续步骤
            given(regPhone.sms_captcha_input).perform(replaceText(imageCaptcha))
                    .check(matches(withText(imageCaptcha)));        /* 未启用字符位数截取 */
        }
        if(confirmImageCaptcha)
            given(regPhone.image_captcha_ok_button).perform(click());
        else
            given(regPhone.image_captcha_cancel_button).perform(click());

        if(enteredImageCaptchaExpect != null)       //不为null后 验证toast提示消息
            given(enteredImageCaptchaExpect).check(matches(isDisplayed()));

        if(sms_sent_toast.equals(enteredImageCaptchaExpect)) {      //验证短信验证码已发送后的界面元素展示
            given(regPhone.sms_captcha_input).check(matches(isDisplayed()));
            given(regPhone.phone_reg_submit).check(matches(isDisplayed()));
            Wish.allNotEnabled(regPhone.pickup_country_code_button , regPhone.country_code_input ,
                    regPhone.phone_input , regPhone.phone_reg_pwd , regPhone.phone_reg_pwd_confirm ,
                    regPhone.phone_accept_service_check);           //检查已禁用部分元素
        }else{
            Wish.allNotDisplayed(regPhone.sms_captcha_input , regPhone.phone_reg_submit);
            return;
        }

        if(phoneCaptcha != null)
            given(regPhone.sms_captcha_input).perform(replaceText(phoneCaptcha))
                    .check(matches(withText(phoneCaptcha)));        /* 未启用字符位数截取 */

        given(regPhone.phone_reg_submit).perform(click());

        if(expect != null)                                      //没有预期toast则登录成功
            given(expect).check(matches(isDisplayed()));
        else
            assertThat(Wish.isLogin() , thisObject(true));      //期望可是自动登录成功

    }

//    @Test
    public void terms_content_test(){
        given(regEmail.email_terms).perform(click());
        DoIt.regIdlingResource(new ActivityIdlingResource(WEB_VIEW,this.matr.getActivity() ,true));
        onWebView()
                .withElement(findElement(Locator.CSS_SELECTOR , ".terms"))
                .check(customWebMatches(getText() , containsString(getString(terms_content))));
        DoIt.unRegIdlingResource();
    }
}
