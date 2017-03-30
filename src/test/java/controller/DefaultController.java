package controller;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 26/02/2017.
 */
public class DefaultController extends Controller {
    public DefaultController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public String called = null;

    public void index(){
        called = "index";
    }

    public void index(Integer number){
        called = "index"+number;
    }

    public void defaultTest(){
        called = "defaultTest";
    }
}
