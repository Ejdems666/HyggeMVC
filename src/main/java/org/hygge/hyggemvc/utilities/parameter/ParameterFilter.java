package org.hygge.hyggemvc.utilities.parameter;

import org.hygge.hyggemvc.utilities.parameter.exception.ParameterParserException;
import org.hygge.hyggemvc.utilities.parameter.exception.RequiredParameterNonExistentException;
import org.hygge.hyggemvc.utilities.parameter.type.*;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 19/04/2017.
 */
public class ParameterFilter {
    private Map<String, Parameter> parameters = new HashMap<>();

    public ParameterMask<Integer> addInteger(String key) {
        IntegerParameter parameter = new IntegerParameter(key);
        parameters.put(key, parameter);
        return parameter;
    }

    public ParameterMask<String> addString(String key) {
        StringParameter parameter = new StringParameter();
        parameters.put(key, parameter);
        return parameter;
    }

    public ParameterMask<Boolean> addBoolean(String key) {
        BooleanParameter parameter = new BooleanParameter(key);
        parameters.put(key, parameter);
        return parameter;
    }

    public ParsedParameters parseParameters(ServletRequest request) throws ParameterParserException {
        ParsedParameters parsedParameters = new ParsedParameters();
        for (Map.Entry<String, Parameter> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String sendValue = request.getParameter(key);
            Parameter parameter = entry.getValue();
            if (sendValueIsNotEmpty(sendValue)) {
                parameter.parseSendValue(sendValue);
            } else if (parameter.getDefaultValue() == null && parameter.isRequired()) {
                throw new RequiredParameterNonExistentException(key);
            }
            parsedParameters.addParameter(key,parameter);
        }
        return parsedParameters;
    }

    private boolean sendValueIsNotEmpty(String sendValue) {
        return sendValue != null && !sendValue.isEmpty();
    }
}
