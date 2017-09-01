package org.hygge.mvc.core.router.exceptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by adam on 01/09/2017.
 */
public class IncorrectRequestMethod extends Exception {
    public IncorrectRequestMethod(Method method, Class<? extends Annotation> requestMethod) {
        super("Matched endpoint method "+method+" was annotated with "+requestMethod+" which does not correlate with http method of the request");
    }
}
