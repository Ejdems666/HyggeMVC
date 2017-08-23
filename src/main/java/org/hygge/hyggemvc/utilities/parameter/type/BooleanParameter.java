package org.hygge.hyggemvc.utilities.parameter.type;

import org.hygge.hyggemvc.utilities.parameter.exception.InvalidParameterTypeException;

/**
 * Created by adam on 24/05/2017.
 */
public class BooleanParameter extends GeneralMask<Boolean> implements Parameter {
    private Boolean defaultValue = null;
    private Boolean value = false;
    private String key;

    public BooleanParameter(String key) {
        this.key = key;
    }

    @Override
    public ParameterMask setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }


    @Override
    public void parseSendValue(String sendValue) throws InvalidParameterTypeException {
        if (sendValue.equals("0")) {
            value = false;
        } else if (sendValue.equals("1")) {
            value = true;
        } else {
            throw new InvalidParameterTypeException(Boolean.class, key, sendValue);
        }
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public Boolean getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
