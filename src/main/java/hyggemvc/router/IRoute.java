package hyggemvc.router;

/**
 * Created by adam on 25/02/2017.
 */
public interface IRoute {
    String getControllerClass();

    void setErrorRoute(Exception e, String errorType);

    void setController(String controllerName);

    String getMethodName();

    void setMethod(String methodName);

    Object getArgument();

    void setArgument(Object argument);

    void setControllerPackage(String controllerPackage);
}
