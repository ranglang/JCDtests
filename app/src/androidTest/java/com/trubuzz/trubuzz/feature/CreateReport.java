package com.trubuzz.trubuzz.feature;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.trubuzz.trubuzz.utils.DoIt;

/**
 * Created by king on 2016/9/21.
 */

public class CreateReport {
    private static TestReport testReport = null;


    public static TestReport getTestReport() {
        return testReport;
    }

    /**
     * 确保 testReport 为单例
     * 在整个测试开始时调用 {@link AdRunner#onCreate(Bundle)}
     */
    public static synchronized void createTestReport() {
        if (testReport == null){
            testReport = new TestReport();
        }
    }

    /**
     * 将测试结果写文件
     * { 在 @link AdRunner#finish(int, Bundle) 中进行调用}
     */
    public static void testOutputReport(){
        String str = JSON.toJSONString(testReport);
        DoIt.writeFileData(str,"report");
    }
}
