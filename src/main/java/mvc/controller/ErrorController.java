package mvc.controller;

import org.apache.http.HttpRequest;

/**
 * Created by adam on 25/02/2017.
 */
public class ErrorController extends Controller{
    public ErrorController(HttpRequest request) {
        super(request);
    }

    public void notFound(Object arg) {
        return;
    }
}
