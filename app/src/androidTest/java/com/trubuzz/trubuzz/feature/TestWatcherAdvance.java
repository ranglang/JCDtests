package com.trubuzz.trubuzz.feature;

import android.util.Log;

import com.trubuzz.trubuzz.utils.TestResult;

import org.junit.AssumptionViolatedException;
import org.junit.rules.TestName;
import org.junit.runner.Description;

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
    private TestReport.TestClass testClass;
    private Map useData;

   // public TestWatcherAdvance(){}
    public TestWatcherAdvance(TestReport.TestClass testClass){
        this.testClass = testClass;
    }

    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        this.testName = description.getMethodName();
        Log.i(TAG, "starting: ...."+ testName);
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
        this.message = e.getMessage();
        // take screenshot when test failed
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
     */
    protected void finished(Description description) {
        Log.i(TAG, "finished: ....");
        TestReport.TestClass.TestCase testCase = testClass.createTestCase()
                .setCaseName(this.testName)
                .setErrorMsg(this.message)
                .setImageName(this.errorImagePath)
                .setTestResult(this.result)
                .setUseData(this.useData);

        testClass.getTestCases().add(testCase);

    }


    public String getTestName() {
        return testName;
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
