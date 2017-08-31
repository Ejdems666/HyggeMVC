package org.hygge.mvc.core.mock.controller;

import org.hygge.mvc.core.run.result.Result;
import org.hygge.mvc.core.mock.result.StringResult;

/**
 * Created by adam on 26/02/2017.
 */
public class DefaultController extends Controller {

    public Result index() {
        return new StringResult("index");
    }

    public void index(Integer number) {
    }

    public void index(String text) {
    }

    public void defaultTest() {
    }

    public void string(String text) {
    }

    public void cmswitch() {
    }
}
