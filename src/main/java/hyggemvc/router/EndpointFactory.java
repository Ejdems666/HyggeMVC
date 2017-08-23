package hyggemvc.router;

import hyggemvc.controller.Controller;
import hyggemvc.run.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by adam on 23/05/2017.
 */
public class EndpointFactory {
    public Result callEndpoint(EndpointReflection reflection, HttpServletRequest request, HttpServletResponse response)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassCastException {
        Controller controller = instantiateController(reflection, request, response);
        Object result = reflection.getMethod().invoke(controller, reflection.getParameters());
        if (result == null) {
            return null;
        }
        return (Result) result;
    }

    private Controller instantiateController(EndpointReflection reflection, HttpServletRequest request, HttpServletResponse response)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = reflection.getControllerClass().getConstructor();
        Controller controller = ((Controller) constructor.newInstance());

        controller.setModuleName(reflection.getModuleName());
        controller.setControllerName(reflection.getControllerName());
        controller.setMethodName(reflection.getMethodName());
        controller.setRequest(request);
        controller.setResponse(response);
        return controller;
    }
}
