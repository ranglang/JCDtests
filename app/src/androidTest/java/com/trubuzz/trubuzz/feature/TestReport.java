package com.trubuzz.trubuzz.feature;

import com.trubuzz.trubuzz.utils.TestResult;

import java.util.Date;
import java.util.List;

/**
 * Created by king on 2016/9/21.
 */

public class TestReport {

    private String TestName;
    private long testStartTime = new Date().getTime();

    private List<TestClass> testClasses;

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
            private String useData;

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

            public String getUseData() {
                return useData;
            }

            public TestCase setUseData(String useData) {
                this.useData = useData;
                return this;
            }
        }
    }
}
