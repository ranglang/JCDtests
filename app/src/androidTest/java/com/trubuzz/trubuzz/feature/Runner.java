package com.trubuzz.trubuzz.feature;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.support.test.internal.runner.RunnerArgs;
import android.support.test.internal.runner.TestExecutor;
import android.support.test.internal.runner.TestRequest;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by king on 2016/9/22.
 * 该类主要重写了父类的onStart()方法,照搬了祖父类的onStart()方法 ( 通过反射方式获取到私有属性及方法 ).
 * 欲实现 添加监听器{@link ExListener}的功能 , 然而适用价值并不大,不如{@link TestWatcherAdvance} 监控
 * 测试生命周期实用.
 * @deprecated use {@link AdRunner}
 */

@Deprecated
public class Runner extends AndroidJUnitRunner{
    private final String TAG = "jcd_Runner";
    @Override
    public void onCreate(Bundle arguments) {
        CreateReport.createTestReport();
        super.onCreate(arguments);
        Log.i(TAG,"onCreate");
    }

    @Override
    public void onStart(){
        Log.i(TAG, "onStart: ");
//       super.onStart();
        ppOnStart();    //调用祖父级 onStart()方法
        RunnerArgs mRunnerArgs = (RunnerArgs) getField("mRunnerArgs");

        if (mRunnerArgs.idle) {
            Log.i(TAG, "Runner is idle...");
            return;
        }

        Bundle results = new Bundle();
        try {
            TestExecutor.Builder executorBuilder = new TestExecutor.Builder(this);

            executorBuilder.addRunListener(new ExListener()); //添加自定义监听

            addListeners(mRunnerArgs, executorBuilder);

            TestRequest testRequest = buildRequest(mRunnerArgs, getArguments());

            results = executorBuilder.build().execute(testRequest);

        } catch (RuntimeException e) {
            final String msg = "Fatal exception when running tests";
            Log.e(TAG, msg, e);
            // report the exception to instrumentation out
            results.putString(Instrumentation.REPORT_KEY_STREAMRESULT,
                    msg + "\n" + Log.getStackTraceString(e));
        }
        finish(Activity.RESULT_OK, results);
    }

    @Override
    public boolean onException(Object obj, Throwable e){
        Log.e(TAG, "onException:  ......d" );
        return super.onException(obj,e);
    }

    /**
     * 反射实现
     * @param args
     * @param builder
     */
    private void addListeners(RunnerArgs args, TestExecutor.Builder builder) {
        exMethod("addListeners",args,builder);
    }

    /**
     * 反射实现
     * @param runnerArgs
     * @param bundleArgs
     * @return
     */
    TestRequest buildRequest(RunnerArgs runnerArgs, Bundle bundleArgs) {
        return (TestRequest)exMethod("buildRequest", runnerArgs,bundleArgs);
    }

    /**
     *
     */
    private void tryLoadingJsBridge() {
        exPMethod("tryLoadingJsBridge");
    }
    /**
     * 反射实现
     * @return
     */
    private Bundle getArguments(){
        return (Bundle) getField("mArguments");
    }

    /**
     * 反射实现
     */
    private void ppOnStart(){
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                tryLoadingJsBridge();
            }});
        waitForIdleSync();
    }

    public void test(){

    }
    public Object getField(String fieldName){
        Class clz = this.getClass().getSuperclass();
        Field[] f = clz.getDeclaredFields();
        Field.setAccessible(f,true);
        for(Field field : f){
            if(fieldName.equals(field.getName())){
                try {
                    return field.get(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private Object exMethod(String methodName ,Object...obj){
        Method[] ms = this.getClass().getSuperclass().getDeclaredMethods();
        Method.setAccessible(ms,true);
        for(Method m : ms){
            if (methodName.equals(m.getName())){    //匹配方法名
                boolean same = true;
                Type[] types = m.getGenericParameterTypes();
                if (obj.length == types.length){        //匹配参数个数和类型
                    for(int i=0;i<obj.length; i++){
                        if (! types[i].equals(obj[i].getClass())){
                            same = false;
                        }
                    }
                }
                //方法名和参数类型需完全匹配
                if (same) {
                    try {
                        return m.invoke(this, obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return  null;
    }

    private Object exPMethod(String methodName ,Object...obj){
        Method[] ms = this.getClass().getSuperclass().getSuperclass().getDeclaredMethods();
        Method.setAccessible(ms,true);
        for(Method m : ms){
            if (methodName.equals(m.getName())){    //匹配方法名
                boolean same = true;
                Type[] types = m.getGenericParameterTypes();
                if (obj.length == types.length){        //匹配参数个数和类型
                    for(int i=0;i<obj.length; i++){
                        if (! types[i].equals(obj[i].getClass())){
                            same = false;
                        }
                    }
                }
                //方法名和参数类型需完全匹配
                if (same) {
                    try {
                        return m.invoke(this, obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return  null;
    }
}
