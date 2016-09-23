package com.trubuzz.trubuzz;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by king on 2016/9/23.
 */

public class T2 {

    @Test
    public  void test() throws Exception {
        Try t = new T1();
        Field[] a = t.getClass().getSuperclass().getFields();

        Field.setAccessible(a,true);
        System.out.println(a.length);
        for(Field field : a) {
            System.out.println(field.get(t));
        }
    }
}
