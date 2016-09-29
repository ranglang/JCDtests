package com.trubuzz.trubuzz.test;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created by king on 2016/9/29.
 */
@RunWith(Theories.class)
public class Points {
    @DataPoints
    public static int[] data = {1,2,3,};
    @DataPoints
    public static String[] str = {"a","b","c"};

    @Theory
    public void test1(int a,String s){
        System.out.println("int : "+data + " String : "+str);
    }
}
