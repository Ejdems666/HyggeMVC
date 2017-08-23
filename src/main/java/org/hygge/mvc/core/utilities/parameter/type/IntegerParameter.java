package org.hygge.mvc.core.utilities.parameter.type;


import org.hygge.mvc.core.utilities.parameter.exception.InvalidParameterTypeException;

/**
 * Created by adam on 24/05/2017.
 */
public class IntegerParameter extends GeneralMask<Integer> implements Parameter {
    private Integer defaultValue = null;
    private Integer value;
    private String key;

    public IntegerParameter(String key) {
        this.key = key;
    }


    @Override
    public void parseSendValue(String sendValue) throws InvalidParameterTypeException {
        try {
            value = Integer.parseInt(sendValue);
        } catch (NumberFormatException e) {
            throw new InvalidParameterTypeException(Integer.class, key, sendValue);
        }
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Integer getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public ParameterMask setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

}
