package org.hygge.mvc.core.run.exceptions;

import org.hygge.mvc.core.router.EndpointReflection;
import org.hygge.mvc.core.run.result.Result;

/**
 * Created by adam on 01/09/2017.
 */
public class IncorectMethodReturnType extends Exception {
    public IncorectMethodReturnType(EndpointReflection reflection) {
        super("Method [" + reflection.getMethod() +
                "] in controller [" + reflection.getControllerClass() +
                "] did not return object of type: [" + Result.class + "]");
    }
}
