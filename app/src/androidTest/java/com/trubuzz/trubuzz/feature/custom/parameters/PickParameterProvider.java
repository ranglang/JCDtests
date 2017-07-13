package com.trubuzz.trubuzz.feature.custom.parameters;

import junitparams.custom.ParametersProvider;

/**
 * Created by king on 2017/7/7.
 */

public class PickParameterProvider implements ParametersProvider<PickParameter> {
    PickParameter parametersAnnotation;
    @Override
    public void initialize(PickParameter parametersAnnotation) {

    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
