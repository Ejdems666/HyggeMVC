package org.hygge.hyggemvc.router;

import org.hygge.hyggemvc.router.exceptions.DefaultNameOfCallableInUrlException;
import org.hygge.hyggemvc.utilities.Notator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adam on 12/03/2017.
 * Identifies testmodule, controller, method and parameters in url according to regex route
 */
public class UrlMatcher {
    private final Route route;
    private List<Class<?>> parameterTypes = new ArrayList<>();
    private List<Object> parameters = new ArrayList<>();
    private Matcher matcher;

    public UrlMatcher(String url, Route route) {
        Pattern regex = Pattern.compile(route.getPattern());
        matcher = regex.matcher(url);
        this.route = route;
        if (matcher.matches()) {
            processParameters();
        }
    }

    private void processParameters() {
        String rawParameter;
        Object parameter;
        for (int i = 0; i < matcher.groupCount(); i++) {
            try {
                rawParameter = getMatchedGroup("int" + i);
                parameter = Integer.parseInt(rawParameter);
                addParameter(Integer.class, parameter);
            } catch (IllegalArgumentException e) {
                try {
                    rawParameter = getMatchedGroup("string" + i);
                    addParameter(String.class, rawParameter);
                } catch (IllegalArgumentException e1) {
                    break; // if there are no parameters with current identifier, there will be none with a higher one
                }
            }
        }
    }

    private String getMatchedGroup(String name) throws IllegalArgumentException {
        String rawGroup = matcher.group(name);
        if (rawGroup != null && rawGroup.charAt(0) == '/') {
            return rawGroup.substring(1);
        }
        return rawGroup;
    }

    private void addParameter(Class<?> type, Object parameter) {
        parameterTypes.add(type);
        parameters.add(parameter);
    }

    public void extractCallableElements() throws DefaultNameOfCallableInUrlException {
        CallableElementsHolder callableElements = route.getCallableElementsHolder();
        processCallableElement(callableElements.getController());
        processCallableElement(callableElements.getMethod());
        if (callableElements.getModule() != null) {
            processCallableElement(callableElements.getModule());
        }
    }

    private void processCallableElement(CallableElement callableElement) throws DefaultNameOfCallableInUrlException {
        String urlElement;
        try {
            urlElement = getMatchedGroup(callableElement.getRegexGroup());
        } catch (IllegalArgumentException e) {
            return;
        }
        if (urlElement != null) {
            urlElement = Notator.toCamelCase(urlElement);
            if (urlElement.equals(callableElement.getDefaultValue())) {
                throw new DefaultNameOfCallableInUrlException(urlElement, callableElement.getRegexGroup());
            } else {
                callableElement.setUrlValue(urlElement);
            }
        }
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes.toArray(new Class<?>[parameterTypes.size()]);
    }

    public Object[] getParameters() {
        return parameters.toArray();
    }

    public boolean matches() {
        return matcher.matches();
    }
}