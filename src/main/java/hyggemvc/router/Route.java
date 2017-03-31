package hyggemvc.router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 10/03/2017.
 */
public class Route {
    private String pattern;
    private Map<String,RouteElement> callableElements = new HashMap<>();

    public Route(String pattern, String defaultController, String defaultMethod) {
        this.pattern = pattern;
        callableElements.put("controller",new RouteElement(Notator.lcFirst(defaultController)));
        callableElements.put("method",new RouteElement(defaultMethod));
        callableElements.put("module",new RouteElement(null));
    }

    public Route(String pattern, String defaultController, String defaultMethod, String defaultModel) {
        this.pattern = pattern;
        callableElements.put("controller",new RouteElement(Notator.lcFirst(defaultController)));
        callableElements.put("method",new RouteElement(defaultMethod));
        callableElements.put("module",new RouteElement(defaultModel));
    }

    public String getPattern() {
        return pattern;
    }

    public Map<String, RouteElement> getCallableElements() {
        return callableElements;
    }
}
