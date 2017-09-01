package org.hygge.mvc.core.mock.controllers;

import org.hygge.mvc.core.controller.Controller;
import org.hygge.mvc.core.controller.annotation.Get;
import org.hygge.mvc.core.controller.annotation.Post;

/**
 * Created by adam on 26/02/2017.
 */
public class TestController extends Controller {

    public void index() {

    }

    public void test() {

    }

    public void number(Integer number) {

    }

    public void multiple(Integer number, String text, Integer number2) {
    }

    public void string(String text) {

    }

    public void cmswitch() {

    }

    @Post
    public void post() {

    }

    @Get
    public void get() {

    }
}
