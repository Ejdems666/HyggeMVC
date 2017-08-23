package mock.controller;

import org.hygge.hyggemvc.controller.Controller;
import org.hygge.hyggemvc.run.result.Result;
import org.hygge.hyggemvc.run.result.StringResult;

/**
 * Created by adam on 12/03/2017.
 */
public class ApiController extends Controller {

    public Result index(Integer number) {
        return new StringResult("index" + number);
    }
}
