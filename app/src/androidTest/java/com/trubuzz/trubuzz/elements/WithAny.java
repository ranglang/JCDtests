package com.trubuzz.trubuzz.elements;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.trubuzz.trubuzz.feature.custom.CustomMatcher;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/29.
 * 做一些简单的匹配元素操作 具有局限性 .
 * 一些复杂的匹配依然需要自定义实现
 */

public class WithAny {

    public static Matcher<View>[] getMatchers(final String[] matcherStr){
        String[] strings = new String[3];
        Matcher<View>[] matchers = (Matcher<View>[]) Array.newInstance(Matcher.class,matcherStr.length +1);
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
        matchers[matcherStr.length] = isDisplayed();
        return matchers;
    }

    /**
     * 将 {@link #getMatchers(String[])} 方法 用{@link #all(Matcher[])} 组合
     * @param matcherStr
     * @return
     */
    public static Matcher<View> getAllMatcher(final String[] matcherStr){
        return all(getMatchers(matcherStr));
    }
    /**
     * 按要求传入数组 , 并返回元素的匹配
     * @param matcherStr array .输入顺序: { id , text ,hint } 若不具备则使用null or "" 占位 , 必须这么做
     *                   因为这只是一个简易的实现 {@link #getMatchers(String[])}
     * @return ViewInteraction
     */
    static ViewInteraction getViewInteraction(String[] matcherStr){
        return onView(getAllMatcher(matcherStr));
    }

    /**
     * 多方式获取 , 待完善
     * @param desc
     * @param <T>
     * @return
     */
    public static <T> ViewInteraction getViewInteraction(T desc){
        if (desc instanceof String[]){
            return getViewInteraction((String[]) desc);
        }
        return (ViewInteraction)desc;
    }

    /**
     * 多方式匹配 , 定格的 id text hint 和 自定义的 Matcher<View>... ms
     * 这是独立的查找方式 跟该类中的其他 getViewInteraction 方法无关
     * @param matcherStr
     * @param ms
     * @return
     */
    static ViewInteraction getViewInteraction(String[] matcherStr,Matcher<View>... ms){
        return onView(all(createMatchers(matcherStr , ms)));
    }

    static Matcher<View>[] createMatchers(String[] matcherStr ,Matcher<View>... ms){
        List<Matcher<View>> matcherList = new ArrayList<Matcher<View>>();
        for(int i=0 ;i<matcherStr.length ;i++){

            if(i==0 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
               matcherList.add(id(matcherStr[i]));
                continue;
            }
            if(i==1 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matcherList.add(text(matcherStr[i]));
                continue;
            }
            if(i==2 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matcherList.add(hint(matcherStr[i]));
            }
        }
        for(Matcher<View> matcher : ms){
            matcherList.add(matcher);
        }
        matcherList.add(isDisplayed());
        return God.list2array(Matcher.class ,matcherList);
    }

    /**
     * 使用自定义的匹配器获取toast
     * @param toastStr
     * @return
     */
    public static ViewInteraction getToast(String toastStr){
        return onView(withText(toastStr))
                .inRoot(CustomMatcher.withToast());
    }

    /**
     * 使用activity来获取 Context 的方式匹配toast ( 官方使用方法 )
     * 优点 : 清晰的错误日志, 方便调试 ( 推荐使用 )
     * @param toastStr
     * @param activity
     * @return
     */
    public static ViewInteraction getToast(String toastStr , ActivityTestRule activity){
        return onView(withText(toastStr))
                .inRoot(withDecorView(not(is(activity.getActivity().getWindow().getDecorView()))));
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
