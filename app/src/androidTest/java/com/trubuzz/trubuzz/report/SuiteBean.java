package com.trubuzz.trubuzz.report;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 16/10/27.
 */

public class SuiteBean {
    private String suiteName;
    private List<ClassBean> testClasses;
    private List<SuiteBean> childSuites;
    private long spendTime;
    private String suiteClassName;

    public SuiteBean() {
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public List<ClassBean> getTestClasses() {
        return testClasses;
    }

    public void setTestClasses(List<ClassBean> testClasses) {
        this.testClasses = testClasses;
    }

    /**
     * 初始化再add
     * @param testClass
     * @return
     */
    public ClassBean addTestClasses(ClassBean testClass) {
        if (testClasses == null) testClasses = new ArrayList<ClassBean>();
        this.testClasses.add(testClass);
        return testClass;
    }

    public List<SuiteBean> getChildSuites() {
        return childSuites;
    }

    public void setChildSuites(List<SuiteBean> childSuites) {
        this.childSuites = childSuites;
    }

    /**
     * 初始化再添加
     * @param childSuite
     * @return
     */
    public SuiteBean addChildSuite(SuiteBean childSuite) {
        if (childSuites == null) childSuites = new ArrayList<SuiteBean>();
        childSuites.add(childSuite);
        return childSuite;
    }

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    public String getSuiteClassName() {
        return suiteClassName;
    }

    public void setSuiteClassName(String suiteClassName) {
        this.suiteClassName = suiteClassName;
    }
}