package org.hygge.mvc.core.controller;


/**
 * Created by adam on 25/02/2017.
 */
public class ErrorController extends Controller {
    public void notFound(Exception exception) {
        System.out.println("Native 404 was called, no route matched and no ErrorController.notFound was found");
    }
}
