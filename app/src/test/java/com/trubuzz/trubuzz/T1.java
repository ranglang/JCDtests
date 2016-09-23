package com.trubuzz.trubuzz;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by king on 2016/9/23.
 */

public class T1 extends Try {


    @Test
    public void test() throws Exception {
        super.age = 20;
        super.sayAge();
        aa();
    }

    public void sayName(){

    }
    public void aa() throws Exception {
//        Field[] a = this.getClass().getSuperclass().getDeclaredFields();
//        Field.setAccessible(a,true);
//        System.out.println(a.length);
//        for(Field field : a) {
//            System.out.println(field.getName());
//        }

        Method[] m = this.getClass().getSuperclass().getDeclaredMethods();
        Method.setAccessible(m,true);
        for(Method method :m){
            System.out.println(method.getName());
            Type[] type0 = new Type[]{String.class,T2.class};
            Type[] type = method.getGenericParameterTypes();
            if(type.equals(type0)){
                System.out.println("hello");
            }

            for(Type t : type){
                if(t.equals( String.class)){
                    System.out.println("string....");
                }
                System.out.println(t);
            }
        }
    }
}
