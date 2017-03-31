import caller.RequestMocUp;
import caller.ResponseMockUp;
import controller.DefaultController;
import controller.TestController;
import hyggemvc.controller.Controller;
import hyggemvc.controller.ErrorController;
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
class BasicRouteRouterTest {

    private Route firstRoute;
    private BasicRouter router;

    @BeforeEach
    void setUp() {
        firstRoute = new Route("(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?", "Default", "index");
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
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "index");
    }

    @Test
    void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/test");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "test");
    }

    @Test
    void testWrongUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/23");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ErrorController);
    }

    @Test
    void testUrlWithDefaultValue() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/default");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ErrorController);
    }

    @Test
    void testUrlWithDefaultValueInMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/index");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ErrorController);
    }
}