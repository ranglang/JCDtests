package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import java.util.Collection;
import java.util.List;

import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * Created by king on 2016/9/5.
 */
public class God {

    /**
     * get context
     * @return
     */
//    public static Context getContext(){
//        return InstrumentationRegistry.getInstrumentation().getContext();
//    }

    /**
     * get ActivityManager
     * @param context
     * @return
     */
    public static ActivityManager getActivityManager(Context context){
        return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * get Running Task Info List
     * @param activityManager
     * @return
     */
    public static List<ActivityManager.RunningTaskInfo> getRunningTaskInfoList(ActivityManager activityManager){
        return activityManager.getRunningTasks(Integer.MAX_VALUE);
    }

    /**
     * 获得栈顶activity的相关描述
     * @param RunningTaskInfoList
     * @return 字符串结果,like this:"ComponentInfo{com.trubuzz.trubuzz/com.trubuzz.roy.SplashActivity}"
     */
    public static String getTopActivityNameInfo(List<ActivityManager.RunningTaskInfo> RunningTaskInfoList){
        return RunningTaskInfoList.get(0).toString();
    }

    /**
     * 返回给定全类名的Class对象 (已处理异常)
     * @param className
     * @return
     */
    public static Class<?> getFixedClass(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Activity getCurrentActivity(Instrumentation instrumentation) {
        final Activity[] currentActivity = new Activity[1];
        instrumentation.runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    currentActivity[0] = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity[0];
    }
}
