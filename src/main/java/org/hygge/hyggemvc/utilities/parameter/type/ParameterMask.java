package org.hygge.hyggemvc.utilities.parameter.type;

/**
 * Created by adam on 24/05/2017.
 */
public interface ParameterMask<K> {
    public ParameterMask setDefaultValue(K defaultValue);

    public ParameterMask setRequired();

    public ParameterMask setRequired(boolean required);
}
