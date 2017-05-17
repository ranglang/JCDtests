package com.trubuzz.trubuzz.test.common;

/**
 * Created by king on 17/5/17.
 */

public class Actions {
    /**
     * 自由校验
     */
    void verify(Something something){
        something.operation();
    }

    /**
     * 自由执行操作
     */
    void fulfill(Something something){
        something.operation();
    }
}
