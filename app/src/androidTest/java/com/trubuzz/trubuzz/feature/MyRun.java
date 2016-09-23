package com.trubuzz.trubuzz.feature;

import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.internal.util.AndroidRunnerParams;

import org.junit.runners.model.InitializationError;

/**
 * Created by king on 2016/9/23.
 */

public class MyRun extends AndroidJUnit4ClassRunner {
    /**
     * Constructs a new instance of the default runner
     *
     * @param klass
     * @param runnerParams
     */
    public MyRun(Class<?> klass, AndroidRunnerParams runnerParams) throws InitializationError {
        super(klass, runnerParams);
    }
}
