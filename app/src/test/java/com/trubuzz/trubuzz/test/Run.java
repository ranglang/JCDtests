package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.utils.Param;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by king on 2016/10/20.
 */

public class Run {

    @Test
    public void run() throws NoSuchMethodException {
        Other other = new Other();
        Method method = other.getClass().getMethod("testParamA" , String.class , int.class);
        System.out.println(Arrays.toString(getParamsName(method)));
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

