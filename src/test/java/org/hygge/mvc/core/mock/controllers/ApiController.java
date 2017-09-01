package org.hygge.mvc.core.mock.controllers;

import org.hygge.mvc.core.controller.Controller;
import org.hygge.mvc.core.run.result.Result;
import org.hygge.mvc.core.mock.result.StringResult;

/**
 * Created by adam on 12/03/2017.
 */
public class ApiController extends Controller {

    public Result index(Integer number) {
        return new StringResult("index" + number);
    }
}
