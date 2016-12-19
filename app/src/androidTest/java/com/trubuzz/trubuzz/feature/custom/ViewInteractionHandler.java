package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.trubuzz.trubuzz.shell.Element;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;

/**
 * Created by king on 16/12/12.
 */

public class ViewInteractionHandler {
    private final static String TAG = "jcd_"+ViewInteractionHandler.class.getSimpleName();

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
    public static int getRecyclerViewItemCount(final ViewInteraction viewInteraction) {
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
    public static int getRecyclerViewItemCount(final Matcher<View> matcher) {
        return getRecyclerViewItemCount(onView(matcher));
    }

    public static int getRecyclerViewItemCount(final Element<Matcher<View>> element) {
        return getRecyclerViewItemCount(element.interactionWay());
    }

    /**
     * 获得RecyclerView item 的position 和 view
     * 封装在 {@link ViewPosition} 中
     * @param v
     * @param findMatcher
     * @return
     */
    public static List<ViewPosition> getRecyclerViewItem(final ViewInteraction v , Matcher<View> findMatcher) {
        List<ViewPosition> views = new ArrayList<ViewPosition>();
        v.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "get RecyclerView children position and view .";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                int count = recyclerView.getAdapter().getItemCount();
                for(int i = 0;i<count;i++) {
                    new CustomRecyclerViewActions.ScrollToRecyclerPosition(i).perform(uiController, view);
                    uiController.loopMainThreadUntilIdle();
                    View tmpView = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                    if (findMatcher.matches(tmpView)) {
                        ViewPosition vp = new ViewPosition(i, tmpView);
                        views.add(vp);
                    }
                }
            }

        });
        return views;
    }

    public static List<ViewPosition> getRecyclerViewItem(final Matcher<View> matcher ,Matcher<View> findMatcher){
        return getRecyclerViewItem(onView(matcher), findMatcher);
    }
    public static List<ViewPosition> getRecyclerViewItem(final Element<Matcher<View>> element , Matcher<View> findMatcher){
        return getRecyclerViewItem(element.interactionWay(), findMatcher);
    }
    /**
     * {@link #getRecyclerViewItem} 的内部类
     */
    public static class ViewPosition{
        public int position;
        public View view;

        ViewPosition(int position, View view) {
            this.position = position;
            this.view = view;
        }
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