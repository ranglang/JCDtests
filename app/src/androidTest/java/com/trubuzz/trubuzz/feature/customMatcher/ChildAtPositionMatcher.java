package com.trubuzz.trubuzz.feature.customMatcher;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by king on 2016/10/9.
 */

public class ChildAtPositionMatcher extends TypeSafeMatcher<View> {
    private Matcher<View> parentMatcher;
    private int position ;

    public ChildAtPositionMatcher( Matcher<View> parentMatcher, int position) {
        this.parentMatcher = parentMatcher;
        this.position = position;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
                && view.equals(((ViewGroup) parent).getChildAt(position));
    }
}
