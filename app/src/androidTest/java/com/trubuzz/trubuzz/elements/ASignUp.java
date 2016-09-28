package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import com.trubuzz.trubuzz.utils.God;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.utils.DoIt.init;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 2016/9/18.
 */
public class ASignUp {

    public static class RegEmail {
        private static ViewInteraction v_emailReg = null;
        private static ViewInteraction v_email = null;
        private static ViewInteraction v_pwd = null;
        private static ViewInteraction v_pwdConfirm = null;
        private static ViewInteraction v_acceptCheck = null;
        private static ViewInteraction v_terms = null;
        private static ViewInteraction v_register = null;
        private static String s_acceptTerms = null;

        private static final String[] ID_TEXT_SignUpEmail = {"text1" , "邮箱注册"};
        private static final String[] ID_HINT_EMAIL = {"email", "请输入您的邮箱地址"};
        private static final String[] ID_HINT_PWD = {"password", "请输入密码"};
        private static final String[] ID_HINT_PWDC = {"confirm", "请再次输入密码"};
        private static final String ID_ACCEPT = "accept";
        private static final String[] ID_TEXT_TERMS = {"service", "服务条款"};
        private static final String[] ID_TEXT_REG = {"submit", "注册"};
        private static final String TEXT_ACCEPT = "请阅读并勾选同意服务条款以注册账号";

        /**
         * 邮箱注册图标及文字
         * @return
         */
        public static ViewInteraction emailReg() {
            return v_emailReg = v_emailReg == null
                    ? onView(allOf(
                        withChild(
                                allOf(withText(God.getString(ID_TEXT_SignUpEmail[1], com.trubuzz.trubuzz.test.R.string.sign_up_email)),
                                    withResourceName(ID_TEXT_SignUpEmail[0]))),
                        isDisplayed()
                    ))
                    : v_emailReg;
        }

        public static ViewInteraction email() {
            if (v_email == null) {
                v_email = onView(
                        allOf(
                                withResourceName(ID_HINT_EMAIL[0]),
                                withHint(God.getString(ID_HINT_EMAIL[1], com.trubuzz.trubuzz.test.R.string.sign_up_email_hint))
                        )
                );
            }
            return v_email;
        }

        public static ViewInteraction password() {
            if (v_pwd == null) {
                v_pwd = onView(
                        allOf(
                                withResourceName(ID_HINT_PWD[0]),
                                withHint(God.getString(ID_HINT_PWD[1], com.trubuzz.trubuzz.test.R.string.sign_up_password_hint))
                        )
                );
            }
            return v_pwd;
        }

        public static ViewInteraction pwdConfirm() {
            if (v_pwdConfirm == null) {
                v_pwdConfirm = onView(
                        allOf(
                                withResourceName(ID_HINT_PWDC[0]),
                                withHint(God.getString(ID_HINT_PWDC[1], com.trubuzz.trubuzz.test.R.string.sign_up_confirm_hint))
                        )
                );
            }
            return v_pwdConfirm;
        }

        public static ViewInteraction acceptCheck() {
            if (v_acceptCheck == null) {
                v_acceptCheck = onView(
                        allOf(
                                withResourceName(ID_ACCEPT),
                                isNotChecked()
                        )
                );
            }
            return v_acceptCheck;
        }

        public static ViewInteraction terms() {
            if (v_terms == null) {
                v_terms = onView(
                        allOf(
                                withResourceName(ID_TEXT_TERMS[0]),
                                withText(God.getString(ID_TEXT_TERMS[1], com.trubuzz.trubuzz.test.R.string.terms_of_service)),
                                hasLinks()
                        )
                );
            }
            return v_terms;
        }

        public static ViewInteraction register() {
            if (v_register == null) {
                v_register = onView(
                        allOf(
                                withResourceName(ID_TEXT_REG[0]),
                                withText(God.getString(ID_TEXT_REG[1], com.trubuzz.trubuzz.test.R.string.sign_up))
                        )
                );
            }
            return v_register;
        }

        public static String acceptTerms() {
            if (s_acceptTerms == null){
                s_acceptTerms = God.getString(TEXT_ACCEPT, com.trubuzz.trubuzz.test.R.string.accept_terms_of_service_hint);
            }
            return s_acceptTerms;
        }
    }

    public static class RegPhone{
        private static ViewInteraction v_phoneReg ;
        private static ViewInteraction v_pickupCountryCode ;
        private static ViewInteraction v_country_code ;

        private static final String[] ID_TEXT_PhoneReg = {"text1" , "手机注册"};
        private static final String[] ID_TEXT_pickup_country_code ={ "pickup_country_code" , "挑选"};
        private static final String[] ID_TEXT_country_code ={ "country_code" , "86"};

        /**
         * "手机注册"图标
         * @return
         */
        public static ViewInteraction phoneReg(){
            return init(v_phoneReg , onView(withChild(allOf(
                    withText(God.getString(ID_TEXT_PhoneReg[1] , com.trubuzz.trubuzz.test.R.string.sign_up_phone)),
                    withResourceName(ID_TEXT_PhoneReg[0]))
            )));
        }

        /**
         * 挑选国别码
         * @return
         */
        public static ViewInteraction pickupCode(){
            return init(v_pickupCountryCode ,
                    onView(allOf(
                            withResourceName(ID_TEXT_pickup_country_code[0]),
                            withText(God.getString(ID_TEXT_pickup_country_code[1],com.trubuzz.trubuzz.test.R.string.pickup))
                    )));
        }

        /**
         * 国别码编辑框
         * @return
         */
        public static ViewInteraction editCountryCode(){
            return init(v_country_code ,
                    onView(allOf(
                            withResourceName(ID_TEXT_country_code[0]),
                            withText(ID_TEXT_country_code[1])
                    )));
        }
    }
}
