package hyggemvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 26/02/2017.
 */
public class TestController extends Controller{
    public TestController(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public void index() {

    }
}
