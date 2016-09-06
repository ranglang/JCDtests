package com.trubuzz.trubuzz.feature;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.trubuzz.trubuzz.data.AName;

import org.junit.runner.RunWith;

/**
 * Created by king on 2016/6/30.
 */
@RunWith(AndroidJUnit4.class)
public class BaseTest {
    private static Class<?> launcherActivityClass;
    public static ActivityTestRule<?> mActivityTestRule;
    private Context context;

    /**
     * 启动默认的MainActivity
     */
    public void lunchActivity(){
        lunchActivity(AName.MAIN);
    }
    /**
     * 启动指定的Activity
     * @param launcher_activity 当前活动的activity全类名
     */
    public void lunchActivity(String launcher_activity){
        getActivityClass(launcher_activity);

        mActivityTestRule = new ActivityTestRule(launcherActivityClass, true, false);

        context = InstrumentationRegistry.getInstrumentation().getContext();

        Intent intent = new Intent(context,launcherActivityClass);
        mActivityTestRule.launchActivity(intent);

    }

    /**
     * 反射获取默认MainActivity的类对象
     */
    protected void getActivityClass(){
        getActivityClass(AName.MAIN);
    }

    /**
     * 获取指定Activity名称的类对象
     * @param launcher_activity 当前活动的activity全类名
     */
    protected void getActivityClass(String launcher_activity){
        try {
            launcherActivityClass = Class.forName(launcher_activity);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected ActivityTestRule<?> getMActivityTestRule(){
        return mActivityTestRule;
    }


    public Context getContext(){
        return this.context;
    }
}
