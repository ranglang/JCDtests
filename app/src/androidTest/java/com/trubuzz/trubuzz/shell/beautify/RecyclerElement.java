package com.trubuzz.trubuzz.shell.beautify;

import android.content.Context;
import android.graphics.Rect;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;

import com.trubuzz.trubuzz.shell.Element;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 16/12/7.
 */

public class RecyclerElement implements Element<Matcher<View>> {
    private static final String TAG = "jcd_" + RecyclerElement.class.getSimpleName();

    private Matcher<View> recyclerMatcher;
    private int position;
    private Matcher<View> findMatcher;

    public RecyclerElement(Matcher<View> recyclerMatcher) {
        this.recyclerMatcher = recyclerMatcher;
    }
    public RecyclerElement(){}

    public RecyclerElement setRecyclerMatcher(Matcher<View> recyclerMatcher) {
        this.recyclerMatcher = recyclerMatcher;
        return this;
    }

    public RecyclerElement setPosition(int position) {
        this.position = position;
        return this;
    }

    public RecyclerElement setFindMatcher(Matcher<View> findMatcher) {
        this.findMatcher = findMatcher;
        return this;
    }

    public static RecyclerElement withRecyclerMatcher(Matcher<View> recyclerMatcher){
        return new RecyclerElement(recyclerMatcher);
    }

    public Matcher<View> atPosition(int position){
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with RecyclerView position : " + position);
            }

            @Override
            protected boolean matchesSafely(View view) {
                ViewParent viewParent = view.getParent();
                if (!(viewParent instanceof RecyclerView))    return false;
                if (!recyclerMatcher.matches(viewParent)) return false;

                RecyclerView recyclerView = (RecyclerView) viewParent;
                View childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;

                return view == childView;
            }
        };
    }

    public Matcher<View> atMatcher(Matcher<View> matcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View view) {
                if(! matcher.matches(view))     return false;
                ViewParent parent = view.getParent();
                while (parent != null) {
                    if(!(parent instanceof RecyclerView) && ! recyclerMatcher.matches(parent))
                        return true;
                    view = (View) parent;
                    parent = view.getParent();
                }
                return false;
            }
        };

    }
    @Override
    public Matcher<View> interactionWay() {
        return allOf(this.atPosition(this.position),this.atMatcher(this.findMatcher));
    }

    public static int getPosition(final ViewInteraction viewInteraction ,Matcher<View> viewMatcher){
        final int[] position = {-1};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "get RecyclerView`s position .";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                int itemCount = recyclerView.getAdapter().getItemCount();
                for (int i = 0; i < itemCount; i++) {
                    View itemView = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                    if(viewMatcher.matches(itemView)){
                        position[0] = i;
                    }
                }
            }
        });
        return position[0];
    }


    public static View getVisibleViewInteraction(final ViewInteraction viewInteraction){
        final View[] visibleView = {null};
        viewInteraction.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "get RecyclerView`s children , if current is visible 90% ";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                int childCount = recyclerView.getChildCount();
                for (int i = childCount - 1; i >= 0; i--) {
                    View childAt = recyclerView.getChildAt(i);
                    if (isVis(childAt, 90)) {
                        visibleView[0] = childAt;
                        Log.i(TAG, "getVisibleViewInteraction: childCount =" + childCount + " ; at child : " + i);
                        return;
                    }
                }
            }
        });
        return visibleView[0];
    }

    /**
     * 判断View可见度
     * @param view
     * @param areaPercentage 可见度
     * @return
     */
    public static boolean isVis(View view ,int areaPercentage){
        Rect visibleParts = new Rect();
        boolean visibleAtAll = view.getGlobalVisibleRect(visibleParts);
        if (!visibleAtAll) {
            return false;
        }

        Rect screen = getScreenWithoutStatusBarActionBar(view);
        int viewHeight = (view.getHeight() > screen.height()) ? screen.height() : view.getHeight();
        int viewWidth = (view.getWidth() > screen.width()) ? screen.width() : view.getWidth();

        double maxArea = viewHeight * viewWidth;
        double visibleArea = visibleParts.height() * visibleParts.width();
        int displayedPercentage = (int) ((visibleArea / maxArea) * 100);

        return displayedPercentage >= areaPercentage
                && withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE).matches(view);

    }

    private static Rect getScreenWithoutStatusBarActionBar(View view) {
        DisplayMetrics m = new DisplayMetrics();
        ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(m);

        // Get status bar height
        int resourceId = view.getContext().getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = (resourceId > 0) ? view.getContext().getResources()
                .getDimensionPixelSize(resourceId) : 0;

        // Get action bar height
        TypedValue tv = new TypedValue();
        int actionBarHeight = (view.getContext().getTheme().resolveAttribute(
                android.R.attr.actionBarSize, tv, true)) ? TypedValue.complexToDimensionPixelSize(
                tv.data, view.getContext().getResources().getDisplayMetrics()) : 0;

        return new Rect(0, 0, m.widthPixels, m.heightPixels - (statusBarHeight + actionBarHeight));
    }
}
