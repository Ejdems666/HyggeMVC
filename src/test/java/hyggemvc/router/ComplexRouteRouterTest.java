package hyggemvc.router;

import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;

/**
 * Created by adam on 25/02/2017.
 */
public class ComplexRouteRouterTest {

    @Test
    public void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getStandartRoute());
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/number/1");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "number");
        assertEquals(endpointReflection.getParameters()[0], 1);
    }

    private Route getStandartRoute() {
        return new Route(
                "(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?(?<int>(/)?\\d+)?",
                "Default",
                "index"
        );
    }

    @Test
    public void testRouteOnlyWithNumber() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getStandartRoute());
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/1");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "index");
        assertEquals(endpointReflection.getParameters()[0], 1);
    }

    @Test
    public void testRouteWithFixedPrefixNotUsingFirstRouteRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getStandartRoute();
        Router router = new Router(firstRoute);
        router.addRoute(getApiRoute());
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/api/1");
        assertEquals(endpointReflection.getControllerName(), "Api");
        assertEquals(endpointReflection.getMethodName(), "index");
        assertEquals(endpointReflection.getParameters()[0], 1);
    }

    private Route getApiRoute() {
        return new Route(
                "api(?<method>/[a-z\\-]+)?(?<int>/\\d+)?",
                "Api",
                "index"
        );
    }

    @Test
    public void testRouteStandardRouteNotBeingFirstRule() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Route firstRoute = getApiRoute();
        Router router = new Router(firstRoute);
        router.addRoute(getStandartRoute());
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/number/1");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "number");
        assertEquals(endpointReflection.getParameters()[0], 1);
    }

    @Test
    public void testRouteWithMultipleDifferentParameters() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getRouteWithMultipleParameters());
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/multiple/1/text/2");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "multiple");
        assertEquals(endpointReflection.getParameters()[0], 1);
        assertEquals(endpointReflection.getParameters()[1], "text");
        assertEquals(endpointReflection.getParameters()[2], 2);
    }

    private Route getRouteWithMultipleParameters() {
        return new Route(
                "(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?(?<int>/\\d+)?(?<string>/\\w+)?(?<int>/\\d+)?",
                "Default",
                "index"
        );
    }

    @Test
    public void testRouteWithCMSwitchParameterFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchRoutes();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/cmswitch");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "cmswitch");
    }

    @Test
    public void testRouteWithCMSwitchSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchRoutes();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/cmswitch");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "cmswitch");
    }

    private Router getRouterWithCMSwitchRoutes() {
        Router router = new Router(new Route("(?<method>[a-z\\-]+)", "Default", "index"));
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)", "Default", "index"));
        return router;
    }

    @Test
    public void testRouteWithMultipleDifferentParametersWhereNotAllAreFilled() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = new Router(getRouteWithMultipleParameters());
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/multiple/1");
        assertEquals(endpointReflection.getControllerName(), "Error");
        assertEquals(endpointReflection.getMethodName(), "notFound");
    }

    @Test
    public void testRouteWithCMSwitchAndStringParameterFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/string/value");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "string");
        assertEquals(endpointReflection.getParameters()[0], "value");
    }

    @Test
    public void testRouteWithCMSwitchAndStringParameterSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/string/value");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "string");
        assertEquals(endpointReflection.getParameters()[0], "value");
    }

    @Test
    public void testRouteWithCMSwitchAndStringParameterThird() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCMSwitchAndStringParameterRoutes();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/value");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "index");
        assertEquals(endpointReflection.getParameters()[0], "value");
    }

    private Router getRouterWithCMSwitchAndStringParameterRoutes() {
        Router router = new Router(new Route("(?<method>[a-z\\-]+)(?<string>/[a-z\\-]+)?", "Default", "index"));
        router.addRoute(new Route("(?<controller>[a-z\\-]+)(?<method>/[a-z\\-]+)?(?<string>/[a-z\\-]+)", "Default", "index"));
        router.addRoute(new Route("(?<string>[a-z\\-]+)", "Default", "index"));
        return router;
    }

    @Test
    public void testRouteWithModuleFirst() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/");
        assertEquals(endpointReflection.getModuleName(), "module");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteWithModuleSecond() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/test");
        assertEquals(endpointReflection.getModuleName(), "module");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "test");
    }

    @Test
    public void testRouteWithModuleThird() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Router router = getRouterWithCmSwitchAndModelRoute();
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/testmodule/test/test");
        assertEquals(endpointReflection.getModuleName(), "testmodule");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "test");
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
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "test");
    }
}