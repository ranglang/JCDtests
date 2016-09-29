package com.trubuzz.trubuzz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by king on 2016/9/23.
 */
@RunWith(Parameterized.class)
public class T2 {
    private int a ,b,c;

    public T2(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    @Parameterized.Parameters(name = "{index}:tes({0})-{1}")
    public static Collection initA(){  return Arrays.asList(new Object[][]{{1,2,3}, {3,4,5}});}

    @Test
    public void test1(){System.out.println("hello : "+ (a+b));}
    @Test
    public void hello(){System.out.println(b);}
}
