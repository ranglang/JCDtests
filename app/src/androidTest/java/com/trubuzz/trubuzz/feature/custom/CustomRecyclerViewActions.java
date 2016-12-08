package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
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
}
