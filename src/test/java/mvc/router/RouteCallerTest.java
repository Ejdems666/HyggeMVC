package mvc.router;

import mvc.controller.Controller;
import mvc.controller.ErrorController;
import org.apache.http.HttpRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by adam on 25/02/2017.
 */
class RouteCallerTest {
    private RouteCaller routeCaller;
    private Route route = new BasicRoute("mvc.controller");
    private HttpRequest httpRequest = new RequestMocUp();

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