package controller.module;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 31/03/2017.
 */
public class DefaultController extends Controller {
    public DefaultController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
    public String called = null;

    public void index(){
        called = "index";
    }
    public void text(){
        called = "text";
    }
}
