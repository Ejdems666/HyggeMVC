package controller.module;

import hyggemvc.controller.Controller;
import hyggemvc.run.result.Result;
import hyggemvc.run.result.StringResult;

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
