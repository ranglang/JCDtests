package com.trubuzz.trubuzz.idlingResource;

import android.support.test.espresso.IdlingResource;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by king on 16/12/12.
 */

public class ViewMatcherIdlingResource implements IdlingResource {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private ResourceCallback resourceCallback;
    private boolean isIdle;
    private final View view;
    private final Matcher<View> matcher;

    public ViewMatcherIdlingResource(View view, Matcher<View> matcher) {
        this.view = view;
        this.matcher = matcher;
    }

    @Override
    public String getName() {
        return this.getClass().getName() + " view : "+view.toString();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        isIdle = matcher.matches(view);
        Log.i(TAG, "isIdleNow: " + isIdle);
        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
