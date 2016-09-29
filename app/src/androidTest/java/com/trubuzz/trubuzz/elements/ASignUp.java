package com.trubuzz.trubuzz.elements;

import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.elements.WithAny.getToast;
import static com.trubuzz.trubuzz.test.R.string.accept_terms_of_service_hint;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_confirm;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_phone_format;
import static com.trubuzz.trubuzz.test.R.string.input_captcha;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 2016/9/18.
 */
public class ASignUp {

    private static final String[] ID_HINT_PWD = {"password", "请输入密码"};
    private static final String[] ID_HINT_PWDC = {"confirm", "请再次输入密码"};
    private static final String ID_ACCEPT = "accept";
    private static final String[] ID_TEXT_TERMS = {"service", "服务条款"};
    private static final String TEXT_ACCEPT = getString("请阅读并勾选同意服务条款以注册账号", accept_terms_of_service_hint);
    private static final String TEXT_incorrect_pwd_format = getString("请输入6–16字符的大小写字母和数字组合", incorrect_password_format);
    private static final String TEXT_incorrect_pwd_confirm = getString("确认密码输入不一致", incorrect_password_confirm);
    private static final String[] ID_TEXT_captcha_frame_title = {"title", getString("请输入验证码",input_captcha)};

    public static ViewInteraction captcha_frame(){
        return onView(withChild(allOf(
                withResourceName(ID_TEXT_captcha_frame_title[0]),
                withText(ID_TEXT_captcha_frame_title[1])
        )));
    }
    @NonNull
    public static ViewInteraction incorrect_pwd_confirm_toast(){
        return getToast(TEXT_incorrect_pwd_confirm);
    }
    public static ViewInteraction incorrect_pwd_format_toast(){
        return getToast(TEXT_incorrect_pwd_format);
    }
    public static ViewInteraction password() {
       return onView(allOf(
                        withResourceName(ID_HINT_PWD[0]),
                        withHint(getString(ID_HINT_PWD[1], com.trubuzz.trubuzz.test.R.string.sign_up_password_hint))
                ));
    }

    public static ViewInteraction pwdConfirm() {
        return onView(
                allOf(
                        withResourceName(ID_HINT_PWDC[0]),
                        withHint(getString(ID_HINT_PWDC[1], com.trubuzz.trubuzz.test.R.string.sign_up_confirm_hint))
                )
        );
    }

    public static ViewInteraction acceptCheck() {
       return onView(
                allOf(
                        withResourceName(ID_ACCEPT),
                        isNotChecked()
                )
        );
    }

    public static ViewInteraction terms() {
        return onView(
                allOf(
                        withResourceName(ID_TEXT_TERMS[0]),
                        withText(getString(ID_TEXT_TERMS[1], com.trubuzz.trubuzz.test.R.string.terms_of_service)),
                        hasLinks()
                )
        );
    }

    public static ViewInteraction acceptTerms() {
        return getToast(TEXT_ACCEPT);
    }



    public static class RegEmail {

        private static final String[] ID_TEXT_SignUpEmail = {"text1" , "邮箱注册"};
        private static final String[] ID_HINT_EMAIL = {"email", "请输入您的邮箱地址"};
        private static final String[] ID_TEXT_REG = {"submit", "注册"};


        /**
         * 邮箱注册图标及文字
         * @return
         */
        public static ViewInteraction emailReg() {
            return onView(allOf(
                        withChild(
                                allOf(withText(getString(ID_TEXT_SignUpEmail[1], com.trubuzz.trubuzz.test.R.string.sign_up_email)),
                                    withResourceName(ID_TEXT_SignUpEmail[0]))),
                        isDisplayed()
                    ));
        }

        public static ViewInteraction email() {
            return onView(
                    allOf(
                            withResourceName(ID_HINT_EMAIL[0]),
                            withHint(getString(ID_HINT_EMAIL[1], com.trubuzz.trubuzz.test.R.string.sign_up_email_hint))
                    )
            );
        }

        public static ViewInteraction register() {
            return onView(
                    allOf(
                            withResourceName(ID_TEXT_REG[0]),
                            withText(getString(ID_TEXT_REG[1], com.trubuzz.trubuzz.test.R.string.sign_up))
                    )
            );
        }


    }

    public static class RegPhone{

        private static final String[] ID_TEXT_PhoneReg = {"text1" , "手机注册"};
        private static final String[] ID_TEXT_pickup_country_code ={ "pickup_country_code" , "挑选"};
        private static final String[] ID_TEXT_country_code ={ "country_code" , "86"};
        private static final String[] ID_HINT_phone ={ "phone" , "请输入手机号"};
        private static final String[] ID_TEXT_get_sms ={ "btn_sms" , "获取验证码"};
        private static final String TEXT_incorrect_phone_format = getString("手机号格式不正确", incorrect_phone_format);


        /**
         * "手机注册"图标
         * @return
         */
        public static ViewInteraction phoneReg(){
            return  onView(withChild(allOf(
                    withText(getString(ID_TEXT_PhoneReg[1] , com.trubuzz.trubuzz.test.R.string.sign_up_phone)),
                    withResourceName(ID_TEXT_PhoneReg[0]))
            ));
        }

        /**
         * 挑选国别码
         * @return
         */
        public static ViewInteraction pickupCode(){
            return onView(allOf(
                            withResourceName(ID_TEXT_pickup_country_code[0]),
                            withText(getString(ID_TEXT_pickup_country_code[1],com.trubuzz.trubuzz.test.R.string.pickup))
                    ));
        }

        /**
         * 国别码编辑框
         * @return
         */
        public static ViewInteraction editCountryCode(){
            return onView(allOf(
                            withResourceName(ID_TEXT_country_code[0]),
                            withText(ID_TEXT_country_code[1])
                    ));
        }

        /**
         * 手机号输入框
         * @return
         */
        public static ViewInteraction phoneNumber(){
            return onView(allOf(
                            withResourceName(ID_HINT_phone[0]),
                            withHint(getString(ID_HINT_phone[1], com.trubuzz.trubuzz.test.R.string.sign_up_phone_hint))
                    ));
        }

        /**
         * 获取验证码
         * @return
         */
        public static ViewInteraction getSms(){
            return onView(allOf(
                            withResourceName(ID_TEXT_get_sms[0]),
                            withHint(getString(ID_TEXT_get_sms[1], com.trubuzz.trubuzz.test.R.string.get_sms))
                    ));
        }

        /**
         * 手机号格式不正确 提示
         * @return
         */
        public static ViewInteraction incorrect_phone_format_toast(){
            return getToast(TEXT_incorrect_phone_format);
        }
    }
}
