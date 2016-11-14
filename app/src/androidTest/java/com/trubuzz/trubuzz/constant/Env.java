package com.trubuzz.trubuzz.constant;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

/**
 * Created by king on 2016/9/26.
 * 定义一些环境常量
 */

public final class Env {
    public final static String packageName = "com.trubuzz.trubuzz";
    public final static String filesDir = "/data/data/"+packageName + "/files/";
    public final static String suiteRegisterKey = "SUITE_REGISTER_KEY";

    public final static Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    public final static UiDevice uiDevice = UiDevice.getInstance(instrumentation);



}
