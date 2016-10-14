package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * Created by king on 2016/9/5.
 */
public class God {

    public static Context ctx = InstrumentationRegistry.getContext();
    public static Resources res = ctx.getResources();

    /**
     * get context
     * @return
     */
    public static Context getContext(){
        return ctx;
    }

    /**
     * return resources
     * @return
     */
    public static Resources getResources(){
        return res;
    }

    /**
     * 通过R文件中的string的id 返回该string 在string.xml中当前语言环境中的值
     * @param str
     * @return
     */
    public static String getString (int str){
        return res.getString(str);
    }
    public static String getString (String str){
        return str;
    }
    /**
     * 如果R文件匹配失败则返回默认字符串
     * @param defaultStr
     * @param str
     * @return
     */
    public static String getString (String defaultStr ,int str){
        try {
            return res.getString(str);
        } catch (Resources.NotFoundException e) {
            return defaultStr;
        }
    }
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

    /**
     * 获得当前Activity对象
     * @param instrumentation
     * @return
     */
    public static Activity getCurrentActivity(Instrumentation instrumentation) {
        final Activity[] currentActivity = new Activity[1];
        instrumentation.runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()){
                    Activity a = (Activity) resumedActivities.iterator().next();
                    Log.i("jcd_resumedActivities",a.toString());
                    currentActivity[0] = a;
                }
            }
        });
        return currentActivity[0];
    }

    /**
     * 获得top activity的全类名
     * @param context
     * @return top activity class name
     */
    public static String getTopActivityName(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
        String cmpNameTemp = null;
        if (null != runningTaskInfos)
        {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
            Log.w("jcd_topActivity",cmpNameTemp);
            Log.w("jcd_2Activity",(runningTaskInfos.get(1).topActivity).toString());
        }

        if (null == cmpNameTemp)
        {
            return "";
        }

        return cmpNameTemp.split(Pattern.quote("/"))[1].split(Pattern.quote("}"))[0];
    }

    /**
     * 格式化时间
     * @param d Date or long
     * @param format like : "yyyy-MM-dd HH:mm:ss"
     * @param locale 时区
     * @return
     */
    public static String getDateFormat(Object d , String format, Locale locale){
        return new SimpleDateFormat(format, locale).format(d);
    }

    /**
     * 格式化时间
     * @param d Date or long
     * @param format
     * @return
     */
    public static String getDateFormat(Object d , String format){
        return new SimpleDateFormat(format).format(d);
    }

    /**
     * 默认使用中国传统格式
     * @param d
     * @return
     */
    public static String getDateFormat(Object d){
        return getDateFormat(d , "yy/MM/dd HH:mm:ss:SSS" ,Locale.CHINA);
    }

    public static <T> T[] list2array(Class clz ,List<T> list){
        int length = list.size();
        T [] ts = (T[]) Array.newInstance(clz , length);
        for (int i = 0; i<length; i++) {
            ts[i] = list.get(i);
        }
        return ts;
    }
}
