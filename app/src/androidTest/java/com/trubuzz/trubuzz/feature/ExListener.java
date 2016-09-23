package com.trubuzz.trubuzz.feature;

import android.util.Log;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Created by king on 2016/9/22.
 */
@Deprecated
public class ExListener extends RunListener {
    private final String TAG = "jcd_ExListener";

    public void testRunStarted(Description description) throws Exception {
        Log.i(TAG, "testRunStarted: "+description.testCount());
    }

    public void testRunFinished(Result result) throws Exception {
        Log.i(TAG, "testRunFinished: "+result.getRunCount());
    }

    public void testStarted(Description description) throws Exception {
        Log.i(TAG, "testStarted: "+description.getMethodName());
    }

    public void testFinished(Description description) throws Exception {
        Log.i(TAG, "testFinished: "+description.getMethodName());
    }

    public void testFailure(Failure failure) throws Exception {
        Log.e(TAG, "testFailure: "+failure.getDescription().getMethodName() );
    }

    public void testAssumptionFailure(Failure failure) {
        Log.e(TAG, "testAssumptionFailure: "+failure.getDescription().getMethodName() );
    }

    public void testIgnored(Description description) throws Exception {
        Log.i(TAG, "testIgnored: "+description.getMethodName());
    }
}
