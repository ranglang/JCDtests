package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by king on 16/10/24.
 */

public class CustomViewAction  {

    private CustomViewAction(){}

    public static ViewAction pause(long time){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayed();
            }

            @Override
            public String getDescription() {
                return "sleep "+ time/1000 + " seconds .";
            }

            @Override
            public void perform(UiController uiController, View view) {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
