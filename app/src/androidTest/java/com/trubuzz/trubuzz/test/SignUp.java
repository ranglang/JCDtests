package com.trubuzz.trubuzz.test;

import android.util.Log;

import com.trubuzz.trubuzz.feature.ClassWatcherAdvance;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * Created by king on 2016/9/18.
 */
public class SignUp extends BaseTest{

    @ClassRule
    public static ClassWatcherAdvance c = new ClassWatcherAdvance();
//    @Rule
//    public TestWatcherAdvance t = new TestWatcherAdvance(CreateReport.getTestReport().createTestClass());

    @Test
    public void test(){
        Log.i("jcd", "test: sign up test");
        int i = 5/0;
        isSucceeded = true;
    }

    @Test
    public void twoT(){
        Log.i("jcd", "twoT");
        isSucceeded = true;
    }
}
