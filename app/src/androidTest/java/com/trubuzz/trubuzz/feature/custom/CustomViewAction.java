package com.trubuzz.trubuzz.feature.custom;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by king on 16/10/24.
 */

public class CustomViewAction  {

    private CustomViewAction(){}

    public static ViewAction pause(long time){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
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

    public static ViewAction setDate(final int year, final int monthOfYear, final int dayOfMonth) {
        String dclz = "com.wdullaer.materialdatetimepicker.date.DayPickerView";
        String ond = "com.wdullaer.materialdatetimepicker.date.DatePickerController";
        return new ViewAction() {

            @Override
            public void perform(UiController uiController, View view) {
//                final DayPickerView dayPickerView = (DayPickerView) view;

                try {
                    Field f = null; //NoSuchFieldException
                    f = Class.forName(dclz).getDeclaredField("mController");
                    f.setAccessible(true);
                    Object controller = f.get(view); //IllegalAccessException
                    Method onday = Class.forName(ond).getDeclaredMethod("onDayOfMonthSelected" ,int.class ,int.class,int.class);
                    onday.invoke(controller ,year, monthOfYear, dayOfMonth);
//                    controller.onDayOfMonthSelected(year, monthOfYear, dayOfMonth);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getDescription() {
                return "set date";
            }

            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }
        };

    }

}
