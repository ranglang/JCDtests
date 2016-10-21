package com.trubuzz.trubuzz.utils;

/**
 * Created by king on 2016/10/20.
 */

public class DoSomething {

    public static String string2ASCII(String string){
        String newString = "";
        char[] chars = string.toCharArray();
        for(char c: chars){
            newString = newString + Integer.valueOf(c) + ",";
        }
        return newString;
    }

    public static String ASCII2String(String ascii){
        StringBuffer sbu = new StringBuffer();
        String [] strings = ascii.split(",");
        for(String s : strings){
            sbu.append((char) Integer.parseInt(s));
        }
        return sbu.toString();
    }
}
