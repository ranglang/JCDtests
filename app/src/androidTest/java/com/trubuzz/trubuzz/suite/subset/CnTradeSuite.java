package com.trubuzz.trubuzz.suite.subset;

import com.trubuzz.trubuzz.suite.BaseSuite;
import com.trubuzz.trubuzz.test.trade.tests.TradeReverseTest;
import com.trubuzz.trubuzz.test.trade.tests.TradeTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by king on 17/6/5.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TradeReverseTest.class,
})
public class CnTradeSuite extends BaseSuite {
    private static final String suiteDesc = "trade reverse test ";
    private static final Class suiteClass = CnTradeSuite.class;

    @BeforeClass
    public static void setup(){
        BaseSuite.init(suiteDesc  , suiteClass);
        BaseSuite.baseSetup();
    }
}
