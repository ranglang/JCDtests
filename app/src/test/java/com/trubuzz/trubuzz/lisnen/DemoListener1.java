package com.trubuzz.trubuzz.lisnen;

/**
 * Created by king on 16/11/14.
 */

public class DemoListener1 implements DemoListener{
    public void handleEvent(DemoEvent de) {
        System.out.println("Inside listener1...");
        de.print();//回调
    }
}
