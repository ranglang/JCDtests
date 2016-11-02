package com.trubuzz.trubuzz.suite;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Created by king on 16/10/27.
 */

public abstract class Su {
    private static String string ;

    @BeforeClass
    public static void aa(){
        System.out.println("oooooooooooooooooo  " + string);
    }
    @AfterClass
    public static void bb(){
        System.out.println("xxxxxxxxxxxxxxxxxxxxxx  " + string);
    }

    public static void init(String s){
        string = s;
    }

}
