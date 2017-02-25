package mvc.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class Controller {
    protected HttpServletRequest request;

    public Controller(HttpServletRequest request) {
        this.request = request;
    }
}
