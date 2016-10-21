package com.trubuzz.trubuzz.test;

import org.junit.Test;

/**
 * Created by king on 2016/10/20.
 */

public class Run {

    @Test
    public void run(){
        String s = "h   5\na\tb";

        System.out.println(s);
        System.out.println("----------------------------------------");
        System.out.println(s.replaceAll("\\s+", "-"));
        System.out.println("----------------------------------------");
        System.out.println(s.replaceAll("  ", "-"));
        System.out.println("----------------------------------------");
        System.out.println(s.replaceAll("\\x20+" , "-"));
    }
}
