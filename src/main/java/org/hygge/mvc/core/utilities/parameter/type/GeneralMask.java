package org.hygge.mvc.core.utilities.parameter.type;

/**
 * Created by adam on 24/05/2017.
 */
public abstract class GeneralMask<K> implements ParameterMask<K> {
    protected boolean required;

    @Override
    public ParameterMask setRequired() {
        required = true;
        return this;
    }

    @Override
    public ParameterMask setRequired(boolean required) {
        this.required = required;
        return this;
    }
}
