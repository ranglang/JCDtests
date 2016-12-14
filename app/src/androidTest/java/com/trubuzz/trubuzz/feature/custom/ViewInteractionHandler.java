package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.trubuzz.trubuzz.shell.Element;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;

/**
 * Created by king on 16/12/12.
 */

public class ViewInteractionHandler {

    /**
     * 获取元素上的字串
     *
     * @param viewInteraction
     * @return
     */
    public static String getText(final ViewInteraction viewInteraction) {
        final String[] stringHolder = {null};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    public static String getText(final Matcher<View> matcher) {
        return getText(onView(matcher));
    }

    public static <T> String getText(final Element<T> element) {
        T ele = element.interactionWay();
        if (ele instanceof ViewInteraction) return getText((ViewInteraction) ele);
        if (ele instanceof Matcher) return getText((Matcher<View>) ele);

        return null;
    }

    /**
     * 获取 RecyclerView 项目的个数
     * @param viewInteraction
     * @return
     */
    public static int getRecyclerViewChildrenCount(final ViewInteraction viewInteraction) {
        final int[] count = {0};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "get RecyclerView Children Count .";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                count[0] = recyclerView.getAdapter().getItemCount();
            }
        });
        return count[0];
    }
    public static int getRecyclerViewChildrenCount(final Matcher<View> matcher) {
        return getRecyclerViewChildrenCount(onView(matcher));
    }

    public static <T> int getRecyclerViewChildrenCount(final Element<T> element) {
        T ele = element.interactionWay();
        if (ele instanceof ViewInteraction) return getRecyclerViewChildrenCount((ViewInteraction) ele);
        if (ele instanceof Matcher) return getRecyclerViewChildrenCount((Matcher<View>) ele);

        return 0;
    }

    /**
     * 从 ViewInteraction 获取View
     * @param viewInteraction
     * @return
     */
    public static View getView(final ViewInteraction viewInteraction) {
        final View[] returnView = new View[1];
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return any(View.class);
            }

            @Override
            public String getDescription() {
                return "get view .";
            }

            @Override
            public void perform(UiController uiController, View view) {
                returnView[0] = view;
            }
        });
        return returnView[0];
    }
    public static View getView(final Matcher<View> matcher) {
        return getView(onView(matcher));
    }

    public static <T> View getView(final Element<T> element) {
        T ele = element.interactionWay();
        if (ele instanceof ViewInteraction) return getView((ViewInteraction) ele);
        if (ele instanceof Matcher) return getView((Matcher<View>) ele);

        return null;
    }
}