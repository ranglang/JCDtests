package com.trubuzz.trubuzz.constant;

import com.trubuzz.trubuzz.constant.enumerate.Condition;

import java.math.BigDecimal;

/**
 * Created by king on 2016/10/19.
 */

public class Config {
    // app 的运行环境[DEV ,CN ,GLOBAL]
    public static final Condition condition = Condition.DEV;
    public static final String hasBrokerUser = "zhao.deng@jucaidao.com";
    public static final String hasBrokerPwd = "aA123456";
    public static final String notBrokerUser = "abc@abc.com";
    public static final String notBrokerPwd = "aA123321";
    public static final String tradePwd = "1234";

    public static final String reportName = "聚财道 app 2.0 Android 自动化测试报告";

    public static final String cacheHeadImage = "head.png";

    public static final int VISIBILITY = 90;
    public static final String watchlistKey = "name";

    public static final BigDecimal usMinFee = new BigDecimal("5");
    public static final BigDecimal usMinFee_G = new BigDecimal("6.99");
    public static final BigDecimal usPerFee = new BigDecimal("0.01");
    public static final BigDecimal usMaxFee = new BigDecimal("0.006");
    public static final BigDecimal hkMinFee = new BigDecimal("40");
    public static final BigDecimal hkPerFee = new BigDecimal("0.0014");
}
