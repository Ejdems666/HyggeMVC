package org.hygge.hyggemvc.utilities.parameter.exception;

/**
 * Created by adam on 19/04/2017.
 */
public class InvalidParameterTypeException extends ParameterParserException {
    public InvalidParameterTypeException(Class<?> type, String key, String actualValue) {
        super("Parameter " + type + " is supposed to be of type " + key +
                " Which is incompatable with actuall value: " + actualValue);
    }
}
