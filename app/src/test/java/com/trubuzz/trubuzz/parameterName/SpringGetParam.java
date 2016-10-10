package com.trubuzz.trubuzz.parameterName;

import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

/**
 * Created by king on 2016/10/10.
 */

public class SpringGetParam {

    @Test
    public void aaa() throws NoSuchMethodException {
        PN1 pn1 = new PN1();
        HandlerMethod handlerMethod = new HandlerMethod(pn1 ,pn1.getMethod() );

        MethodParameter[] parameters = handlerMethod.getMethodParameters();
        for(MethodParameter parameter : parameters){
            System.out.println(parameter.getParameterName());
//            Object[] objects = getMethodArgumentValues(null ,new ModelAndViewContainer());
        }
    }
}
