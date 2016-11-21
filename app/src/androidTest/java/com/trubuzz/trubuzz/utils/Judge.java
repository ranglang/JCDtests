package com.trubuzz.trubuzz.utils;

import android.content.Context;

import com.trubuzz.trubuzz.shell.Element;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static com.trubuzz.trubuzz.shell.Park.given;

/**
 * Created by king on 2016/9/5.
 */
public class Judge {

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
}
