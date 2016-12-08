package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 16/12/8.
 */

public class CustomRecyclerViewActions {

    public static ViewAction scrollToRecyclerPosition(int position) {
        return new ScrollToRecyclerPosition(position);
    }
    public static ViewAction atPositionAction(int position ,ViewAction viewAction){
        return new AtPositionViewAction<>(position, viewAction);
    }

    /************ ViewActions ***********/
    private static final class ScrollToRecyclerPosition implements ViewAction{
        private int position;

        ScrollToRecyclerPosition(int position) {
            this.position = position;
        }

        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll RecyclerView to position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            layoutManager.scrollToPositionWithOffset(position, 0);
//                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, position);

        }
    }

    /**
     *
     * @param <VH>
     */
    private static final class AtPositionViewAction<VH extends RecyclerView.ViewHolder> implements
            ViewAction {
        private final int position;
        private final ViewAction viewAction;

        private AtPositionViewAction(int position, ViewAction viewAction) {
            this.position = position;
            this.viewAction = viewAction;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "actionOnItemAtPosition performing ViewAction: " + viewAction.getDescription()
                    + " on item at position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;

            new ScrollToRecyclerPosition(position).perform(uiController, view);
            uiController.loopMainThreadUntilIdle();

            @SuppressWarnings("unchecked")
            VH viewHolderForPosition = (VH) recyclerView.findViewHolderForPosition(position);
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

            viewAction.perform(uiController, viewAtPosition);
        }
    }
}
