package com.trubuzz.trubuzz;

import com.trubuzz.trubuzz.utils.MyWatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.Result;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Rule
    public TestWatcher watcher = new TestWatcher(){
        @Override
        protected void starting(Description description) {
            System.out.println("start .... ");
        }
        @Override
        protected void finished(Description description) {
            System.out.println("finished -----");
        }
        @Override
        protected void failed(Throwable e, Description description) {
            System.out.println("failed ****");
        }
    };

    @Rule
    public MyWatcher myWatcher = new MyWatcher();

    public Result result = new Result();

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(3, 2 + 2);

    }
    @Test
    public void testTrue(){
        assertEquals(true,false);
    }
    @Test
    public void testZero(){
        assertEquals(0,5/0);
    }

    @After
    public void xixi(){
        System.out.println("after ");
        System.out.println(result.wasSuccessful());
    }
    @Before
    public void before(){
        System.out.println("before");
    }
}