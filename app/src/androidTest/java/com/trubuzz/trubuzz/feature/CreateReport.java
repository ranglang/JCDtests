package com.trubuzz.trubuzz.feature;

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

    public static synchronized void createTestReport() {
        if (testReport == null){
            testReport = new TestReport();
        }
    }

    public static void testOutputReport(){
        String str = JSON.toJSONString(testReport);
        DoIt.writeFileData(str,"report");
//        System.out.println(str);
    }
}
