import caller.RequestMocUp;
import caller.ResponseMockUp;
import controller.DefaultController;
import controller.TestController;
import hyggemvc.controller.Controller;
import hyggemvc.router.Router;
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
class SwitchedRouteRouterTest {

    private Route firstRoute;
    private Router router;

    @BeforeEach
    void setUp() {
        firstRoute = new Route("(?<method>[a-z\\-]+)?(?<controller>/[a-z\\-]+)?", "Default", "index");
        router = new Router(firstRoute);
    }

    @Test
    void testRouteOfEmptyUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "index");
    }

    @Test
    void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/test");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "test");
    }

    @Test
    void testRouteOfUrlWithEmptyControllerAndFullMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/default-test");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "defaultTest");
    }
}