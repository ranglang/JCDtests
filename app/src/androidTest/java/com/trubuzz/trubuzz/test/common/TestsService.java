package com.trubuzz.trubuzz.test.common;

/**
 * Created by king on 17/5/17.
 */

public interface TestsService <T> {
    /**
     * 返回当前测试需要用到的 ToastView
     * 避免多次 new 对象出来
     * @return
     */
    T theToast();
}
