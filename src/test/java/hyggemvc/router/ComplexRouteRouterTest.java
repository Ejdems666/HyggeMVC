package hyggemvc.router;

import caller.RequestMocUp;
import caller.ResponseMockUp;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;

/**
 * Created by adam on 25/02/2017.
 */
public class ComplexRouteRouterTest {

    private RequestMocUp request;
    private ResponseMockUp response;

    @BeforeMethod
    public void setUp() {
        request = new RequestMocUp();
        response = new ResponseMockUp();
    }

    @Test
    public void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getStandartRoute());
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/number/1");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "number");
        assertEquals(controllerReflection.getParameters()[0], 1);
    }

    private Route getStandartRoute() {
        return new Route(
                "(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?(?<int0>(/)?\\d+)?",
                "Default",
                "index"
        );
    }

    @Test
    public void testRouteOnlyWithNumber() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getStandartRoute());
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/1");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "index");
        assertEquals(controllerReflection.getParameters()[0], 1);
    }

    @Test
    public void testRouteWithFixedPrefixNotUsingFirstRouteRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getStandartRoute();
        Router router = new Router(firstRoute);
        router.addRoute(getApiRoute());
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/api/1");
        assertEquals(controllerReflection.getControllerName(), "Api");
        assertEquals(controllerReflection.getMethodName(), "index");
        assertEquals(controllerReflection.getParameters()[0], 1);
    }

    private Route getApiRoute() {
        return new Route(
                "api(?<method>/[a-z\\-]+)?(?<int0>/\\d+)?",
                "Api",
                "index"
        );
    }

    @Test
    public void testRouteStandardRouteNotBeingFirstRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getApiRoute();
        Router router = new Router(firstRoute);
        router.addRoute(getStandartRoute());
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/number/1");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "number");
        assertEquals(controllerReflection.getParameters()[0], 1);
    }

    @Test
    public void testRouteWithMultipleDifferentParameters() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getRouteWithMultipleParameters());
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/multiple/1/text/2");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "multiple");
        assertEquals(controllerReflection.getParameters()[0], 1);
        assertEquals(controllerReflection.getParameters()[1], "text");
        assertEquals(controllerReflection.getParameters()[2], 2);
    }

    private Route getRouteWithMultipleParameters() {
        return new Route(
                "(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?(?<int0>/\\d+)?(?<string1>/\\w+)?(?<int2>/\\d+)?",
                "Default",
                "index"
        );
    }

    @Test
    public void testRouteWithCMSwitchParameterFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchRoutes();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/cmswitch");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "cmswitch");
    }

    @Test
    public void testRouteWithCMSwitchSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchRoutes();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/cmswitch");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "cmswitch");
    }

    private Router getRouterWithCMSwitchRoutes() {
        Router router = new Router(new Route("(?<method>[a-z\\-]+)", "Default", "index"));
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)", "Default", "index"));
        return router;
    }

    @Test
    public void testRouteWithMultipleDifferentParametersWhereNotAllAreFilled() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getRouteWithMultipleParameters());
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/multiple/1");
        assertEquals(controllerReflection.getControllerName(), "Error");
        assertEquals(controllerReflection.getMethodName(), "notFound");
    }

    @Test
    public void testRouteWithCMSwitchAndStringParameterFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/string/value");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "string");
        assertEquals(controllerReflection.getParameters()[0], "value");
    }

    @Test
    public void testRouteWithCMSwitchAndStringParameterSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/string/value");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "string");
        assertEquals(controllerReflection.getParameters()[0], "value");
    }

    @Test
    public void testRouteWithCMSwitchAndStringParameterThird() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/value");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "index");
        assertEquals(controllerReflection.getParameters()[0], "value");
    }

    private Router getRouterWithCMSwitchAndStringParameterRoutes() {
        Router router = new Router(new Route("(?<method>[a-z\\-]+)(?<string0>/[a-z\\-]+)?", "Default", "index"));
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?(?<string0>/[a-z\\-]+)", "Default", "index"));
        router.addRoute(new Route("(?<string0>[a-z\\-]+)", "Default", "index"));
        return router;
    }

    @Test
    public void testRouteWithModuleFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/");
        assertEquals(controllerReflection.getModuleName(), "module");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteWithModuleSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/test");
        assertEquals(controllerReflection.getModuleName(), "module");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "test");
    }

    @Test
    public void testRouteWithModuleThird() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/testmodule/test/test");
        assertEquals(controllerReflection.getModuleName(), "testmodule");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "test");
    }

    private Router getRouterWithCmSwitchAndModelRoute() {
        Router router = new Router(
                new Route("(?<method>/[a-z\\-]+)?", "Default", "index", "module")
        );
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?", "Default", "index", "module"));
        ;
        router.addRoute(new Route("(?<module>[a-z]+)(?<controller>/[a-z\\-]+)(?<method>/[a-z\\-]+)?", "Default", "index", "module"));
        return router;
    }

    @Test
    public void testSpecificRoute() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(
                new Route("test", "Test", "test")
        );
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "test");
    }
}