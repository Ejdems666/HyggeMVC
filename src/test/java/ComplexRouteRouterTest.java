import caller.RequestMocUp;
import caller.ResponseMockUp;
import controller.ApiController;
import controller.DefaultController;
import controller.TestController;
import hyggemvc.controller.Controller;
import hyggemvc.controller.ErrorController;
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
        BasicRouter router = new BasicRouter(getStandartRoute());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/number/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "number1");
    }

    private Route getStandartRoute() {
        return new Route(
                "(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?(?<int0>(/)?\\d+)?",
                "Default",
                "index"
        );
    }

    @Test
    void testRouteOnlyWithNumber() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BasicRouter router = new BasicRouter(getStandartRoute());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "index1");
    }

    @Test
    void testRouteWithFixedPrefixNotUsingFirstRouteRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getStandartRoute();
        BasicRouter router = new BasicRouter(firstRoute);
        router.addRoute(getApiRoute());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/api/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ApiController);
        assertEquals(((ApiController) controller).called, "index1");
    }

    private Route getApiRoute() {
        return new Route(
                "api(?<method>/[a-z\\-]+)?(?<int0>/\\d+)?",
                "Api",
                "index"
        );
    }

    @Test
    void testRouteStandardRouteNotBeingFirstRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getApiRoute();
        BasicRouter router = new BasicRouter(firstRoute);
        router.addRoute(getStandartRoute());
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
        return new Route(
                "(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?(?<int0>/\\d+)?(?<string1>/\\w+)?(?<int2>/\\d+)?",
                "Default",
                "index"
        );
    }

    @Test
    void testRouteWithMultipleDifferentParametersWhereNotAllAreFilled() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BasicRouter router = new BasicRouter(getRouteWithMultipleParameters());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/multiple/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ErrorController);
    }
}