package com.trubuzz.trubuzz.feature;

import android.support.test.internal.statement.UiThreadStatement;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.utils.ClassesParamName;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.TestResult;

import org.junit.AssumptionViolatedException;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.rules.TestName;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junitparams.internal.InvokeParameterisedMethod;

/**
 * Created by king on 2016/9/20.
 */

public class TestWatcherAdvance extends TestName {
    private final String TAG = "jcd_TestWatcher";

    private String testName;
    private TestResult result;
    private String message;
    private String errorImagePath;
    private TestReport.TestClass testClass;
    private Map useData;
    private String localizedMessage;
    private String[] stackTraces;
    private long startTime;
    private long stopTime;

   // public TestWatcherAdvance(){}
    public TestWatcherAdvance(TestReport.TestClass testClass){
        this.testClass = testClass;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        System.out.println("base class : "+ base.getClass());
        useData = new HashMap();
        ActivityTestRule a;
        UiThreadStatement b;
        RunAfters c;
        opBase(base);
        if (!(base instanceof InvokeParameterisedMethod)){     /*如果属于该类,表明没有使用JUnitParamsRunner的
                                                            参数化方法 , 则不用管useData的值,由BaseTest处理*/
            List<String> paramNames = new ArrayList<>();
            InvokeParameterisedMethod ipm = (InvokeParameterisedMethod) base;
            try {
                paramNames = ClassesParamName.getMethodParamNames(ipm.getTestClass().getClass() , ipm.getTestMethod().getMethod());
            } catch (IOException e) {
                Log.e(TAG, "apply: parameter name get error ! ",e );
            }
            for(int i = 0; i<paramNames.size() ; i++){
                useData.put(paramNames.get(i),ipm.getParams()[i]);  //为了保证顺序, 按下标取值
            }
        }
        return super.apply(base, description);
    }
    public void opBase(Statement base) {
        try {
            Field[] fields = getDecFields(base);
            for(Field f : fields){
                if(! f.getName().equals("mBase"))continue;
                Object x = f.get(base);
                System.out.println("f : "+ f.getName());
                System.out.println(x.getClass());

                Field[] fields1 = getDecFields(x);
                for(Field field1 : fields1){
                    if(! field1.getName().equals("mBase"))continue;

                    Object y = field1.get(x);
                    System.out.println("field : "+ field1.getName());
                    System.out.println(y.getClass());

                    Field[] fields2 = getDecFields(y);
                    for(Field field2 : fields2){
                        if(!field2.getName().equals("next"))continue;

                        Object z = field2.get(y);
                        System.out.println("field2 : "+ field2.getName());
                        System.out.println(z.getClass());

                        Field[] fields3 = getDecFields(z);
                        for(Field field3 : fields3){
                            if(!field3.getName().equals("next"))continue;

                            Object w = field3.get(z);
                            System.out.println("field3 : "+ field3.getName());
                            System.out.println(w.getClass());

                            Field[] fields4 = getDecFields(w);
                            for(Field field4 : fields4){
                                if(! field4.getName().equals("params")){
                                    continue;
                                }
                                Object[] q = (Object[]) field4.get(w);
                                System.out.println("field4 : "+ field4.getName());
                                System.out.println(q.getClass());

                                for(Object o : q){
                                    System.out.println(o.toString());
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Field[] getDecFields(Object object){
        Class clz = object.getClass();
        Field [] fields = clz.getDeclaredFields();
        Field.setAccessible(fields , true);
        return fields;
    }
    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        this.startTime = new Date().getTime();
        this.testName = description.getMethodName();
        Log.i(TAG, "starting: ...."+ testName +" at "+ God.getDateFormat(this.startTime));
        ArrayList<Description> children = description.getChildren();
        System.out.println(  );
    }

    /**
     * Invoked when a test succeeds
     */
    protected void succeeded(Description description) {
        this.result = TestResult.SUCCEEDED;
        Log.i(TAG, "succeeded: ");
    }

    /**
     * Invoked when a test fails
     */
    protected void failed(Throwable e, Description description) {
//
        Log.e(TAG,"test failed : "+e.getMessage());
        this.result = TestResult.FAILED;
        this.message = e.getMessage();                       //完整的错误信息
        this.localizedMessage = e.getLocalizedMessage();    //简短的错误信息
        StackTraceElement[] stackTraceElements = e.getStackTrace();        //错误堆栈信息
        stackTraces = new String[stackTraceElements.length];
        for(int i=0; i< stackTraceElements.length ; i++){
            stackTraces[i] = stackTraceElements[i].toString();
        }
    }

    /**
     * Invoked when a test is skipped due to a failed assumption.
     */
    @Deprecated
    protected void skipped(AssumptionViolatedException e, Description description) {
        super.skipped(e,description);
        this.result = TestResult.SKIPPED;
        this.message = e.getMessage();
    }



    /**
     * Invoked when a test method finishes (whether passing or failing)
     * 测试完成后, 将该test case的直接结果写入报表中
     */
    protected void finished(Description description) {
        this.stopTime = new Date().getTime();
        Log.i(TAG, "finished: ...."+ testName + " at "+God.getDateFormat(stopTime));

        TestReport.TestClass.TestCase testCase = testClass.createTestCase()
                .setCaseName(this.testName)
                .setErrorMsg(this.message)
                .setImageName(this.errorImagePath)
                .setTestResult(this.result)
                .setUseData(this.useData)
                .setLocalizedMessage(this.localizedMessage)
                .setStackTraces(this.stackTraces)
                .setSpendTime(stopTime - startTime);
        testClass.getTestCases().add(testCase);
    }


    public String getTestName() {
        return testName;
    }

    public Map getUseData() {
        return useData;
    }

    public TestResult getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorImagePath() {
        return errorImagePath;
    }

    public void setErrorImagePath(String errorImagePath) {
        this.errorImagePath = errorImagePath;
    }

    public void setUseData(Map useData) {
        this.useData = useData;
    }
}
