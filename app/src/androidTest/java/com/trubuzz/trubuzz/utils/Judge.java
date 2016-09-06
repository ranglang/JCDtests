package com.trubuzz.trubuzz.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by king on 2016/9/5.
 */
public class Judge {

    /**
     * 判断给定名称的activity是否是任务栈顶的activity
     * @param topActivityInfo
     * @param context
     * @return
     */
    public static boolean isTopActivity(String topActivityInfo , Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
        String cmpNameTemp = null;
        if (null != runningTaskInfos)
        {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
            Log.e("jcd_cmpNameTemp",cmpNameTemp);
        }

        if (null == cmpNameTemp)
        {
            return false;
        }

        return cmpNameTemp.equals(topActivityInfo);
    }
}
