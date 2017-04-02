package controller;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 12/03/2017.
 */
public class ApiController extends Controller {
    public ApiController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public String called;

    public void index(Integer number) {
        called = "index"+number;
    }
}
