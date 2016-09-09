package com.trubuzz.trubuzz.utils;

import android.content.Context;

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
}
