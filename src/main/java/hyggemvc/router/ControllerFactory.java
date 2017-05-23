package hyggemvc.router;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by adam on 23/05/2017.
 */
public class ControllerFactory {
    public Controller callController(ControllerReflection callable, HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = callable.getControllerClass()
                .getConstructor(HttpServletRequest.class, HttpServletResponse.class);
        Controller controller = ((Controller) constructor.newInstance(request, response));
        controller.setModuleName(callable.getModuleName());
        controller.setControllerName(callable.getControllerName());
        controller.setMethodName(callable.getMethodName());
        callable.getMethod().invoke(controller, callable.getParameters());
        return controller;
    }
}
