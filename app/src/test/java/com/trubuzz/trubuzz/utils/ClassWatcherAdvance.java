package com.trubuzz.trubuzz.utils;

import org.junit.rules.TestName;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Date;

/**
 * Created by king on 2016/9/20.
 * 用于创建class 级的测试报告
 */

public class ClassWatcherAdvance extends TestName {
    private final String TAG = "jcd_ClassWatcher";

    private String testClassName;
    private long startTime;
    private long stopTime;

    public Statement apply(final Statement base, final Description description) {
        System.out.println("hello");
        return super.apply(base,description);
    }
    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        this.startTime = new Date().getTime();
        this.testClassName = description.getClassName();

    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected void finished(Description description) {
        this.stopTime = new Date().getTime();

    }


}
