package com.trubuzz.trubuzz.report;

import java.util.List;

/**
 * Created by king on 16/10/27.
 */

public class ClassBean {
    private String testClassName;
    private List<CaseBean> testCases;
    private long spendTime;

    public ClassBean(String testClassName, List<CaseBean> testCases, long spendTime) {
        this.testClassName = testClassName;
        this.testCases = testCases;
        this.spendTime = spendTime;
    }

    public ClassBean() {
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public void setTestCases(List<CaseBean> testCases) {
        this.testCases = testCases;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public List<CaseBean> getTestCases() {
        return testCases;
    }

    public long getSpendTime() {
        return spendTime;
    }
}
