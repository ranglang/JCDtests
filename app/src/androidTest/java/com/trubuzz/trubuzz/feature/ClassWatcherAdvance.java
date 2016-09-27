package com.trubuzz.trubuzz.feature;

import android.util.Log;

import org.junit.rules.TestName;
import org.junit.runner.Description;

import java.util.ArrayList;

/**
 * Created by king on 2016/9/20.
 */

public class ClassWatcherAdvance extends TestName {
    private final String TAG = "jcd_ClassWatcher";

//    private String testClassName;
    private TestReport.TestClass testClass ;


    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        testClass = CreateReport.getTestReport().createTestClass();
        testClass.setTestCases(new ArrayList<TestReport.TestClass.TestCase>());
        Log.i(TAG, "starting: ...."+ testClass.setTestClassName(description.getClassName()));
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected void finished(Description description) {
        Log.i(TAG, "finished: ....");
        CreateReport.getTestReport().getTestClasses().add(testClass);
    }


    public TestReport.TestClass getTestClass() {
        return testClass;
    }
}
