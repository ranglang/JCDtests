package com.trubuzz.trubuzz.feature;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by king on 2016/9/26.
 */

public class AdRunner extends AndroidJUnitRunner {
    private final String TAG = "jcd_AdRunner";

    @Override
    public void onCreate(Bundle arguments) {
        CreateReport.createTestReport();
        CreateReport.getTestReport().setTestClasses(new ArrayList<TestReport.TestClass>());
        Log.i(TAG,"onCreate : 创建测试报告对象");
        super.onCreate(arguments);
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        CreateReport.testOutputReport();
        super.finish(resultCode , results);
    }
}
