package com.trubuzz.trubuzz.lisnen;

/**
 * Created by king on 16/11/14.
 * 假设这是模拟操作过程中出现异常
 */

public class ADV {

    public void pro(boolean b){
        if(b){
            System.out.println(b);
        }else{
            DemoSource ds = new DemoSource();
            //将监听器在事件源对象中登记：
            ds.addDemoListener(new DemoListener() {
                @Override
                public void handleEvent(DemoEvent dm) {

                }
            });
        }
    }
}
