package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.utils.Param;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
        tt();
    }

    public void tt(){
        System.out.println(new BigDecimal("1.3").equals(new BigDecimal("1.20")));  //输出false
        System.out.println(new BigDecimal("1.1").compareTo(new BigDecimal("1.20"))); //输出true

        System.out.println(new BigDecimal(1.2).equals(new BigDecimal("1.2"))); //输出是?
        System.out.println(new BigDecimal(1.2).compareTo(new BigDecimal("1.2")) == 0); //输出是?

        System.out.println(new BigDecimal(1.2).equals(new BigDecimal(1.20))); //输出是?
        System.out.println(new BigDecimal(1.2).compareTo(new BigDecimal(1.20)) == 0);//输出是?

        System.out.println(new BigDecimal(1.20));
        int i = 0;
        String m = "124242131";
        System.out.println(new DecimalFormat(",###.#######").format(new BigDecimal(m)));
        System.out.println(new BigDecimal("111.0").divide(new BigDecimal("0.67") ,new MathContext(17 , RoundingMode.DOWN)));
        System.out.println(m);
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_DOWN));
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_FLOOR));
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_UP));
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_CEILING));
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_HALF_DOWN));
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_HALF_UP));
        System.out.println(new BigDecimal(m).divide(new BigDecimal("1"), i, BigDecimal.ROUND_HALF_EVEN));
    }
    public void tr(){
        String str = "demo@qq.com";
        String pattern = ".*o.?";

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

