package com.trubuzz.trubuzz.idlingResource;

import android.content.Context;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import com.trubuzz.trubuzz.utils.Judge;

/**
 * Created by king on 2016/9/2.
 * 用于等待给定info的activity处于栈顶
 */
public class SomeActivityIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdle;
    private String currentActivityName ;
    private Context context;


    public SomeActivityIdlingResource(String currentActivityName , Context context){
        this.currentActivityName = currentActivityName;
        this.context = context;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;

        isIdle = Judge.isTopActivity(currentActivityName,context);
        Log.d("jcd_isIdle", Boolean.toString(isIdle));

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
