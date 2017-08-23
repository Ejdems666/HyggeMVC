package org.hygge.hyggemvc.utilities.parameter.type;

/**
 * Created by adam on 24/05/2017.
 */
public interface ParsedParameter<K> {
    K getValue();
    K getDefaultValue();
}
