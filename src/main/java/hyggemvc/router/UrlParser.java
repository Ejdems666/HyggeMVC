package hyggemvc.router;

import hyggemvc.router.exceptions.DefaultElementValueInUrlException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adam on 12/03/2017.
 */
public class UrlParser {
    private Integer controllerGroup = null;
    private Integer methodGroup = null;
    private Map<Integer,Class<?>> parameterTypes = new HashMap<>();
    private List<Object> parameters = new ArrayList<>();
    private String pattern;
    private Matcher matcher;
    private final Route route;

    public UrlParser(String url, Route route) {
        pattern = detectAndReplaceRouteElementsInPattern(route.getPattern());
        Pattern regex = Pattern.compile(pattern);
        matcher = regex.matcher(url);
        this.route = route;
        if (matcher.matches()) {
            processParameters();
        }
    }

    private String detectAndReplaceRouteElementsInPattern(String pattern) {
        String[] patternParts = pattern.split("/");
        int group = 1;
        for (int i = 0; i < patternParts.length; i++) {
            if (patternParts[i].contains(Route.Element.CONTROLLER.toString())) {
                controllerGroup = group++;
                pattern = pattern.replace(Route.Element.CONTROLLER.toString(), "[a-z\\-]+");
            }
            if (patternParts[i].contains(Route.Element.METHOD.toString())) {
                methodGroup = group++;
                pattern = pattern.replace(Route.Element.METHOD.toString(), "[a-z\\-]+");
            }
            if (patternParts[i].contains(Route.Element.NUMBER.toString())) {
                parameterTypes.put(group++,Integer.class);
                pattern = pattern.replace(Route.Element.NUMBER.toString(), "\\d+");
            }
            if (patternParts[i].contains(Route.Element.STRING.toString())) {
                parameterTypes.put(group++,String.class);
                pattern = pattern.replace(Route.Element.STRING.toString(), "[A-Za-z0-9\\-\\_]+");
            }
        }
        return pattern;
    }

    private void processParameters() {
        Object attribute;
        Iterator<Map.Entry<Integer, Class<?>>> iterator = parameterTypes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Class<?>> entry = iterator.next();
            attribute = extractAttributeByGroup(entry.getValue(),entry.getKey());
            if (attribute != null) {
                parameters.add(attribute);
            } else {
                iterator.remove();
            }
        }
    }
    private Object extractAttributeByGroup(Class<?> type, int group){
        String rawAttribute = extractElementByGroup(group);
        if (rawAttribute == null) return null;
        if (type.equals(Integer.class)) {
            return Integer.parseInt(rawAttribute);
        }
        return rawAttribute;
    }

    public boolean matches() {
        return matcher.matches();
    }

    public String extractControllerName() throws DefaultElementValueInUrlException {
        String controller = extractElementByGroup(controllerGroup);
        if (controllerGroup == null || controller == null) {
            return route.getDefaultController();
        } else if (controller.equals(route.getDefaultController())) {
            throw new DefaultElementValueInUrlException(controller,"Controller");
        }
        return controller;
    }

    public String extractMethodName() throws DefaultElementValueInUrlException {
        String method = extractElementByGroup(methodGroup);
        if (methodGroup == null || method == null) {
            return route.getDefaultMethod();
        }
        if (method.equals(route.getDefaultController())) {
            throw new DefaultElementValueInUrlException(method,"Method");
        }
        return method;
    }
    private String extractElementByGroup(int group) {
        if (group > 1) {
            String elementWithSlash = matcher.group(group);
            if (elementWithSlash == null) return null;
            return elementWithSlash.substring(1);
        }
        return matcher.group(group);
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes.values().toArray(new Class<?>[parameterTypes.size()]);
    }
    public Object[] getParameters() {
        return parameters.toArray();
    }
}
