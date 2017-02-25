package hyggemvc.router;

import hyggemvc.controller.Controller;
import hyggemvc.controller.ErrorController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by adam on 25/02/2017.
 */
class RouteCallerTest {
    private RouteCaller routeCaller;
    private Route route = new BasicRoute("hyggemvc.controller");
    private HttpServletRequest httpRequest = new RequestMocUp();

    @BeforeEach
    void setUp() {
        routeCaller = new RouteCaller(route,httpRequest);
    }

    @Test
    void callRoute() {
        route.setErrorRoute(new Exception("test"),"notFound");
        Controller controller = routeCaller.callRoute();
        Assertions.assertTrue(controller instanceof ErrorController);
    }
}