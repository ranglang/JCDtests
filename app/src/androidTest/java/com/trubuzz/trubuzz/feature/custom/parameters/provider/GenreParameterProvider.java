package com.trubuzz.trubuzz.feature.custom.parameters.provider;

import com.trubuzz.trubuzz.feature.custom.parameters.GenreParameter;
import com.trubuzz.trubuzz.test.signUp.tests.SignUpReverseTest;

import org.junit.runners.model.FrameworkMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junitparams.NullType;
import junitparams.custom.ParametersProvider;

import static com.trubuzz.trubuzz.utils.DoIt.list2array;

/**
 * Created by king on 2017/7/10.
 */

public class GenreParameterProvider implements ParametersProvider<GenreParameter> {
    GenreParameter parametersAnnotation;

    @Override
    public void initialize(GenreParameter parametersAnnotation , FrameworkMethod frameworkMethod) {
        this.parametersAnnotation = parametersAnnotation;
    }

    @Override
    public Object[] getParameters() {
        String[] values = parametersAnnotation.value();
        Class[] ts = parametersAnnotation.type();
        int typeLen = ts.length;
        if (typeLen == 1 && ts[0] == NullType.class) {
            return values;
        }

        List res = new ArrayList();
        for (String val : values) {
            String[] ps = val.split(",");
            if (ps.length != typeLen) {
                res.add(ps);
            }else{
                Object[] o = new Object[typeLen];
                for(int i=0;i<typeLen; i++) {
                    if (ts[i] == Integer.class) {
                        o[i] = Integer.valueOf(ps[i]);
                        continue;
                    }
                    if (ts[i] == String.class) {
                        o[i] = ps[i];
                        continue;
                    }
                    if (ts[i] == Float.class) {
                        o[i] = Float.valueOf(ps[i]);
                        continue;
                    }
                    if (ts[i] == Double.class) {
                        o[i] = Double.valueOf(ps[i]);
                        continue;
                    }
                    if (ts[i] == Long.class) {
                        o[i] = Long.valueOf(ps[i]);
                        continue;
                    }
                    if (ts[i] == BigDecimal.class) {
                        o[i] = new BigDecimal(ps[i]);
                        continue;
                    }
                    if (ts[i].isEnum()) {
                        o[i] = Enum.valueOf(ts[i], ps[i]);
                        continue;
                    }
                    o[i] = (Object) ps[i];
                    System.out.println("jcd_没有合适的类型 : "+ ts[i]);
                }
                res.add(o);
            }
        }
        return list2array(res);
    }
}
