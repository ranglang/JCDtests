package com.trubuzz.trubuzz.suite.subset;

import com.trubuzz.trubuzz.suite.BaseSuite;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.NounsTest;
import com.trubuzz.trubuzz.test.trade.tests.TradeTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 2017/7/10.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        NounsTest.class,
})
public class AssetSuite extends BaseSuite{
    private static final String suiteDesc = "asset test ";
    private static final Class suiteClass = AssetSuite.class;

    @BeforeClass
    public static void setup(){
        BaseSuite.init(suiteDesc  , suiteClass);
        BaseSuite.baseSetup();
    }
}
