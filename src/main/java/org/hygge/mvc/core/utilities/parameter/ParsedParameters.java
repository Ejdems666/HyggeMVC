package org.hygge.mvc.core.utilities.parameter;

import org.hygge.mvc.core.utilities.parameter.type.ParsedParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 19/04/2017.
 */
public class ParsedParameters {
    private Map<String, Object> parameters = new HashMap<>();

    public void addParameter(String key, ParsedParameter parameter) {
        parameters.put(key, getValue(parameter));
    }

    private Object getValue(ParsedParameter parameter) {
        if (parameter.getValue() == null) {
            return parameter.getDefaultValue();
        }
        return parameter.getValue();
    }

    public String getString(String key) {
        return ((String) parameters.get(key));
    }

    public Integer getInteger(String key) {
        return ((Integer) parameters.get(key));
    }

    public Boolean getBoolean(String key) {
        return ((Boolean) parameters.get(key));
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
