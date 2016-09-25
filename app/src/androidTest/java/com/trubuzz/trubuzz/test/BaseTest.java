package com.trubuzz.trubuzz.test;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.trubuzz.trubuzz.feature.AdvancedViewInteraction;
import com.trubuzz.trubuzz.feature.TestWatcherAdvance;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.After;
import org.junit.Rule;

import java.io.File;

/**
 * Created by king on 2016/9/23.
 */

public class BaseTest {
    private final String TAG = "jcd_BaseTest";
    public boolean isSucceeded = false;
    public static Context ctx = InstrumentationRegistry.getContext();
    public static Resources res = ctx.getResources();
    protected Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private String fileName ;

    @Rule
    public TestWatcherAdvance testWatcherAdvance = new TestWatcherAdvance();

    public UiDevice uiDevice = UiDevice.getInstance(instrumentation);

    @After
    public void tearDown(){

        String mN = testWatcherAdvance.getTestName();
        Log.i("jcd", "tearDown: ....."+ mN);
        if (! isSucceeded){
            Object obj = Registor.unReg(AdvancedViewInteraction.class.toString());
            try {
                if (obj instanceof Bitmap){
                    this.fileName = DoIt.outPutScreenshot(God.getCurrentActivity(instrumentation)
                            , (Bitmap) obj ,mN);
                }else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    this.fileName = DoIt.takeScreenshot(God.getCurrentActivity(InstrumentationRegistry.getInstrumentation()), mN);
                    DoIt.ts(uiDevice,new File(God.getCurrentActivity(instrumentation).getFilesDir().getAbsolutePath()+"/bb1.png"));
                }
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
