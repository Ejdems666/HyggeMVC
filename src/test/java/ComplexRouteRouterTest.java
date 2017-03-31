import caller.RequestMocUp;
import caller.ResponseMockUp;
import controller.ApiController;
import controller.DefaultController;
import controller.TestController;
import hyggemvc.controller.Controller;
import hyggemvc.controller.ErrorController;
import hyggemvc.router.Router;
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
        Router router = new Router(getStandartRoute());
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
        Router router = new Router(getStandartRoute());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "index1");
    }

    @Test
    void testRouteWithFixedPrefixNotUsingFirstRouteRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getStandartRoute();
        Router router = new Router(firstRoute);
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
        Router router = new Router(firstRoute);
        router.addRoute(getStandartRoute());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/number/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "number1");
    }

    @Test
    void testRouteWithMultipleDifferentParameters() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getRouteWithMultipleParameters());
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
    void testRouteWithCMSwitchParameterFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchRoutes();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/cmswitch");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "cmswitch");
    }

    @Test
    void testRouteWithCMSwitchSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchRoutes();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/cmswitch");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "cmswitch");
    }

    private Router getRouterWithCMSwitchRoutes() {
        Router router = new Router(new Route("(?<method>[a-z\\-]+)","Default","index"));
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?","Default","index"));
        return router;
    }

    @Test
    void testRouteWithMultipleDifferentParametersWhereNotAllAreFilled() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getRouteWithMultipleParameters());
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/multiple/1");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof ErrorController);
    }

    @Test
    void testRouteWithCMSwitchAndStringParameterFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/string/value");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "stringvalue");
    }

    @Test
    void testRouteWithCMSwitchAndStringParameterSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/string/value");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof TestController);
        assertEquals(((TestController) controller).called, "stringvalue");
    }

    @Test
    void testRouteWithCMSwitchAndStringParameterThird() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/value");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof DefaultController);
        assertEquals(((DefaultController) controller).called, "indexvalue");
    }

    private Router getRouterWithCMSwitchAndStringParameterRoutes() {
        Router router = new Router(new Route("(?<method>[a-z\\-]+)(?<string0>/[a-z\\-]+)?","Default","index"));
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?(?<string0>/[a-z\\-]+)","Default","index"));
        router.addRoute(new Route("(?<string0>[a-z\\-]+)","Default","index"));
        return router;
    }

    @Test
    void testRouteWithModuleFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof controller.module.DefaultController);
        assertEquals(((controller.module.DefaultController) controller).called, "index");
    }

    @Test
    void testRouteWithModuleSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/test/test");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof controller.module.TestController);
        assertEquals(((controller.module.TestController) controller).called, "test");
    }

    @Test
    void testRouteWithModuleThird() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        RouteCallable routeCallable = router.getRouteCallable("controller", "/testmodule/test/test");
        Controller controller = routeCallable.callRoute(new RequestMocUp(), new ResponseMockUp());
        assertTrue(controller instanceof controller.testmodule.TestController);
        assertEquals(((controller.testmodule.TestController) controller).called, "test");
    }

    private Router getRouterWithCmSwitchAndModelRoute() {
        Router router = new Router(
                new Route("(?<method>/[a-z\\-]+)?","Default","index","module")
        );
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?","Default","index","module"));;
        router.addRoute(new Route("(?<module>[a-z]+)(?<controller>/[a-z\\-]+)(?<method>/[a-z\\-]+)?", "Default", "index", "module"));
        return router;
    }
}