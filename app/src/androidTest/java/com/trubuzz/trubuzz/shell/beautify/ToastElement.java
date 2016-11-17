package com.trubuzz.trubuzz.shell.beautify;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.utils.God;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.utils.DoIt.notEmpty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 16/11/16.
 */

public class ToastElement implements Element<ViewInteraction>{

    private String toastMsg;
    private ActivityTestRule activity;

    public ToastElement(String toastMsg, ActivityTestRule activity) {
        this.toastMsg = toastMsg;
        this.activity = activity;
    }
    public ToastElement(String toastMsg) {
        this.toastMsg = toastMsg;
    }
    public ToastElement(){}

    public ToastElement setToastMsg(String toastMsg) {
        this.toastMsg = toastMsg;
        return this;
    }

    public ToastElement setActivity(ActivityTestRule activity) {
        this.activity = activity;
        return this;
    }

    @Override
    public String toString() {
        String string = "{";
        if(notEmpty(toastMsg))   string += "toastMsg='" + toastMsg + "', ";
        if(notEmpty(activity))  string += "activity='" + activity + "', ";
        return string += '}';
    }

    @Override
    public ViewInteraction interactionWay() {
        if(notEmpty(toastMsg)){
            if(notEmpty(activity)){
                return onView(withText(toastMsg))
                        .inRoot(withDecorView(not(is(activity.getActivity().getWindow().getDecorView()))));
            }else{
                return onView(withText(toastMsg))
                        .inRoot(withDecorView(not(is(God.getCurrentActivity(Env.instrumentation).getWindow().getDecorView()))));
            }
        }
        return null;
    }
}
