package com.trubuzz.trubuzz.feature;

import android.util.Log;

import com.trubuzz.trubuzz.test.LoginTest;
import com.trubuzz.trubuzz.test.SignUpTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 2016/8/24.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SignUpTest.class,
        LoginTest.class
})
public class TestSuit {
    public void test(){
        Log.i("jcd", "test:  hello......");
    }
}
