package com.trubuzz.trubuzz.feature;

import android.util.Log;

import com.trubuzz.trubuzz.test.SignUp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 2016/8/24.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SignUp.class,
//        LoginTest.class
})
public class TestSuit {
    public void test(){
        Log.i("jcd", "test:  hello......");
    }
}
