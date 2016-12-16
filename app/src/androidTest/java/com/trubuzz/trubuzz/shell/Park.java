package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

/**
 * Created by king on 16/10/24.
 */

public class Park {

    /**
     * @deprecated use {@link #given(Element)} instead .
     * 1. 避免泛型不安全性
     * 2. 已经使用了Element做封装, 可满足各式各样的需求
     * @param viewInteractionDesc
     * @param <T>
     * @return
     */
    public static <T> AdViewInteraction given(T viewInteractionDesc){
        return new AdViewInteraction(WithAny.getViewInteraction(viewInteractionDesc));
    }

    /**
     * @deprecated use {@link #given(Element)} instead .
     * 已将toast 封装入Element .
     * so , 后期将不推荐单独getToast
     * @param viewInteraction
     * @return
     */
    public static AdViewInteraction given(ViewInteraction viewInteraction){
        return new AdViewInteraction(viewInteraction);
    }

    /**
     * 使用matcher的封装
     * @param matcher
     * @return
     */
    public static AdViewInteraction given(Matcher<View> matcher){
        return new AdViewInteraction(onView(matcher));
    }

    /**
     * 较常用的方式
     * @param element
     * @param <T>
     * @return
     */
    public static <T> AdViewInteraction given(Element<T> element){
        T ele = element.interactionWay();
        if(ele instanceof ViewInteraction)  return new AdViewInteraction((ViewInteraction) ele);
        if(ele instanceof Matcher)  return new AdViewInteraction(onView((Matcher<View>) ele));

        return new AdViewInteraction();
    }
    public static AdViewInteraction given(AdViewInteraction element){
        return element;
    }

/****************** web view ********************/

    /**
     * 封装onWebView
     * @return
     */
    public static AdWebInteraction webGiven(){
        return new AdWebInteraction(onWebView());
    }
    public static AdWebInteraction webGiven(Matcher<View> viewMatcher) {
        return new AdWebInteraction(onWebView(viewMatcher));
    }


/********************** onData ***********************/
    /**
     * Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific row.
     * <p>
     * Note: A custom matcher can be used to match the content and have more readable code.
     * See the Custom Matcher Sample.
     * </p>
     *
     * @param value the content of the field
     * @return a {@link DataInteraction} referencing the row
     */
    public static DataInteraction onRow(String key ,String value) {
        return onData(hasEntry(equalTo(key), is(value)));
    }
}
