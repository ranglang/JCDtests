//package com.trubuzz.trubuzz.parameterName;
//
//import org.junit.Test;
//import org.springframework.core.DefaultParameterNameDiscoverer;
//import org.springframework.core.MethodParameter;
//import org.springframework.core.ParameterNameDiscoverer;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.method.support.InvocableHandlerMethod;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by king on 2016/10/10.
// */
//
//public class SpringGetParam {
//
//    @Test
//    public void aaa() throws NoSuchMethodException {
//        PN1 pn1 = new PN1();
//        HandlerMethod handlerMethod = new HandlerMethod(pn1 ,pn1.getMethod() );
//
//        MethodParameter[] parameters = handlerMethod.getMethodParameters();
//        for(MethodParameter parameter : parameters){
//            System.out.println(parameter.getParameterName());
////            Object[] objects = getMethodArgumentValues(null ,new ModelAndViewContainer());
//        }
//    }
//
//    private List<String> springGetParamNames(Object obj ,Method testMethod){
//        List<String> paramNames = new ArrayList<String>();
//        ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
//
//        InvocableHandlerMethod handlerMethod = new InvocableHandlerMethod(obj ,testMethod );
//
//        MethodParameter[] parameters = handlerMethod.getMethodParameters();
//        for(int i=0; i<parameters.length; i++){
//            parameters[i].initParameterNameDiscovery(parameterNameDiscoverer);
//            paramNames.add(i ,parameters[i].getParameterName());
//        }
//        return paramNames;
//    }
//}
