package hyggemvc.router;

/**
 * Created by adam on 10/03/2017.
 */
public class Route {
    private String pattern;
    private CallableElementsHolder callableElementsHolder;

    public Route(String pattern, String defaultController, String defaultMethod) {
        this.pattern = indexMethodParametersInPattern(pattern);
        callableElementsHolder = new CallableElementsHolder(defaultController, defaultMethod);
    }

    public Route(String pattern, String defaultController, String defaultMethod, String defaultModel) {
        this.pattern = indexMethodParametersInPattern(pattern);
        callableElementsHolder = new CallableElementsHolder(defaultController, defaultMethod, defaultModel);
    }
    private String indexMethodParametersInPattern(String pattern) {
        int order = 0;
        while (true) {
            int indexOfString = pattern.indexOf("<string>");
            int indexOfInt = pattern.indexOf("<int>");
            if (indexOfString >= 0) {
                if (indexOfInt >= 0) {
                    if (indexOfString < indexOfInt) {
                        pattern = pattern.replaceFirst("<string>","<string"+(order++)+">");
                    } else {
                        pattern = pattern.replaceFirst("<int>","<int"+(order++)+">");
                    }
                } else {
                    pattern = pattern.replaceFirst("<string>","<string"+(order++)+">");
                }
            } else if (indexOfInt >= 0) {
                pattern = pattern.replaceFirst("<int>","<int"+(order++)+">");
            } else {
                break;
            }
        }
        return pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public CallableElementsHolder getCallableElementsHolder() {
        return callableElementsHolder;
    }
}
