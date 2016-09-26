package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

import com.trubuzz.trubuzz.utils.God;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.hasLinks;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        private static ViewInteraction s_acceptTerms = null;

        private static final String ID_EMAIL_TEXT = "text1";
        private static final String[] ID_HINT_EMAIL = {"email", "请输入您的邮箱地址"};
        private static final String[] ID_HINT_PWD = {"password", "请输入密码"};
        private static final String[] ID_HINT_PWDC = {"confirm", "请再次输入密码"};
        private static final String ID_ACCEPT = "accept";
        private static final String[] ID_TEXT_TERMS = {"service", "服务条款"};
        private static final String[] ID_TEXT_REG = {"submit", "注册"};
        private static final String TEXT_ACCEPT = "请阅读并勾选同意服务条款以注册账号";


        public static ViewInteraction emailReg() {
            if (v_emailReg == null) {
                v_emailReg = onView(withChild(withResourceName(ID_EMAIL_TEXT)));
            }
            return v_emailReg;
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
            return God.getString(TEXT_ACCEPT, com.trubuzz.trubuzz.test.R.string.accept_terms_of_service_hint);
        }
    }

    public static class RegPhone{


    }
}
