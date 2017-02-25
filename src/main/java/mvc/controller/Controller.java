package mvc.controller;


import com.sun.deploy.net.HttpRequest;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class Controller {
    protected HttpRequest request;

    public Controller(HttpRequest request) {
        this.request = request;
    }
}
