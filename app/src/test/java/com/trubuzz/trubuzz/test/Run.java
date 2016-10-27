package com.trubuzz.trubuzz.test;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by king on 2016/10/20.
 */

public class Run {

    @Test
    public void run(){
        ArrayList list = new ArrayList();
        String s = new String("hehe");
        list.add(s);
        s = new String("hello");

        System.out.println(list.get(0));
    }
}
