package hyggemvc.router;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by adam on 23/05/2017.
 */
public class EndpointFactory {
    public Controller callEndpoint(EndpointReflection reflection, HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<?> constructor = reflection.getControllerClass()
                .getConstructor(HttpServletRequest.class, HttpServletResponse.class);
        Controller controller = ((Controller) constructor.newInstance(request, response));
        controller.setModuleName(reflection.getModuleName());
        controller.setControllerName(reflection.getControllerName());
        controller.setMethodName(reflection.getMethodName());
        reflection.getMethod().invoke(controller, reflection.getParameters());
        return controller;
    }
}
