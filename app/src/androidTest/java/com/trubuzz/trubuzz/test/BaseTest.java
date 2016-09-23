package com.trubuzz.trubuzz.test;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.trubuzz.trubuzz.feature.TestWatcherAdvance;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.After;
import org.junit.Rule;

/**
 * Created by king on 2016/9/23.
 */

public class BaseTest {
    private final String TAG = "jcd_BaseTest";
    public boolean isSucceeded = false;
    public static Context ctx = InstrumentationRegistry.getContext();
    public static Resources res = ctx.getResources();
    private String fileName ;

    @Rule
    public TestWatcherAdvance testWatcherAdvance = new TestWatcherAdvance();

    @After
    public void tearDown(){
        String mN = testWatcherAdvance.getTestName();
        Log.i("jcd", "tearDown: ....."+ mN);
        if (! isSucceeded){
            try {
                this.fileName = DoIt.takeScreenshot(God.getCurrentActivity(InstrumentationRegistry.getInstrumentation()),mN);
            }catch (NullPointerException e){
                Log.w(TAG, "tearDown: 可能没有activity启动 ",e );
            }
        }
    }

    public static String getString (int str){
        return res.getString(str);
    }
    public String getFileName(){
        return this.fileName;
    }
}
