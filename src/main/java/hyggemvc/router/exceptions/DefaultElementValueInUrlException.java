package hyggemvc.router.exceptions;

/**
 * Created by adam on 12/03/2017.
 */
public class DefaultElementValueInUrlException extends Exception{
    public DefaultElementValueInUrlException(String element, String type) {
        super("Default value "+element+" of element type "+ type +" was detected in url." +
                " This leads to duplicate endpoints and therefore results in error.");
    }
}
