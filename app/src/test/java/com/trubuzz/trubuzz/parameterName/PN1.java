package com.trubuzz.trubuzz.parameterName;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by king on 16/10/9.
 */

public class PN1 {
    public String name = "xiaoming";

    @Test
    public void run() throws NoSuchMethodException, IOException {
        long start = new Date().getTime();
        for(String name : Classes.getMethodParamNames(this.getClass(),getMethod())){
            System.out.println(name);
        }
        System.out.println(new Date().getTime()-start);

        long start1 = new Date().getTime();
        long stop1 = new Date().getTime();

        System.out.println(stop1-start1);

    }


    public void param1(int a ,String b){
        System.out.println("a = "+a+" >> " );
        System.out.println("b = "+b+" >> "+b.getClass() );

    }

    public Method getMethod() throws NoSuchMethodException {
        Class clz = this.getClass();
        return clz.getMethod("param1" , int.class ,String.class);
    }
}
