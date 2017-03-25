package hyggemvc.router;

/**
 * Created by adam on 10/03/2017.
 */
public class Route {
    private String pattern;
    private final String defaultController;
    private final String defaultMethod;

    public Route(String pattern, String defaultController, String defaultMethod) {
        this.pattern = pattern;
        this.defaultController = defaultController;
        this.defaultMethod = defaultMethod;
    }

    public String getPattern() {
        return pattern;
    }

    public String getDefaultController() {
        return defaultController;
    }

    public String getDefaultMethod() {
        return defaultMethod;
    }
}
