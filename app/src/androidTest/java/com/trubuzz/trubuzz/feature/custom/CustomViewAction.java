package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by king on 16/10/24.
 */

public class CustomViewAction  {
    private static final String TAG = "jcd_" + CustomViewAction.class.getSimpleName();

    private CustomViewAction(){}

   public static ViewAction nothing(){
       return new ViewAction() {
           @Override
           public Matcher<View> getConstraints() {
               return isDisplayed();
           }

           @Override
           public String getDescription() {
               return "nothing to do .";
           }

           @Override
           public void perform(UiController uiController, View view) {
               Log.i(TAG, "perform: no thing to do .");
           }
       };
   }

}
