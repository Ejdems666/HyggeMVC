package org.hygge.hyggemvc.utilities.parameter.exception;

/**
 * Created by adam on 19/04/2017.
 */
public class RequiredParameterNonExistentException extends ParameterParserException {
    public RequiredParameterNonExistentException(String parameterName) {
        super("Required parameter " + parameterName + " was not send or was empty");
    }
}
