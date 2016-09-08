package com.trubuzz.trubuzz.myInterface;

import android.support.test.espresso.IdlingResource;

/**
 * Created by king on 2016/9/8.
 */
public interface ActivityLifecycleIdlingResource<T> extends IdlingResource {

    void inject(T activityComponent);

    void clear();
}