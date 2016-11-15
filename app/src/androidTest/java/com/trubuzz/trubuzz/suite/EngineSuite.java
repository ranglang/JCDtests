package com.trubuzz.trubuzz.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        SuiteA.class,
        SuiteB.class
})
public class EngineSuite {
}
