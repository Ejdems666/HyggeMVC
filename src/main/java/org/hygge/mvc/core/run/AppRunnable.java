package org.hygge.mvc.core.run;

import org.hygge.mvc.core.controller.Controller;
import org.hygge.mvc.core.router.EndpointReflection;
import org.hygge.mvc.core.run.exceptions.IncorectMethodReturnType;
import org.hygge.mvc.core.run.result.JsonResult;
import org.hygge.mvc.core.run.result.Result;
import org.hygge.mvc.core.run.result.jsp.JspResult;
import org.hygge.mvc.core.run.resulter.JsonResulter;
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
            sendResponse(result);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace(); // should not happen, already checked in EndpointReflection constructor
        } catch (IncorectMethodReturnType | ClassCastException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendResponse(Result result) {
        Resulter resulter;
        if (result instanceof JspResult) {
            resulter = new JspResulter((JspResult) result, request, response);
        } else if (result instanceof JsonResult) {
            resulter = new JsonResulter(result, response);
        } else {
            return;
        }
        resulter.returnResultInResponse();
    }
}
