package com.trubuzz.trubuzz.test;

import android.util.Log;

import com.trubuzz.trubuzz.feature.ClassWatcherAdvance;
import com.trubuzz.trubuzz.feature.TestWatcherAdvance;
import com.trubuzz.trubuzz.shell.AdViewInteraction;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import java.util.ArrayList;
import java.util.List;

import static com.trubuzz.trubuzz.constant.Env.uiDevice;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 2016/9/23.
 * 主要功能用于测试生命周期的监控 ; 测试错误截图 ; 测试报告数据收集等
 * 所有的Test子类必须继承该类
 */

public class BaseTest {
    private final String TAG = "jcd_BaseTest";
    private List<String> compareImageNames = new ArrayList<>();

    @ClassRule
    public static ClassWatcherAdvance classWatcherAdvance = new ClassWatcherAdvance();
    @Rule
    public TestWatcherAdvance testWatcherAdvance = new TestWatcherAdvance(classWatcherAdvance.getTestClass() , this);


    @Before
    public void setUp() {
        Registor.reg(BaseTest.class.toString(), this);
        sleep(1000);    //在每个test之间预留1秒的缓冲
    }

    /**
     * 等待 1 秒后截屏 , 确保元素完全展示
     * 该功能也用在 {@link AdViewInteraction check(ViewInteraction, ViewAssertion) ;}
     * and {@link AdViewInteraction perform(ViewInteraction, ViewAction...)}
     * @return 截屏图片的绝对路径
     */
    public String takeScreenshot() {
        sleep(1000);
        String fPath = DoIt.takeScreenshot(uiDevice, testWatcherAdvance.getTestName());
        Log.i(TAG, "takeScreenshot: 截图成功 ; 保存路径 : " + fPath);
        return fPath;
    }

    /**
     * 截图并将路径保存至list
     * @param secondName
     */
    public void compareTakeScreenshot(String secondName){
        this.compareImageNames.add(DoIt.takeScreenshot(uiDevice ,secondName));
    }

    public List<String> getCompareImageNames() {
        return compareImageNames;
    }
}
