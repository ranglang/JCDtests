package com.trubuzz.trubuzz.feature.custom.parameters;

import junitparams.custom.ParametersProvider;

/**
 * Created by king on 2017/7/7.
 */

public class ValuesParameterProvider implements ParametersProvider<ValuesParameter> {
    private ValuesParameter parametersAnnotation;

    @Override
    public void initialize(ValuesParameter parametersAnnotation) {
        this.parametersAnnotation = parametersAnnotation;
    }

    @Override
    public Object[] getParameters() {
        String[] values = parametersAnnotation.value();
        return values;
    }
}
