package org.hygge.mvc.core.router;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;


/**
 * Created by adam on 25/02/2017.
 */
public class SwitchedRouteRouterTest {

    private String requestMethod = "GET";
    private Route firstRoute;
    private Router router;

    @BeforeMethod
    public void setUp() {
        firstRoute = new Route("(?<method>[a-z\\-]+)?(?<controller>/[a-z\\-]+)?", "Default", "index");
        router = new Router(requestMethod, firstRoute);
    }

    @Test
    public void testRouteOfEmptyUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/test/test");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "test");
    }

    @Test
    public void testRouteOfUrlWithEmptyControllerAndFullMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/default-test");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "defaultTest");
    }
}