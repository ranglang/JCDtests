package com.trubuzz.trubuzz.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by king on 2016/9/2.
 */
public class MyActivityManager {
        private static MyActivityManager sInstance = new MyActivityManager();
        private WeakReference<Activity> sCurrentActivityWeakRef;


        public MyActivityManager() {

        }

        public static MyActivityManager getInstance() {
            return sInstance;
        }

        public Activity getCurrentActivity() {
            Activity currentActivity = null;
            if (sCurrentActivityWeakRef != null) {
                currentActivity = sCurrentActivityWeakRef.get();
            }
            return currentActivity;
        }

        public void setCurrentActivity(Activity activity) {
            sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
        }

    /**
     * 判断给定名称的activity是否是任务栈顶的activity
     * @param name
     * @param context
     * @return
     */
    public static boolean isTopActivity(String name , Context context){

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
        String cmpNameTemp = null;
        if (null != runningTaskInfos)
        {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
            Log.e("my debug",cmpNameTemp);
        }

        if (null == cmpNameTemp)
        {
            return false;
        }

        return cmpNameTemp.equals(name);
    }
}
