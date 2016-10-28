package com.trubuzz.trubuzz.report;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.trubuzz.trubuzz.constant.Config;
import com.trubuzz.trubuzz.constant.DeviceDesc;
import com.trubuzz.trubuzz.utils.DoIt;

/**
 * Created by king on 16/10/27.
 */

public class Report {
    private static Report report;
    private String logcatPath;
    private String reportName;
    private long spendTime;
    private long testDate;
    private SuiteBean suiteBean;
    private DeviceDesc deviceDesc;

    public static synchronized Report getReport(){
        if(report == null) report = new Report();
        return report;
    }

    /**
     * 将测试结果写文件
     * {@link com.trubuzz.trubuzz.feature.AdRunner#finish(int, Bundle)} ) 中进行调用}
     */
    public void testOutputReport(){
        initDesc();
        String str = JSON.toJSONString(report);
        DoIt.writeFileData(str,"report");
    }

    /**
     * 初始化一些描述数据
     */
    private void initDesc(){
        this.reportName = Config.reportName;
        this.deviceDesc = DeviceDesc.getDeviceDesc();
        this.logcatPath = null;

    }

    private Report(){}
    public String getLogcatPath() {
        return logcatPath;
    }

    public void setLogcatPath(String logcatPath) {
        this.logcatPath = logcatPath;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    public long getTestDate() {
        return testDate;
    }

    public void setTestDate(long testDate) {
        this.testDate = testDate;
    }

    public SuiteBean getSuiteBean() {
        return suiteBean;
    }

    public void setSuiteBean(SuiteBean suiteBean) {
        this.suiteBean = suiteBean;
    }

    public DeviceDesc getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(DeviceDesc deviceDesc) {
        this.deviceDesc = deviceDesc;
    }
}
