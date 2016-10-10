package com.trubuzz.trubuzz.test;

import com.trubuzz.trubuzz.bean.Person;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by king on 2016/10/8.
 */
@RunWith(JUnitParamsRunner.class)
public class PT2 {
    @Rule
    public TestName testWatcher = new TestName();

    @Test
    @Parameters(source = PersonProvider.class)
    public void personIsAdult(Person person, boolean valid) {

//        System.out.println(person.toString() + " : > "+ valid);
        assertThat(person.isAdult(), is(valid));
    }

    @After
    public void test(){
        System.out.println(testWatcher.getMethodName());
    }
}
