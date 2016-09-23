package com.trubuzz.trubuzz.feature;

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
            CreateReport.testReport = new TestReport();
        }
    }
}
