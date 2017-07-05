package com.trubuzz.trubuzz.test.common;

import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Assert;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.trubuzz.trubuzz.constant.Env.instrumentation;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.thisString;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;

/**
 * Created by king on 17/6/9.
 */

public class CommonAction {
    private static final String TAG = "jcd_" + CommonAction.class.getSimpleName();

    /**
     * 检查给定的activity name 是否属于当前activity
     * @param activityName
     */
    public static void check_current_activity(String activityName ) {
        String currentAName = God.getCurrentActivityName(Env.instrumentation);
        Log.i(TAG, String.format("check_current_activity:  current activity name = %s", currentAName ));

        Assert.assertThat(currentAName ,thisString(activityName));
    }

    /**
     * 检查给定的 toast 是否可见
     * @param toastElement
     */
    public static void check_toast_msg(ToastElement toastElement) {
        given(toastElement).check(matches(isDisplayed()));
    }

    /**
     * 验证自动登录成功
     *      一般在找回密码 , 注册成功后会自动登录
     */
    public static void check_auto_login_successful(){
        DoIt.regIdlingResource(new ActivityIdlingResource(AName.MAIN, instrumentation.getContext(), true));
        given(GlobalView.radio_tray).check(matches(isDisplayed()));
        unRegIdlingResource();
    }
}
