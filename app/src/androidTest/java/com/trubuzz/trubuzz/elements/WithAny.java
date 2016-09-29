package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;
import android.view.View;

import com.trubuzz.trubuzz.feature.CustomMatcher;

import org.hamcrest.Matcher;

import java.lang.reflect.Array;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by king on 2016/9/29.
 * 做一些简单的匹配元素操作 具有局限性 .
 * 一些复杂的匹配依然需要自定义实现
 */

public class WithAny {

    /**
     * 按要求传入数组 , 并返回元素的匹配
     * @param matcherStr array .输入顺序: { id , text ,hint } 若不具备则使用null or "" 占位 , 必须这么做
     *                   因为这只是一个简易的实现
     * @return ViewInteraction
     */
    static ViewInteraction getViewInteraction(String[] matcherStr){
        Matcher<View>[] matchers = (Matcher<View>[]) Array.newInstance(Matcher.class,matcherStr.length);
        for(int i=0 ;i<matcherStr.length ;i++){
            if(i==0 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matchers[i] = id(matcherStr[i]);
                continue;
            }
            if(i==1 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matchers[i] = text(matcherStr[i]);
                continue;
            }
            if(i==2 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matchers[i] = hint(matcherStr[i]);
            }
        }
        return onView(all(matchers));
    }

    static ViewInteraction getToast(String toastStr){
        return onView(withText(toastStr))
                .inRoot(CustomMatcher.isToast());
    }

    private static Matcher<View> id(String resourceName){
        return withResourceName(resourceName);
    }
    private static Matcher<View> text(String text){
        return withText(text);
    }
    private static Matcher<View> hint(String text){
        return withHint(text);
    }
    private static Matcher<View> all(Matcher<View>... matcher){
        return allOf(matcher);
    }
}
