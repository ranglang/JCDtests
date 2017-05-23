package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.utils.Param;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by king on 2016/10/20.
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Run {
    boolean a;


    @Test
    public void xrun() throws NoSuchMethodException {
        tr(false);

    }

    public void tr(boolean c){
        String str = "demo@qq.com";
        String pattern = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }

    private void random(int max , int min){
        Random r = new Random();
        for (int i = 0;i<20;i++) {
            int s = r.nextInt(max-min+1)+min;
            System.out.println(s);
        }
    }
    public static String conversionScale(long i , int scale){
        char[] chars = new char[scale];
        char ch = 'A';
        char nu = '0';
        long m = 0;  //商
        long y = 0;  //余
        int index = chars.length;
        long c = 0;  //差

        do{
            m = i / scale;
            y = i % scale;
            c = y - 10;
            chars[--index] = (char) (c < 0 ? nu + y : ch + c);
            i = m;
        }while (m!=0);

        return new String(chars ,index ,chars.length-index);
    }

    private void j16(){
        long d = new Date().getTime();
        long l = 31536000000l;
        long y = (366l * 24l * 60l * 60l *1000l) * 47l;
        long m = 31l*24l*60l*60l*1000l*5l;
        System.out.println(l);
        System.out.println("d = "+d);
        System.out.println("y = "+y);
        System.out.println("m = "+m);

        System.out.println(Long.toHexString(d));
        System.out.println(Long.toHexString(y));
        System.out.println(Long.toHexString(m));
    }
    private Map tm(){
        Map map = new HashMap();
        Map map1 = new HashMap();
        map1.put("hello" , 1);
        Map map2 = null;

        map.putAll(map2);
        System.out.println(1);
        return map1;
    }
    private String[] getParamsName(Method method){
        Annotation[][] ass = method.getParameterAnnotations();
        String[] parameterNames = new String[ass.length];
        int i = 0;
        for(Annotation[] as : ass){
            for(Annotation a : as){
                if (a instanceof Param){
                    parameterNames[i++] = ((Param)a).value();
                }
            }
        }
        return parameterNames;
    }
}

