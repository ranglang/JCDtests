package com.trubuzz.trubuzz.suite;

import com.trubuzz.trubuzz.report.Report;
import com.trubuzz.trubuzz.report.SuiteBean;
import com.trubuzz.trubuzz.test.LoginTest;
import com.trubuzz.trubuzz.utils.Registor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.Date;

import static com.trubuzz.trubuzz.constant.Env.suiteRegisterKey;

/**
 * Created by king on 16/10/27.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class
})
public class SuiteA {

    private static SuiteBean testSuite;
    private static final String descSuite = "login test";
    private static long startTime ;
    @BeforeClass
    public static void setup(){
        startTime = new Date().getTime();

        testSuite =  new SuiteBean();
        testSuite.setSuiteName(descSuite);
        testSuite.setSuiteClassName(SuiteA.class.toString());
        Registor.reg(suiteRegisterKey , testSuite);
    }
    @AfterClass
    public static void teardown(){
        Registor.unReg(suiteRegisterKey);
        testSuite.setSpendTime(new Date().getTime() - startTime);
        Report.getReport().getSuiteBean().addChildSuite(testSuite);
    }
}
