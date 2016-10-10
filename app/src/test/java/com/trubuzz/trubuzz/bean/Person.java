package com.trubuzz.trubuzz.bean;

/**
 * Created by king on 16/10/4.
 */

public class Person {
    private int age;
    private String name;

    public Person(int age) {
        this.age = age;
    }

    public boolean isAdult() {
        if(this.age >=18){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
