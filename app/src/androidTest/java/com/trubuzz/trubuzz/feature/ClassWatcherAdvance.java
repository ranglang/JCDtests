package com.trubuzz.trubuzz.feature;

import android.util.Log;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.report.CaseBean;
import com.trubuzz.trubuzz.report.ClassBean;
import com.trubuzz.trubuzz.report.SuiteBean;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.rules.TestName;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by king on 2016/9/20.
 * 用于创建class 级的测试报告
 */

public class ClassWatcherAdvance extends TestName {
    private final String TAG = "jcd_ClassWatcher";

//    private TestReport.TestClass testClass ;
    private ClassBean testClass ;
    private String testClassName;
    private long startTime;
    private long stopTime;


    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        this.startTime = new Date().getTime();
        this.testClassName = description.getClassName();
        Log.i(TAG, "starting: ...."+ testClassName + " at "+ God.getDateFormat(startTime));

        testClass = new ClassBean();
        testClass.setTestClassName(this.testClassName);
        testClass.setTestCases(new ArrayList<CaseBean>());

//        testClass = CreateReport.getTestReport().createTestClass();
//        testClass.setTestClassName(this.testClassName);
//        testClass.setTestCases(new ArrayList<TestReport.TestClass.TestCase>());
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected void finished(Description description) {
        this.stopTime = new Date().getTime();
        Log.i(TAG, "finished: ...."+ testClassName + " at "+God.getDateFormat(stopTime));

        testClass.setSpendTime(stopTime - startTime);
        SuiteBean testSuite = (SuiteBean) Registor.peekReg(Env.suiteRegisterKey);
        assert testSuite != null;
        testSuite.addTestClasses(testClass);

//        CreateReport.getTestReport().getTestClasses().add(testClass);
    }


    public ClassBean getTestClass() {
        return testClass;
    }
}
