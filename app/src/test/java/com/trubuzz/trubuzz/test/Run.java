package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.utils.Param;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 2016/10/20.
 */

public class Run {
    boolean a;

    @Test
    public void run() throws NoSuchMethodException {
        System.out.println(a);
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

