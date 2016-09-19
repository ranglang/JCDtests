package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;

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
    private ViewInteraction v_emailReg = null ;
    private ViewInteraction v_email = null ;
    private ViewInteraction v_pwd = null ;
    private ViewInteraction v_pwdConfirm = null ;
    private ViewInteraction v_acceptCheck = null ;
    private ViewInteraction v_terms = null ;
    private ViewInteraction v_register = null ;

    private final String ID_EMAIL_TEXT = "text1";
    private final String[] ID_HINT_EMAIL ={ "email","请输入您的邮箱地址"};
    private final String[] ID_HINT_PWD ={ "password","请输入密码"};
    private final String[] ID_HINT_PWDC ={ "confirm","请再次输入密码"};
    private final String ID_ACCEPT = "accept";
    private final String[] ID_TEXT_TERMS ={ "service","服务条款"};
    private final String[] ID_TEXT_REG ={ "submit","注册"};


    public ViewInteraction emailReg() {
        if (v_emailReg == null){
            this.v_emailReg = onView(withChild(withResourceName(ID_EMAIL_TEXT)));
        }
        return v_emailReg;
    }

    public ViewInteraction email() {
        if (v_email == null){
            this.v_email = onView(
                    allOf(
                            withResourceName(ID_HINT_EMAIL[0]),
                            withHint(ID_HINT_EMAIL[1])
                )
            );
        }
        return v_email;
    }
    public ViewInteraction password() {
        if (v_pwd == null){
            this.v_pwd = onView(
                    allOf(
                            withResourceName(ID_HINT_PWD[0]),
                            withHint(ID_HINT_PWD[1])
                    )
            );
        }
        return v_pwd;
    }
    public ViewInteraction pwdConfirm() {
        if (v_pwdConfirm == null){
            this.v_pwdConfirm = onView(
                    allOf(
                            withResourceName(ID_HINT_PWDC[0]),
                            withHint(ID_HINT_PWDC[1])
                    )
            );
        }
        return v_pwdConfirm;
    }
    public ViewInteraction acceptCheck() {
        if (v_acceptCheck == null){
            this.v_acceptCheck = onView(
                    allOf(
                            withResourceName(ID_ACCEPT),
                            isNotChecked()
                    )
            );
        }
        return v_acceptCheck;
    }

    public ViewInteraction terms() {
        if (v_terms == null){
            this.v_terms = onView(
                    allOf(
                            withResourceName(ID_TEXT_TERMS[0]),
                            withText(ID_TEXT_TERMS[1]),
                            hasLinks()
                    )
            );
        }
        return v_terms;
    }
    public ViewInteraction register() {
        if (v_register == null){
            this.v_register = onView(
                    allOf(
                            withResourceName(ID_TEXT_REG[0]),
                            withText(ID_TEXT_REG[1])
                    )
            );
        }
        return v_register;
    }
}
