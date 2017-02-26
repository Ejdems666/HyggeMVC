package hyggemvc.router;

/**
 * Created by adam on 25/02/2017.
 */
public class BasicRoute implements Route {
    private String controllerPackage;
    private String controllerName = "Default";
    private String methodName = "index";
    private Object argument = null;

    public BasicRoute(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public Object getArgument() {
        return argument;
    }


    @Override
    public void setArgument(Object argument) {
        this.argument = argument;
    }

    @Override
    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    @Override
    public void setControllerName(String controllerClass) {
        this.controllerName = controllerClass;
    }

    @Override
    public String getControllerClass() {
        return controllerPackage + "." + controllerName + "Controller";
    }

    @Override
    public void setErrorRoute(Exception e, String errorType){
        controllerName = "Error";
        methodName = errorType;
        argument = e;
    }
}
