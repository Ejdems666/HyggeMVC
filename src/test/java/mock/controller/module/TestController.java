package mock.controller.module;

import org.hygge.hyggemvc.controller.Controller;
import org.hygge.hyggemvc.run.result.Result;
import org.hygge.hyggemvc.run.result.StringResult;

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
