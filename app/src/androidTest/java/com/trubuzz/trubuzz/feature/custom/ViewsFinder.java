package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.shell.Element;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.nothing;

/**
 * Created by king on 16/12/6.
 * 专注于获得多个view
 */

public class ViewsFinder {
    private List<View> views = new ArrayList<>();
    private boolean has_more_views;
    private final String TAG = "jcd_" + this.getClass().getSimpleName();

    private Matcher<View> thisMatcher(final Matcher<View> matcher){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("this is a view finder .");
            }

            @Override
            protected boolean matchesSafely(View view) {
                if(matcher.matches(view)){
                    views.add(view);
                    return true;
                }
                return false;
            }
        };
    }

    private void hasViews(Matcher<View> matcher){
        try {
            onView(thisMatcher(matcher)).perform(nothing());
            has_more_views = true;
        } catch (AmbiguousViewMatcherException e) {
            has_more_views = true;
        } catch (Exception ne){
            Log.e(TAG, "hasViews: ", ne);
            has_more_views = false;
        }
    }

    public List<View> getViews(Matcher<View> matcher) {
        this.hasViews(matcher);
        if(has_more_views)
            return views;
        else {
            Log.w(TAG, "getViews: 没有匹配的view" );
            return null;
        }
    }
    public List<View> getViews(Element<Matcher<View>> matcher) {
        return getViews(matcher.way());
    }
}
