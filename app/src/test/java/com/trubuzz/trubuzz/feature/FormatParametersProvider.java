package com.trubuzz.trubuzz.feature;

import junitparams.custom.ParametersProvider;

/**
 * Created by king on 2016/10/8.
 */
public class FormatParametersProvider implements ParametersProvider<FormatParameters> {
    private FormatParameters formatParameters ;

    @Override
    public void initialize(FormatParameters parametersAnnotation) {
        this.formatParameters = parametersAnnotation;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
