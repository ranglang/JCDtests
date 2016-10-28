package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.report.Report;
import com.trubuzz.trubuzz.report.SuiteBean;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Date;

import static com.trubuzz.trubuzz.constant.Env.suiteRegisterKey;

/**
 * Created by king on 16/10/28.
 */

public class BaseSuite {
    private static String descSuite ;
    private static Class suiteClass;
    private static SuiteBean testSuite;
    private static long startTime ;

    @BeforeClass
    public static void setup(){
        startTime = new Date().getTime();

        testSuite =  new SuiteBean();
        testSuite.setSuiteName(descSuite);
        testSuite.setSuiteClassName(suiteClass.toString());
        Registor.reg(suiteRegisterKey , testSuite);
    }
    @AfterClass
    public static void teardown(){
        Registor.unReg(suiteRegisterKey);
        testSuite.setSpendTime(new Date().getTime() - startTime);
        Report.getReport().getSuiteBean().addChildSuite(testSuite);

        descSuite = null;
        suiteClass = null;
        startTime = 0;
        testSuite = null;
    }

    protected static void init(String childDescSuite, Class childSuiteClass) {
        descSuite = childDescSuite;
        suiteClass = childSuiteClass;
    }
}
