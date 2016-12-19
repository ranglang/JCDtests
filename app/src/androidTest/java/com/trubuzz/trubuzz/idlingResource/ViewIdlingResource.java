package com.trubuzz.trubuzz.idlingResource;

import android.support.test.espresso.IdlingResource;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.utils.Judge;

/**
 * Created by king on 16/12/12.
 */

public class ViewIdlingResource implements IdlingResource {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private ResourceCallback resourceCallback;
    private boolean isIdle;
    private final View view;

    public ViewIdlingResource(View view) {
        this.view = view;
    }

    @Override
    public String getName() {
        return this.getClass().getName() + " view : "+view.toString();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;
        isIdle =  Judge.isVisible(view, 90);
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
