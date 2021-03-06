package org.hygge.mvc.core.router.exceptions;

/**
 * Created by adam on 12/03/2017.
 */
public class DefaultNameOfCallableInUrlException extends Exception{
    public DefaultNameOfCallableInUrlException(String element, String type) {
        super("Default value "+element+" of element type "+ type +" was detected in url." +
                " This leads to duplicate endpoints and therefore results in error.");
    }
}
