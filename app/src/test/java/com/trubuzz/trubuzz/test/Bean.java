package com.trubuzz.trubuzz.test;

/**
 * Created by king on 16/11/7.
 */

public class Bean {
    public final int age = 18;
    public final String name = "xiaxia";


    public static class FirstBean extends Bean{
        public final int fAge = age + 20;
        public final String fName = name.replace("x" ,"v");
    }

    public static class SBean extends Bean{
        public final int fAge = age - 10;
        public final String fName = name.replace("i" ,"w");
    }
}
