package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.test.PT2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({PT2.class})
public class Suite3 extends Su{
    static {
        init(Suite3.class.toString());
    }
}
