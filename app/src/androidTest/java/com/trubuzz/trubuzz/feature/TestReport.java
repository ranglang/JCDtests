package com.trubuzz.trubuzz.feature;

import com.trubuzz.trubuzz.utils.TestResult;

import java.util.List;
import java.util.Map;

/**
 * Created by king on 2016/9/21.
 * 这是一个测试报告的javaBean
 */

public class TestReport {

    private String TestName;
    private List<TestClass> testClasses;
    private long spendTime;


    /*** getter setter ***/
    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public List<TestClass> getTestClasses() {
        return testClasses;
    }

    public void setTestClasses(List<TestClass> testClasses) {
        this.testClasses = testClasses;
    }

    public long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(long spendTime) {
        this.spendTime = spendTime;
    }

    /**
     * 创建TestClass实例
     * @return
     */
    public TestClass createTestClass(){
        return new TestClass();
    }
    /****内部实现****/
    class TestClass{
        private String testClassName;
        private List<TestCase> testCases;
        private long spendTime;

        /*** getter setter ***/
        public String getTestClassName() {
            return testClassName;
        }

        public TestClass setTestClassName(String testClassName) {
            this.testClassName = testClassName;
            return this;
        }

        public List<TestCase> getTestCases() {
            return testCases;
        }

        public TestClass setTestCases(List<TestCase> testCases) {
            this.testCases = testCases;
            return this;
        }

        public long getSpendTime() {
            return spendTime;
        }

        public void setSpendTime(long spendTime) {
            this.spendTime = spendTime;
        }

        /**
         * 创建TestCase 实例
         * @return
         */
        public TestCase createTestCase(){
            return new TestCase();
        }
        class TestCase{
            private String caseName;
            private TestResult testResult;
            private String errorMsg;
            private String imageName;
            private Map useData;
            private String[] stackTraces;
            private String localizedMessage;
            private long spendTime;

            /*** getter setter ***/
            public String getCaseName() {
                return caseName;
            }

            public TestCase setCaseName(String caseName) {
                this.caseName = caseName;
                return this;
            }

            public TestResult getTestResult() {
                return testResult;
            }

            public TestCase setTestResult(TestResult testResult) {
                this.testResult = testResult;
                return this;
            }

            public String getErrorMsg() {
                return errorMsg;
            }

            public TestCase setErrorMsg(String errorMsg) {
                this.errorMsg = errorMsg;
                return this;
            }

            public String getImageName() {
                return imageName;
            }

            public TestCase setImageName(String imageName) {
                this.imageName = imageName;
                return  this;
            }

            public Map getUseData() {
                return useData;
            }

            public TestCase setUseData(Map useData) {
                this.useData = useData;
                return this;
            }

            public String[] getStackTraces() {
                return stackTraces;
            }

            public TestCase setStackTraces(String[] stackTraces) {
                this.stackTraces = stackTraces;
                return this;
            }

            public String getLocalizedMessage() {
                return localizedMessage;
            }

            public TestCase setLocalizedMessage(String localizedMessage) {
                this.localizedMessage = localizedMessage;
                return this;
            }

            public long getSpendTime() {
                return spendTime;
            }

            public TestCase setSpendTime(long spendTime) {
                this.spendTime = spendTime;
                return this;
            }
        }
    }
}
