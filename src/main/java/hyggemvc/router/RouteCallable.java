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
    private Class<?> controllerClass;
    private Method method;
    private Object[] parameters;

    public RouteCallable(
            String packageName, String controllerName, String methodName, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        controllerClass = Class.forName(getFullControllerName(packageName, controllerName));
        method = controllerClass.getDeclaredMethod(methodName, parameterTypes);
        this.parameters = parameters;
    }

    public RouteCallable(
            String packageName, Map<String,RouteElement> callableElements, Class<?>[] parameterTypes, Object[] parameters
    ) throws ClassNotFoundException, NoSuchMethodException {
        String module = callableElements.get("module").getUrlValue();
        if (module != null) {
            packageName += "."+module;
        }
        String controller = Notator.ucFirst(callableElements.get("controller").getUrlValue());
        String method = callableElements.get("method").getUrlValue();
        controllerClass = Class.forName(getFullControllerName(packageName, controller));
        this.method = controllerClass.getDeclaredMethod(method, parameterTypes);
        this.parameters = parameters;
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

    private String getFullControllerName(String packageName, String controllerName) {
        return packageName + "." + controllerName + "Controller";
    }

    public Controller callRoute(HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = controllerClass.getConstructor(HttpServletRequest.class, HttpServletResponse.class);
        Controller controller = ((Controller) constructor.newInstance(request, response));
        method.invoke(controller, parameters);
        return controller;
    }

}
