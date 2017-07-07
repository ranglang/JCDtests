package com.trubuzz.trubuzz.test;

import com.alibaba.fastjson.JSON;
import com.trubuzz.trubuzz.bean.ImageS;
import com.trubuzz.trubuzz.bean.Person;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.custom.CustomParameters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by king on 16/10/4.
 */
@RunWith(JUnitParamsRunner.class)
public class PT1 {
    private static final HashMap m = (HashMap) createMap("abc", 123);
    public static final String s = "";
    public static final String p_s = "~&";

    @Test
    @Parameters({
//            "17   ,   ''",
            "22, ~      ~" ,
            "22, ' or 1=1--" ,
    })
    public void personIsAdult(int age, String img) throws Exception {
        String s = img.replaceAll("~", "");
        System.out.println(s + " | " + age);
    }


//    @Test
    @CParameters({"{n: ,m:''}","{n:20,m:'Jack'}"})
    public void personIsNotAdult(List<HashMap> list) throws Exception {
        for(HashMap map:list) {
            for (Object o : map.keySet()) {
                System.out.println(String.format("%s : %s ; ", o, map.get(o)));
                System.out.println(String.format("key class = %s ; value class =  %s "
                        , o.getClass(), map.get(o).getClass()));
            }
        }
    }
//    @Test
    @CParameters("{n:20,m:'Jack'}")
    public void mapTest(HashMap map) throws Exception {
        for (Object o : map.keySet()) {
            System.out.println(String.format("%s : %s ; ", o, map.get(o)));
            System.out.println(String.format("key class = %s ; value class =  %s "
                    , o.getClass(), map.get(o).getClass()));
        }
    }
//    @Test
    @CParameters("[12,'11']")
    public void listTest(List list) throws Exception {
        for (Object o : list) {
            System.out.println(String.format("%s  ; ", o));
            System.out.println(String.format("class: %s ", o.getClass()));
        }
    }
//    @Test
    @CParameters({"[,%s]"+p_s+"222",})
    public void listListTest(List<List> list) throws Exception {
        for(List map:list) {
            for (Object o : map) {
                System.out.println(String.format("%s  ; ", o));
                System.out.println(String.format("class: %s ", o.getClass()));
            }
        }
    }

    public static <K, V> Map<K, V> createMap(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
