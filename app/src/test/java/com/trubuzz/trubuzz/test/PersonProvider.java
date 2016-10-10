package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.bean.Person;

/**
 * Created by king on 2016/10/8.
 */
public class PersonProvider {

    public static Object[] provideAdults() {
        return new Object[]{
                new Object[]{new Person(25), true},
                new Object[]{new Person(32), true}
        };
    }

    public static Object[] provideTeens() {
        return new Object[]{
                new Object[]{new Person(12), false},
                new Object[]{new Person(17), false}
        };
    }
}
