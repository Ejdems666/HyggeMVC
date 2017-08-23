package controller;

import hyggemvc.controller.Controller;
import hyggemvc.run.result.Result;
import hyggemvc.run.result.StringResult;

/**
 * Created by adam on 12/03/2017.
 */
public class ApiController extends Controller {

    public Result index(Integer number) {
        return new StringResult("index" + number);
    }
}
