package com.trubuzz.trubuzz.test.common;

import android.util.Log;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Assert;

import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.thisString;

/**
 * Created by king on 17/6/9.
 */

public class CommonAction {
    private static final String TAG = "jcd_" + CommonAction.class.getSimpleName();

    public static void check_current_activity(String activityName ) {
        String currentAName = God.getCurrentActivityName(Env.instrumentation);
        Log.i(TAG, String.format("check_current_activity:  current activity name = %s", currentAName ));

        Assert.assertThat(currentAName ,thisString(activityName));
    }
}
