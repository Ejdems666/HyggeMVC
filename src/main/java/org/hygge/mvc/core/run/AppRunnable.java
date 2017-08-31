package org.hygge.mvc.core.run;

import org.hygge.mvc.core.mock.controller.Controller;
import org.hygge.mvc.core.router.EndpointReflection;
import org.hygge.mvc.core.run.result.Result;
import org.hygge.mvc.core.run.result.jsp.JspResult;
import org.hygge.mvc.core.run.resulter.JspResulter;
import org.hygge.mvc.core.run.resulter.Resulter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by adam on 21/02/2017.
 */
public class AppRunnable {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public AppRunnable(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    //TODO: master try catch block would come here
    public void run(EndpointReflection endpointReflection) {
        try {
            ControllerFactory controllerFactory = new ControllerFactory();
            Controller controller = controllerFactory.setupControllerObject(endpointReflection, request, response);
            EndpointInvoker invoker = new EndpointInvoker();
            Result result = invoker.invokeEndpoint(controller, endpointReflection);
            Resulter resulter = getResulter(result);
            if (resulter != null) {
                resulter.returnResultInResponse();
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace(); // should not happen
        } catch (ClassCastException e) {
            System.out.println(
                    "Method " + endpointReflection.getMethod() +
                            " in controller " + endpointReflection.getControllerClass() +
                            " did not return object of type: " + Result.class
            );
        }
    }

    private Resulter getResulter(Result result) {
        Resulter resulter;
        if (result instanceof JspResult) {
            resulter = new JspResulter((JspResult) result, request, response);
        } else {
            return null;
        }
        return resulter;
    }
}
