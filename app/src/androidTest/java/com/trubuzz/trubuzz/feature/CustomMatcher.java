package com.trubuzz.trubuzz.feature;

import android.support.test.espresso.Root;

import com.trubuzz.trubuzz.feature.customMatcher.ToastMatcher;

import org.hamcrest.Matcher;

/**
 * Created by king on 2016/9/23.
 */

public class CustomMatcher {

    public static Matcher<Root> isToast(){
        return new ToastMatcher();
    }

}
