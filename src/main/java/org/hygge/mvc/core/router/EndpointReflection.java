package org.hygge.mvc.core.router;

import org.hygge.mvc.core.utilities.Notator;

import java.lang.reflect.Method;

/**
 * Created by adam on 25/02/2017.
 */
public class EndpointReflection {
    private String controllerName;
    private String methodName;
    private String moduleName = "";
    private Class<?> controllerClass;
    private Method method;
    private Object[] parameters;

    public EndpointReflection(
            String packageName, String controllerName, String methodName, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
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

    public EndpointReflection(
            String packageName, CallableElementsHolder callableElements, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        packageName = setModuleNameAndAddToPackageName(packageName, callableElements.getModule());
        controllerName = Notator.ucFirst(callableElements.getController().getUrlValue());
        methodName = callableElements.getMethod().getUrlValue();
        this.parameters = parameters;
        prepareControllerClassAndMethodReflections(packageName, parameterTypes);

    }

    private String setModuleNameAndAddToPackageName(String packageName, CallableElement callableElementModule) {
        if (callableElementModule != null) {
            moduleName = callableElementModule.getUrlValue();
            packageName += "." + moduleName;
        }
        return packageName;
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
