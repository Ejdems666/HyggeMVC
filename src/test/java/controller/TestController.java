package controller;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 26/02/2017.
 */
public class TestController extends Controller {
    public TestController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public String called = null;

    public void index() {
        called = "index";
    }
    public void test() {
        called = "test";
    }
    public void number(Integer number) {
        called = "number"+number;
    }
    public void multiple(Integer number,String text,Integer number2) {
        called = "multiple"+number+text+number2;
    }
}
