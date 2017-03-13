import caller.RequestMocUp;
import caller.ResponseMockUp;
import controller.ApiController;
import controller.TestController;
import hyggemvc.controller.Controller;
import hyggemvc.router.BasicRouter;
import hyggemvc.router.Route;
import hyggemvc.router.RouteCallable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by adam on 25/02/2017.
 */
class ComplexRouteRouterTest {

    @Test
    void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = new Route("(<controller>)?(/<method>)?(/<number>)?", "Default", "index");
        BasicRouter router = new BasicRouter(firstRoute);
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/number/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "number1");
    }

    @Test
    void testRouteWithFixedPrefixNotUsingFirstRouteRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = new Route("(<controller>)?(/<method>)?(/<number>)?", "Default", "index");
        BasicRouter router = new BasicRouter(firstRoute);
        router.addRoute(new Route("api(/<method>)?(/<number>)?", "Api", "index"));
        RouteCallable routeCallable = router.getRouteCallable("controller", "/api/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ApiController);
        assertEquals(((ApiController) controller).called, "index1");
    }

    @Test
    void testRouteStandardRouteNotBeanFirstRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = new Route("api(/<method>)?(/<number>)?", "Api", "index");
        BasicRouter router = new BasicRouter(firstRoute);
        router.addRoute(new Route("(<controller>)?(/<method>)?(/<number>)?", "Default", "index"));
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/number/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "number1");
    }

    @Test
    void testRouteWithMultipleDifferentParameters() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BasicRouter router = new BasicRouter(getRouteWithMultipleParameters());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/multiple/1/text/2");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "multiple1text2");
    }

    private Route getRouteWithMultipleParameters() {
        return new Route("(<controller>)?(/<method>)?(/<number>)?(/<string>)?(/<number>)?", "Default", "index");
    }

    @Test
    void testRouteWithMultipleDifferentParametersWhereNotAllAreFilled() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BasicRouter router = new BasicRouter(getRouteWithMultipleParameters());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/multiple/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "multiple1nullnull");
    }
}