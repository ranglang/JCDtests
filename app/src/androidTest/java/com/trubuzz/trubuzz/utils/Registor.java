package com.trubuzz.trubuzz.utils;

import java.util.Stack;

/**
 * Created by king on 2016/9/9.
 */
public class Registor <T> {

    private Stack<T> obj = new Stack<T>();

    public Registor(T t){
        obj.push(t);
    }
    public Registor(){}

    public void reg(T t){
        obj.push(t);
    }
    public T unReg(){
        return obj.pop();
    }


}
