package com.trubuzz.trubuzz.test.common;

import android.util.Log;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Assert;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.thisString;
import static com.trubuzz.trubuzz.shell.Park.given;

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
}
