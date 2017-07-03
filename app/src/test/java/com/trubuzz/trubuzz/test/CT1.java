package com.trubuzz.trubuzz.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.javascript.host.Map;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junitparams.custom.ParametersProvider;

/**
 * Created by king on 2017/6/30.
 */

public class CT1 implements ParametersProvider<CParameters> {
    private CParameters parametersAnnotation;
    String p_s = "~&";
    @Override
    public void initialize(CParameters parametersAnnotation) {
        this.parametersAnnotation = parametersAnnotation;
    }

    @Override
    public Object[] getParameters() {
        String[] values = parametersAnnotation.value();
        List res = new ArrayList();
        for(String value : values) {
            if (value.startsWith("{")) {
                String val;
                if(value.endsWith("}")) val = value;
                else val = formatString(value, "}" ,p_s);
                HashMap map = JSON.parseObject(val, HashMap.class);
                res.add(map);
            }else
            if (value.startsWith("[")) {
                String val;
                if(value.endsWith("]")) val = value;
                else val = formatString(value, "]" ,p_s);
                ArrayList list = JSON.parseObject(val, ArrayList.class);
                res.add(list);
            }else {
                res.add(value);
            }
        }
        if (values.length == 1) {
            return new Object[]{res.get(0)};
        }
        return new List[]{res};
    }

    private String formatString(String s, String end ,String p_s) {
        int i = s.indexOf(p_s);
        if (i <= 0) {
            // 若在结尾标识出现后后面仍有字符 , 则将多余字符忽略
            return s.substring(0, s.indexOf(end) + 1);
        }
        // 验证参数标志前为结尾标识 否则忽略结尾标识后的字符
        String c = String.valueOf(s.charAt(i-1));
        if (end.equals(c)) {
            String[] values = s.split(p_s);
            // 若参数为空 , 则将多余字符忽略
            if (values.length < 2) {
                return s.substring(0, s.indexOf(end) + 1);
            }
            String[] ss = values[1].split(",");
            String format = String.format(values[0], ss);
            return format;
        }
        return s.substring(0, s.indexOf(end) + 1);
    }
}
