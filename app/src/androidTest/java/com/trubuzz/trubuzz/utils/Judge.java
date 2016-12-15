package com.trubuzz.trubuzz.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.trubuzz.trubuzz.feature.custom.CustomRecyclerViewActions;
import com.trubuzz.trubuzz.shell.Element;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withView;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 2016/9/5.
 */
public class Judge {
    private static final String TAG = "jcd_" + Judge.class.getSimpleName();

    /**
     * 判断给定名称的activity是否是任务栈顶的activity
     * @param topActivityName
     * @param context
     * @return
     */
    public static boolean isTopActivity(String topActivityName , Context context){
        return topActivityName.equals(God.getTopActivityName(context));
    }

    /**
     * 判断元素是否被选中
     * @param ele
     * @return
     */
    public static boolean isChecked(Element ele){
        try {
            given(ele).check(matches(android.support.test.espresso.matcher.ViewMatchers.isChecked()));
            return true;
        } catch (Throwable e ){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断View可见度
     * @param view
     * @param areaPercentage 可见度
     * @return
     */
    public static boolean isVisible(View view , int areaPercentage){
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

    /**
     * {@link #isVisible(View, int)} 的辅助方法
     * @param view
     * @return
     */
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

    /**
     * 判断 Adapter 是否存在某个子View
     * @param view
     * @param dataMatcher
     * @return
     */
    public static boolean isExistData(final View view ,final Matcher dataMatcher){
        if (view instanceof AdapterView) {
            @SuppressWarnings("rawtypes")
            Adapter adapter = ((AdapterView) view).getAdapter();
            int adapterCount = adapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                Object adapterItem = adapter.getItem(i);
                if (dataMatcher.matches(adapterItem)) {
                    return true;
                }
            }
        }
        if (view instanceof RecyclerView) {
            return isExistData(onView(withView(view)),dataMatcher);
        }
        return false;
    }

    public static<VH extends RecyclerView.ViewHolder> boolean isExistData(final ViewInteraction v,final Matcher dataMatcher) {
        final boolean[] exist = {false};
        v.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }
            @Override
            public String getDescription() {
                return "Determine the view exist .";
            }
            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                int itemCount = adapter.getItemCount();
                for(int position=0;position<itemCount;position++) {

                    new CustomRecyclerViewActions.ScrollToRecyclerPosition(position).perform(uiController, view);
                    uiController.loopMainThreadUntilIdle();

                    @SuppressWarnings("unchecked")
                    VH viewHolderForPosition = (VH) recyclerView.findViewHolderForAdapterPosition(position);
                    if (null == viewHolderForPosition) {
                        throw new PerformException.Builder().withActionDescription(this.toString())
                                .withViewDescription(HumanReadables.describe(view))
                                .withCause(new IllegalStateException("No view holder at position: " + position))
                                .build();
                    }

                    View viewAtPosition = viewHolderForPosition.itemView;
                    if (null == viewAtPosition) {
                        throw new PerformException.Builder().withActionDescription(this.toString())
                                .withViewDescription(HumanReadables.describe(viewAtPosition))
                                .withCause(new IllegalStateException("No view at position: " + position)).build();
                    }
                    Log.d(TAG, String.format("isExistData: itemCount = %s , currentItem = %s", itemCount, position));
                    if (dataMatcher.matches(viewAtPosition)) {
                        exist[0] = true;
                        return;
                    }
                }
            }
        });
        return exist[0];
    }
    public static boolean isExistData(final Element v,final Matcher dataMatcher) {
        return isExistData(getView(v), dataMatcher);
    }
    public static boolean isExistData(final Matcher<View> v,final Matcher dataMatcher) {
        return isExistData(getView(v), dataMatcher);
    }
}
