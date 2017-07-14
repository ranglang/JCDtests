package com.trubuzz.trubuzz.feature.custom.parameters;

import com.trubuzz.trubuzz.feature.custom.parameters.provider.YmlParameterProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import junitparams.custom.CustomParameters;

/**
 * Created by king on 2017/7/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@CustomParameters(provider = YmlParameterProvider.class)
public @interface YmlParameter {

    /**
     * 指定 yml 文件的文件名 , 需加上扩展名
     *      不要写入路径
     * @return
     */
    String value();

    /**
     * 指定当前测试类中 作为缓存使用的变量名称
     *      需使用 static Object 定义
     *      后面解析将使用反射 通过这里给定的名称找到
     * 若使用 default ,即没有使用缓存
     *      则可能多次读取 yml 资源文件 , 造成资源开销 .
     *      但如果该类中只有一个方法需要使用 yml 资源作为参数 , 则无需使用缓存.
     * @return
     */
    String cacheStore() default "";
}
