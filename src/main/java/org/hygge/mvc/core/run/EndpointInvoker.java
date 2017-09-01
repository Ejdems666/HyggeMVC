package org.hygge.mvc.core.run;

import org.hygge.mvc.core.controller.Controller;
import org.hygge.mvc.core.router.EndpointReflection;
import org.hygge.mvc.core.run.exceptions.IncorectMethodReturnType;
import org.hygge.mvc.core.run.result.Result;

import java.lang.reflect.InvocationTargetException;

/**
 * Invokes the controller methods.
 * The whole controller method flow is here
 */
public class EndpointInvoker {
    public Result invokeEndpoint(Controller controller, EndpointReflection reflection)
            throws InvocationTargetException, IllegalAccessException, IncorectMethodReturnType {
        controller.beforeEndpointCall();
        Object result = reflection.getMethod().invoke(controller, reflection.getParameters());
        controller.afterEndpointCall();
        try {
            return (Result) result;
        }
        catch (ClassCastException e) {
            throw new IncorectMethodReturnType(reflection);
        }

    }
}
