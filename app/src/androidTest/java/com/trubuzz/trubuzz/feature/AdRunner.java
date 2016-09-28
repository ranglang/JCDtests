package com.trubuzz.trubuzz.feature;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import java.util.ArrayList;

/**
 * Created by king on 2016/9/26.
 */

public class AdRunner extends AndroidJUnitRunner {
    private final String TAG = "jcd_AdRunner";

//    private long startTime;
//    private long stopTime;

    @Override
    public void onCreate(Bundle arguments) {
//        this.startTime = new Date().getTime();
        CreateReport.createTestReport();
        CreateReport.getTestReport().setTestClasses(new ArrayList<TestReport.TestClass>());
//        Log.i(TAG,"onCreate : 创建测试报告对象 at " + God.getDateFormat(startTime));
        super.onCreate(arguments);
    }

    @Override
    public void finish(int resultCode, Bundle results) {
//        this.stopTime = new Date().getTime();
//        Log.i(TAG, "finish: 测试完成 ,输出测试报告 at "+ God.getDateFormat(stopTime));

//        CreateReport.getTestReport().setSpendTime(stopTime - startTime);
        CreateReport.testOutputReport();
        super.finish(resultCode , results);
    }
}
