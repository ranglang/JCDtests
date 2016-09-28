package com.trubuzz.trubuzz.test;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.trubuzz.trubuzz.feature.AdvancedViewInteraction;
import com.trubuzz.trubuzz.feature.ClassWatcherAdvance;
import com.trubuzz.trubuzz.feature.TestWatcherAdvance;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 2016/9/23.
 */

public class BaseTest {
    private final String TAG = "jcd_BaseTest";
    public boolean isSucceeded = false;

    protected Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private String fileName;

    @ClassRule
    public static ClassWatcherAdvance classWatcherAdvance = new ClassWatcherAdvance();
    @Rule
    public TestWatcherAdvance testWatcherAdvance = new TestWatcherAdvance(classWatcherAdvance.getTestClass());

    public UiDevice uiDevice = UiDevice.getInstance(instrumentation);

    @Before
    public void setUp() {
        Registor.reg(BaseTest.class.toString(), this);
    }

    @After
    public void tearDown() {
        Registor.unRegAll(BaseTest.class.toString());
        String mN = testWatcherAdvance.getTestName();
        Log.i("jcd", "tearDown: ....." + mN);
        if (!isSucceeded) {
            Object obj = Registor.unReg(AdvancedViewInteraction.class.toString());
            if (obj instanceof String) {
                this.fileName = (String) obj;
            } else {
                this.fileName = takeScreenshot();
            }
        }
        testWatcherAdvance.setErrorImagePath(this.fileName);
        testWatcherAdvance.setUseData(this.getUseData());
    }


    public String getFileName() {
        return this.fileName;
    }

    /**
     * 等待 1 秒后截屏 , 确保元素完全展示
     * 该功能也用在 {@link AdvancedViewInteraction#check(ViewInteraction, ViewAssertion) ;}
     * and {@link AdvancedViewInteraction#perform(ViewInteraction, ViewAction...)}
     * @return 截屏图片的绝对路径
     */
    public String takeScreenshot() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String fPath = DoIt.takeScreenshot(uiDevice, testWatcherAdvance.getTestName());
        Log.i(TAG, "takeScreenshot: 截图成功 ; 保存路径 : " + fPath);
        return fPath;
    }

    /**
     *  Return sub class's use data
     *  由于由子类触发调用 , 所以将获取到的为子类定义的属性
     * @return
     */
    public Map getUseData(){
        Map<String , String> userData= new HashMap<String ,String>();
        try {
            Class clz = this.getClass();
            Field[] fields = clz.getDeclaredFields();
            Field.setAccessible(fields,true);
            for(Field f : fields) {
                // 输入数据一般为 String , so , 这里判断输出String类型的data . 可能会有多余的数据输出
                if (f.getGenericType() == String.class) {
                    userData.put(f.getName(),(String)f.get(this));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userData;
    }

}
