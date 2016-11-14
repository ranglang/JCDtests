package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.lisnen.DemoEvent;
import com.trubuzz.trubuzz.lisnen.DemoListener;
import com.trubuzz.trubuzz.lisnen.DemoListener1;
import com.trubuzz.trubuzz.lisnen.DemoSource;

import org.junit.Test;

/**
 * Created by king on 2016/10/20.
 */

public class Run {

    @Test
    public void run(){
        try{
            DemoSource ds = new DemoSource();
            //将监听器在事件源对象中登记：
            DemoListener listener1 = new DemoListener1();
            ds.addDemoListener(listener1);
            ds.addDemoListener(new DemoListener() {
                public void handleEvent(DemoEvent event) {
                    System.out.println("Method come from 匿名类...");
                }
            });
            ds.notifyDemoEvent();//触发事件、通知监听器
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

