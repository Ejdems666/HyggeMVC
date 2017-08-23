package org.hygge.mvc.core.mock.controller.module;

import org.hygge.mvc.core.mock.controller.Controller;
import org.hygge.mvc.core.run.result.Result;
import org.hygge.mvc.core.run.result.StringResult;

/**
 * Created by adam on 31/03/2017.
 */
public class TestController extends Controller {

    public Result index() {
        return new StringResult("index");
    }

    public void test() {
    }
}
