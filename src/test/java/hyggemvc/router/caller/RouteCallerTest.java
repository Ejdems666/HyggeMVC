package hyggemvc.router.caller;

import hyggemvc.controller.Controller;
import hyggemvc.controller.ErrorController;
import hyggemvc.router.BasicRoute;
import hyggemvc.router.Route;
import hyggemvc.router.RouteCaller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adam on 25/02/2017.
 */
class RouteCallerTest {
    private RouteCaller routeCaller;
    private Route route = new BasicRoute("hyggemvc.controller");
    private HttpServletRequest request = new RequestMocUp();
    private HttpServletResponse respose = new ResponseMockUp();

    @BeforeEach
    void setUp() {
        routeCaller = new RouteCaller(route, request, respose);
    }

    @Test
    void callRoute() {
        route.setErrorRoute(new Exception("test"),"notFound");
        Controller controller = routeCaller.callRoute();
        Assertions.assertTrue(controller instanceof ErrorController);
    }
}