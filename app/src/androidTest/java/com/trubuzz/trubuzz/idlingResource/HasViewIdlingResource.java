package com.trubuzz.trubuzz.idlingResource;

import android.support.test.espresso.IdlingResource;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.feature.custom.handlers.ViewsFinder;
import com.trubuzz.trubuzz.shell.Element;

import org.hamcrest.Matcher;

import java.util.List;

/**
 * Created by king on 16/12/12.
 */

public class HasViewIdlingResource implements IdlingResource {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private ResourceCallback resourceCallback;
    private boolean isIdle;
    private final boolean exist;        //true : 可见/存在 ; false: 不可见/不存在
    private final Matcher<View> matcher;

    public HasViewIdlingResource(boolean exist, Matcher<View> matcher) {
        this.exist = exist;
        this.matcher = matcher;
    }
    public HasViewIdlingResource(boolean exist, Element<Matcher<View>> element) {
        this.exist = exist;
        this.matcher = element.way();
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;
        ViewsFinder vf = new ViewsFinder();
        List<View> views = vf.getViews(matcher);
        isIdle =  views == null || views.size() == 0;   // 未匹配到则为 true
        if(exist){
            isIdle = !isIdle;   //如果要求可见则取反
        }
        Log.i(TAG, String.format("isIdleNow: hope view existence = %s ,actual existence = %s .", exist, isIdle));
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
