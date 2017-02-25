package hyggemvc.router;

/**
 * Created by adam on 25/02/2017.
 */
public interface Route {
    String getAssembledControllerClass();

    void setErrorRoute(Exception e, String errorType);

    void setControllerName(String controllerName);

    String getMethodName();

    void setMethodName(String methodName);

    Object getArgument();

    void setArgument(Object argument);
}
