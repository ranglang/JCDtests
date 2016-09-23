package com.trubuzz.trubuzz.feature;

import android.util.Log;

import org.junit.rules.TestName;
import org.junit.runner.Description;

/**
 * Created by king on 2016/9/20.
 */

public class ClassWatcherAdvance extends TestName {
    private final String TAG = "jcd_ClassWatcher";

    private String testClassName;


    /**
     * Invoked when a test is about to start
     */
    protected void starting(Description description) {
        this.testClassName = description.getClassName();
        Log.i(TAG, "starting: ...."+ testClassName);
    }

    /**
     * Invoked when a test method finishes (whether passing or failing)
     */
    protected void finished(Description description) {
        Log.i(TAG, "finished: ....");
    }


    public String getTestClassName() {
        return testClassName;
    }

}
