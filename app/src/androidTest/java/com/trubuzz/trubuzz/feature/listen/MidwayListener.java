package com.trubuzz.trubuzz.feature.listen;

/**
 * Created by king on 16/11/14.
 */

public interface MidwayListener {

    void atTestName(EventSource eventSource);
    void atTakeScreenshot(EventSource eventSource);
    void atParameters(EventSource eventSource);
}
