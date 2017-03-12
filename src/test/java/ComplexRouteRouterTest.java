import caller.RequestMocUp;
import caller.ResponseMockUp;
import controller.ApiController;
import controller.DefaultController;
import controller.TestController;
import hyggemvc.controller.Controller;
import hyggemvc.router.BasicRouter;
import hyggemvc.router.Route;
import hyggemvc.router.RouteCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by adam on 25/02/2017.
 */
class ComplexRouteRouterTest {

    private Route firstRoute;
    private BasicRouter router;

    @BeforeEach
    void setUp() {
        firstRoute = new Route("(<controller>)?(/<method>)?(/<number>)?", "Default", "index");
        router = new BasicRouter(firstRoute);
    }

    @Test
    void testRouteOfEmptyUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "index");
    }

    @Test
    void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/number/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "number1");
    }

    @Test
    void testRouteWithFixedPrefixNotUsingFirstRouteRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        router.addRoute(new Route("api(/<method>)?(/<number>)?", "Api", "index"));
        RouteCallable routeCallable = router.getRouteCallable("controller", "/api/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ApiController);
        assertEquals(((ApiController) controller).called, "index1");

        routeCallable = router.getRouteCallable("controller", "/test/number/1");
        controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "number1");
    }
}