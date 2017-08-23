package org.hygge.mvc.core.utilities.parameter.type;

import org.hygge.mvc.core.utilities.parameter.exception.InvalidParameterTypeException;

/**
 * Created by adam on 24/05/2017.
 */
public class StringParameter extends GeneralMask<String> implements Parameter {
    private String defaultValue = null;
    private String value;

    @Override
    public ParameterMask setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public void parseSendValue(String sendValue) throws InvalidParameterTypeException {
        value = sendValue;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
