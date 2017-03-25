package hyggemvc.router;

import hyggemvc.router.exceptions.DefaultNameOfCallableInUrlException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adam on 12/03/2017.
 * Identifies module, controller, method and parameters in url according to regex route
 */
public class RouteMatcher {
    private final Route route;
    private List<Class<?>> parameterTypes = new ArrayList<>();
    private List<Object> parameters = new ArrayList<>();
    private Matcher matcher;

    public RouteMatcher(String url, Route route) {
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
                    addParameter(String.class,rawParameter);
                } catch (IllegalArgumentException e1) {
                    break; // if there are no parameters with current identifier, there will be none with a higher one
                }
            }
        }
    }

    private String getMatchedGroup(String name) {
        String rawGroup =  matcher.group(name);
        if (rawGroup != null && rawGroup.charAt(0) == '/') {
            return rawGroup.substring(1);
        }
        return rawGroup;
    }

    private void addParameter(Class<?> type, Object parameter) {
        parameterTypes.add(type);
        parameters.add(parameter);
    }

    public boolean matches() {
        return matcher.matches();
    }

    public String extractControllerName() throws DefaultNameOfCallableInUrlException {
        String controller = getMatchedGroup("controller");
        if (controller == null) {
            return route.getDefaultController();
        } else {
            controller = Notator.toCamelCase(controller);
            if (Notator.ucFirst(controller).equals(route.getDefaultController())) {
                throw new DefaultNameOfCallableInUrlException(controller, "Controller");
            }
        }
        return controller;
    }

    public String extractMethodName() throws DefaultNameOfCallableInUrlException {
        String method = getMatchedGroup("method");
        if (method == null) {
            return route.getDefaultMethod();
        } else {
            method = Notator.toCamelCase(method);
            if (method.equals(route.getDefaultMethod())) {
                throw new DefaultNameOfCallableInUrlException(method, "Method");
            }
        }
        return method;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes.toArray(new Class<?>[parameterTypes.size()]);
    }

    public Object[] getParameters() {
        return parameters.toArray();
    }
}