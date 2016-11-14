package com.trubuzz.trubuzz.test;

import android.util.Log;

import com.trubuzz.trubuzz.feature.ClassWatcherAdvance;
import com.trubuzz.trubuzz.feature.TestWatcherAdvance;
import com.trubuzz.trubuzz.feature.listen.EventSource;
import com.trubuzz.trubuzz.shell.AdViewInteraction;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.Parameterized;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import junitparams.JUnitParamsRunner;

import static com.trubuzz.trubuzz.constant.Env.uiDevice;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 2016/9/23.
 * 主要功能用于测试生命周期的监控 ; 测试错误截图 ; 测试报告数据收集等
 * 所有的Test子类必须继承该类
 */

public class BaseTest {
    private final String TAG = "jcd_BaseTest";
    private Map useData;
    public EventSource eventSource = new EventSource();  //预先实例化事件源

    private String fileName;

    @ClassRule
    public static ClassWatcherAdvance classWatcherAdvance = new ClassWatcherAdvance();
    @Rule
    public TestWatcherAdvance testWatcherAdvance = new TestWatcherAdvance(classWatcherAdvance.getTestClass() , this);

//    public UiDevice uiDevice = UiDevice.getInstance(instrumentation);

    @Before
    public void setUp() {
        Registor.reg(BaseTest.class.toString(), this);
        sleep(1000);    //在每个test之间预留1秒的缓冲
    }

    //@After
//    public void tearDown() {
//        Registor.unRegAll(BaseTest.class.toString());
//        String mN = testWatcherAdvance.getTestName();
//        Log.i("jcd", "tearDown: ....." + mN);
//        if (!isSucceeded) {
//            Object obj = Registor.unReg(AdViewInteraction.class.toString());
//            if (obj instanceof String) {
//                this.fileName = (String) obj;
//            } else {
//                this.fileName = takeScreenshot();
//            }
//        }
//        testWatcherAdvance.setErrorImagePath(this.fileName);
//        testWatcherAdvance.setUseData(setUseData());
//    }


    public String getFileName() {
        return this.fileName;
    }

    /**
     * 等待 1 秒后截屏 , 确保元素完全展示
     * 该功能也用在 {@link AdViewInteraction check(ViewInteraction, ViewAssertion) ;}
     * and {@link AdViewInteraction perform(ViewInteraction, ViewAction...)}
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
     * 设置使用数据
     * @return
     */
    public Map setUseData(){
        Class clz = this.getClass();
        Class<? extends Runner> runWithClass = ((RunWith)clz.getAnnotation(RunWith.class)).value();
        if(useData != null && !useData.isEmpty()){
            return useData;
        }
        if(runWithClass.equals(Parameterized.class)){
            useData = new HashMap();
            Field [] fields = clz.getFields();          // 由于注解是要求属性必须为public
            for(Field field : fields){
                try {
                    // 这里只能获取使用了@Parameter(index)注解过的的属性.
                    if (field.isAnnotationPresent(Parameterized.Parameter.class)) {
                        useData.put(field.getName(), field.get(this));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }else if(runWithClass.equals(JUnitParamsRunner.class)){
            Log.i(TAG, "setUseData: 使用了JUnitParamsRunner 的注解 , 将由具体Test方法回调putData(Object ... keys_values) set useData.");
        }
        return this.useData;
    }

    /**
     * 在子类获取useData的值 , 要求必须以键值对的形式输入
     * @param keys_values 如: this.putData("key1",123,"key2","param2", "key3",object ,"key4" , null);
     * @return map
     */
    protected void putData(Object ... keys_values){
        useData = new HashMap();
        for(int i=0 ; i<keys_values.length; i=i+2){
            useData.put(keys_values[i] , keys_values[i+1]);
        }
    }
    protected void putData(Map data){
        this.useData = data;
    }
}
