package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.suite.subset.AssetSuite;
import com.trubuzz.trubuzz.suite.subset.TradeSuite;
import com.trubuzz.trubuzz.suite.subset.UserSuite;
import com.trubuzz.trubuzz.test.NounsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserSuite.class,
//        TradeSuite.class
//        AssetSuite.class
})
public class EngineSuite {
}
