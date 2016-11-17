package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.ViewInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;

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

    public static <T> AdViewInteraction given(Element<T> element){
        T ele = element.interactionWay();
        if(ele instanceof ViewInteraction)  return new AdViewInteraction((ViewInteraction) ele);
        if(ele instanceof Matcher)  return new AdViewInteraction(onView((Matcher<View>) ele));

        return new AdViewInteraction();
    }

}
