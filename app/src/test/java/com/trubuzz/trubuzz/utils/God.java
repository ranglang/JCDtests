package com.trubuzz.trubuzz.utils;

/**
 * Created by king on 2017/8/1.
 */

public class God {

    public static String getIntervalString(String str, char start, char stop) {
        char[] chars = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        boolean canStart = false;
        for (char ch : chars) {
            if (ch == start) {
                canStart = true;
                continue;
            }
            if (ch == stop) {
                return new String(sbf);
            }
            if (canStart) {
                sbf.append(ch);
            }
        }
        return new String(sbf);
    }
}
