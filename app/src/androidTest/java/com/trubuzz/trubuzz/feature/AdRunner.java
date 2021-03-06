package com.trubuzz.trubuzz.feature;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import com.trubuzz.trubuzz.report.Report;
import com.trubuzz.trubuzz.report.SuiteBean;
import com.trubuzz.trubuzz.utils.God;

import java.util.Date;

import io.appflate.restmock.android.RESTMockTestRunner;

/**
 * Created by king on 2016/9/26.
 */

public class AdRunner extends RESTMockTestRunner {
    private final String TAG = "jcd_AdRunner";
    private Report report ;
    private long startTime;
    private long stopTime;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);      //这里得先调用, 否则不能初始化startTime , 其他好像不受影响

        this.startTime = new Date().getTime();
        /** new **/
        report = Report.getReport();
        report.setTestDate(this.startTime);
        report.setSuiteBean(new SuiteBean());   //创建空的测试套件

//        CreateReport.createTestReport();
//        CreateReport.getTestReport().setTestClasses(new ArrayList<TestReport.TestClass>());

        Log.i(TAG,"onCreate : 创建测试报告对象 at " + God.getDateFormat(startTime));
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        this.stopTime = new Date().getTime();
        Log.i(TAG, "finish: 测试完成 at "+ God.getDateFormat(stopTime));

        report.setSpendTime(stopTime - startTime);
        String jsonPath = report.testOutputReport();
        Log.i(TAG, "finish: 测试报告输出 at "+ jsonPath);
        super.finish(resultCode , results);         //这里得最后调用 , 因为将忽略其后的语句.
    }
}
