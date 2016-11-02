package com.trubuzz.trubuzz.feature;

import android.util.Log;

import com.trubuzz.trubuzz.constant.TestResult;
import com.trubuzz.trubuzz.report.CaseBean;
import com.trubuzz.trubuzz.report.ClassBean;
import com.trubuzz.trubuzz.shell.AdViewInteraction;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.AssumptionViolatedException;
import org.junit.rules.TestName;
import org.junit.runner.Description;

import java.util.Date;
import java.util.Map;

/**
 * Created by king on 2016/9/20.
 */

public class TestWatcherAdvance extends TestName {
    private final String TAG = "jcd_TestWatcher";

    private String testName;
    private TestResult result;
    private String message;
    private String errorImagePath;
    private ClassBean testClass;
    private BaseTest baseTest;
    private Map useData;
    private String localizedMessage;
    private String[] stackTraces;
    private long startTime;
    private long stopTime;

   // public TestWatcherAdvance(){}
    public TestWatcherAdvance(ClassBean testClass , BaseTest baseTest){
        this.testClass = testClass;
        this.baseTest = baseTest;
    }

    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        this.startTime = new Date().getTime();
        this.testName = description.getMethodName();
        Log.i(TAG, "starting: ...."+ testName +" at "+ God.getDateFormat(this.startTime));
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
    /********** 这是将BaseTest 中After 搬过来的实现*********/
        //这里只做错误截图和捕获截图文件
        Object obj = Registor.unReg(AdViewInteraction.class.toString());
        if (obj instanceof String) {
            this.errorImagePath = (String) obj;
        } else {
            this.errorImagePath = baseTest.takeScreenshot();
        }

    /************** The End ***************/
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
        Registor.unRegAll(BaseTest.class.toString());
        this.useData = baseTest.setUseData();

        this.stopTime = new Date().getTime();
        Log.i(TAG, "finished: ...."+ testName + " at "+God.getDateFormat(stopTime));

        CaseBean testCase = new CaseBean();
        testCase.setCaseName(this.testName);
        testCase.setErrorMsg(this.message);
        testCase.setImageName(this.errorImagePath);
        testCase.setTestResult(this.result);
        testCase.setUseData(this.useData);
        testCase.setLocalizedMessage(this.localizedMessage);
        testCase.setStackTraces(this.stackTraces);
        testCase.setSpendTime(stopTime - startTime);


//        TestReport.TestClass.TestCase testCase1 = testClass.createTestCase()
//                .setCaseName(this.testName)
//                .setErrorMsg(this.message)
//                .setImageName(this.errorImagePath)
//                .setTestResult(this.result)
//                .setUseData(this.useData)
//                .setLocalizedMessage(this.localizedMessage)
//                .setStackTraces(this.stackTraces)
//                .setSpendTime(stopTime - startTime);

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
