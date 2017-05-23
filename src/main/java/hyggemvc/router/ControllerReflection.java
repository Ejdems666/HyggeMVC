package hyggemvc.router;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by adam on 25/02/2017.
 */
public class ControllerReflection {
    private final String controllerName;
    private final String methodName;
    private final String moduleName;
    private Class<?> controllerClass;
    private Method method;
    private Object[] parameters;

    public ControllerReflection(
            String packageName, String controllerName, String methodName, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        moduleName = "";
        this.controllerName = controllerName;
        this.methodName = methodName;
        this.parameters = parameters;
        prepareControllerClassAndMethodReflections(packageName, parameterTypes);
    }

    /**
     * Creates and checks reflections for controller class and method
     */
    private void prepareControllerClassAndMethodReflections(String packageName, Class<?>[] parameterTypes)
            throws ClassNotFoundException, NoSuchMethodException {
        String fullControllerName = packageName + "." + controllerName + "Controller";
        controllerClass = Class.forName(fullControllerName);
        method = controllerClass.getDeclaredMethod(methodName, parameterTypes);
    }

    public ControllerReflection(
            String packageName, Map<String, RouteElement> callableElements, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        moduleName = callableElements.get("module").getUrlValue();
        if (moduleName != null) {
            packageName += "." + moduleName;
        }
        controllerName = Notator.ucFirst(callableElements.get("controller").getUrlValue());
        methodName = callableElements.get("method").getUrlValue();
        this.parameters = parameters;
        prepareControllerClassAndMethodReflections(packageName, parameterTypes);

    }

    public String getControllerName() {
        return controllerName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
