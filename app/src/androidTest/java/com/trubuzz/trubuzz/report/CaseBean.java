package com.trubuzz.trubuzz.report;

import com.trubuzz.trubuzz.constant.TestResult;

import java.util.Map;

/**
 * Created by king on 16/10/27.
 */

public class CaseBean {
    private String caseName;
    private TestResult testResult;
    private String errorMsg;
    private String imageName;
    private Map useData;
    private String[] stackTraces;
    private String localizedMessage;
    private long spendTime;

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setUseData(Map useData) {
        this.useData = useData;
    }

    public void setStackTraces(String[] stackTraces) {
        this.stackTraces = stackTraces;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }


    public String getCaseName() {
        return caseName;
    }

    public TestResult getTestResult() {
        return testResult;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getImageName() {
        return imageName;
    }

    public Map getUseData() {
        return useData;
    }

    public String[] getStackTraces() {
        return stackTraces;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public long getSpendTime() {
        return spendTime;
    }
}
