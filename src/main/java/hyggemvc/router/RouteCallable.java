package hyggemvc.router;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by adam on 25/02/2017.
 */
public class RouteCallable {
    private final String controllerName;
    private final String methodName;
    private final String moduleName;
    private Class<?> controllerClass;
    private Method method;
    private Object[] parameters;

    public RouteCallable(
            String packageName, String controllerName, String methodName, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        moduleName = "";
        this.controllerName = controllerName;
        this.methodName = methodName;
        this.parameters = parameters;
        prepareCallable(packageName, parameterTypes);
    }

    private void prepareCallable(String packageName, Class<?>[] parameterTypes) throws ClassNotFoundException, NoSuchMethodException {
        String fullControllerName = packageName + "." + controllerName + "Controller";
        controllerClass = Class.forName(fullControllerName);
        method = controllerClass.getDeclaredMethod(methodName, parameterTypes);
    }

    public RouteCallable(
            String packageName, Map<String, RouteElement> callableElements, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        moduleName = callableElements.get("module").getUrlValue();
        if (moduleName != null) {
            packageName += "." + moduleName;
        }
        controllerName = Notator.ucFirst(callableElements.get("controller").getUrlValue());
        methodName = callableElements.get("method").getUrlValue();
        this.parameters = parameters;
        prepareCallable(packageName, parameterTypes);

    }

    public static RouteCallable notFoundCallable(String packageName, Exception exception) {
        try {
            return new RouteCallable(
                    packageName,
                    "Error",
                    "notFound",
                    new Class<?>[]{Exception.class},
                    new Object[]{exception}
            );
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            // If 404 is not present, frameworks default 404 is called
            try {
                return new RouteCallable(
                        "hyggemvc.controller",
                        "Error",
                        "notFound",
                        new Class<?>[]{Exception.class},
                        new Object[]{e}
                );
            } catch (ClassNotFoundException | NoSuchMethodException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }


    public Controller callRoute(HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = controllerClass.getConstructor(HttpServletRequest.class, HttpServletResponse.class);
        Controller controller = ((Controller) constructor.newInstance(request, response));
        controller.setModuleName(moduleName);
        controller.setControllerName(controllerName);
        controller.setMethodName(methodName);
        method.invoke(controller, parameters);
        return controller;
    }

}
