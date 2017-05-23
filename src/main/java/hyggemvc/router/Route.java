package hyggemvc.router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adam on 10/03/2017.
 */
public class Route {
    private String pattern;
    private Map<String, CallableElement> callableElements = new HashMap<>();
    private CallableElementsHolder callableElementsHolder;

    public Route(String pattern, String defaultController, String defaultMethod) {
        this.pattern = pattern;
        callableElementsHolder = new CallableElementsHolder(defaultController, defaultMethod);
    }

    public Route(String pattern, String defaultController, String defaultMethod, String defaultModel) {
        this.pattern = pattern;
        callableElementsHolder = new CallableElementsHolder(defaultController, defaultMethod, defaultModel);
    }

    public String getPattern() {
        return pattern;
    }

    public CallableElementsHolder getCallableElementsHolder() {
        return callableElementsHolder;
    }
}
