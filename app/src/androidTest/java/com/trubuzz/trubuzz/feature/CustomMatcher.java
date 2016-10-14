package com.trubuzz.trubuzz.feature;

import android.support.test.espresso.Root;
import android.view.View;

import com.trubuzz.trubuzz.feature.customMatcher.ChildAtPositionMatcher;
import com.trubuzz.trubuzz.feature.customMatcher.ToastMatcher;

import org.hamcrest.Matcher;

/**
 * Created by king on 2016/9/23.
 */

public class CustomMatcher {

    public static Matcher<Root> isToast(){
        return new ToastMatcher();
    }

    public static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {
        return new ChildAtPositionMatcher(parentMatcher,position);
    }

}
