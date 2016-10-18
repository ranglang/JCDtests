package com.trubuzz.trubuzz.feature.customMatcher;

import android.view.View;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by king on 2016/10/18.
 */

public class AncestorMatcher extends TypeSafeMatcher<View> {
    private Matcher<View> parentMatcher;
    private int index = 0;

    public AncestorMatcher(Matcher<View> parentMatcher) {
        this.parentMatcher = parentMatcher;
    }

    @Override
    protected boolean matchesSafely(View view) {
        ViewParent viewParent = view.getParent();
        while (true){
            if(parentMatcher.matches(viewParent)){
                return true;
            }else{
                index++;
                viewParent = viewParent.getParent();
                if(viewParent == null) return false;
            }
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Child at position " + index + " in parent ");
        parentMatcher.describeTo(description);
    }
}
