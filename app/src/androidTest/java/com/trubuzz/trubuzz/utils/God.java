package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
    public static String getStringFormat (String defaultStr ,int str , String formatString){
        return String.format(getString(defaultStr , str) ,formatString);
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

    public static String getAppName(Activity activity){
        PackageManager pm = activity.getPackageManager();
        String s = activity.getApplicationInfo().loadLabel(pm).toString();
        return s;
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
        return list.toArray(ts);
//        for (int i = 0; i<length; i++) {
//            ts[i] = list.get(i);
//        }
//        return ts;
    }
    public static <T> List<T> array2list(T [] arrays){
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, arrays);
        return list;
    }

    /**
     * 根据起始位置截取字符串
     * @param str
     * @param firstIndex
     * @param endIndex
     * @return 如果字符串小于end index 则返回本身
     */
    public static String getCutString(String str ,int firstIndex , int endIndex){
        return str.length()< endIndex ? str : str.substring(firstIndex , endIndex);
    }
    public static String getCutString(String str , int endIndex){
        return getCutString(str , 0 , endIndex);
    }

    /**
     * 字符串收尾置换
     * @param str
     * @return
     */
    public static String getHead2EndString(String str){
        char[] chars = str.toCharArray();
        char tmp = chars[0];
        chars[0] = chars[chars.length-1];
        chars[chars.length-1] = tmp;
        return String.valueOf(chars);
    }

    /**
     * 获得屏幕可见区域的矩形( 去除status bar And action bar )
     * @param view
     * @return
     */
    public static ScreenRectangle getScreenRectangle(View view){
        DisplayMetrics m = new DisplayMetrics();
        ((WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(m);

        // Get status bar height
        int resourceId = view.getContext().getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = (resourceId > 0) ? view.getContext().getResources()
                .getDimensionPixelSize(resourceId) : 0;

        // Get action bar height
        TypedValue tv = new TypedValue();
        int actionBarHeight = (view.getContext().getTheme().resolveAttribute(
                android.R.attr.actionBarSize, tv, true)) ? TypedValue.complexToDimensionPixelSize(
                tv.data, view.getContext().getResources().getDisplayMetrics()) : 0;

        return new ScreenRectangle(statusBarHeight, m.heightPixels - actionBarHeight, 0, m.widthPixels);
    }

    public static int getRandomInt(int max, int min) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
