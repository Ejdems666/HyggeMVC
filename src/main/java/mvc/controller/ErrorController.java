package mvc.controller;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by adam on 25/02/2017.
 */
public class ErrorController extends Controller{
    public ErrorController(HttpServletRequest request) {
        super(request);
    }

    public void notFound(Object arg) {
        return;
    }
}
