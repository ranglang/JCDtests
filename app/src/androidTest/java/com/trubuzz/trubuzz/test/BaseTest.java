package com.trubuzz.trubuzz.test;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.trubuzz.trubuzz.feature.AdvancedViewInteraction;
import com.trubuzz.trubuzz.feature.TestWatcherAdvance;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

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

    @Before
    public void setUp(){
        Registor.reg(BaseTest.class.toString(),this);
    }

    @After
    public void tearDown(){
        Registor.unRegAll(BaseTest.class.toString());
        String mN = testWatcherAdvance.getTestName();
        Log.i("jcd", "tearDown: ....."+ mN);
        if (! isSucceeded){
            Object obj = Registor.unReg(AdvancedViewInteraction.class.toString());
            if (obj instanceof String){
                this.fileName = (String)obj;
            }else {
                this.fileName = takeScreenshot();
            }
        }
    }

    public static String getString (int str){
        return res.getString(str);
    }
    public String getFileName(){
        return this.fileName;
    }

    /**
     * 等待 1 秒后截屏 , 确保元素完全展示
     * @return 截屏图片的绝对路径
     */
    public String takeScreenshot(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String fPath = DoIt.takeScreenshot(uiDevice, testWatcherAdvance.getTestName());
        Log.i(TAG, "takeScreenshot: 截图成功 ; 保存路径 : "+fPath);
        return fPath;
    }
}
