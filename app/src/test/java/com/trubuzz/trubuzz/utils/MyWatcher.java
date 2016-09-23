package com.trubuzz.trubuzz.utils;

import org.junit.rules.TestName;
import org.junit.runner.Description;

/**
 * Created by king on 2016/9/20.
 */

public class MyWatcher extends TestName {

    /**
     * Invoked when a test succeeds
     */
    protected void succeeded(Description description) {
        System.out.println(description.getDisplayName());
    }

    /**
     * Invoked when a test fails
     */
    protected void failed(Throwable e, Description description) {
        System.out.println(description.getDisplayName()+" f");
        System.out.println(description.getMethodName()+" f ");
        System.out.println(e.getMessage());
    }
}
