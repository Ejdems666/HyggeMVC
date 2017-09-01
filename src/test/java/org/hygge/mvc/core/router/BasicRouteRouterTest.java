package org.hygge.mvc.core.router;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;


/**
 * Created by adam on 25/02/2017.
 */
public class BasicRouteRouterTest {

    private String requestMethod = "GET";
    private Route firstRoute;
    private Router router;

    @BeforeMethod
    public void setUp() {
        firstRoute = new Route("(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?", "Default", "index");
        router = new Router(requestMethod, firstRoute);
    }

    @Test
    public void testRouteOfEmptyUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/test");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/test/test");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "test");
    }

    @Test
    public void testWrongUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/23");
        assertErrorNotFound(endpointReflection);
    }

    private void assertErrorNotFound(EndpointReflection endpointReflection) {
        assertEquals(endpointReflection.getControllerName(), "Error");
        assertEquals(endpointReflection.getMethodName(), "notFound");
    }

    @Test
    public void testUrlWithDefaultValue() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/default");
        assertErrorNotFound(endpointReflection);
    }

    @Test
    public void testUrlWithDefaultValueInMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/test/index");
        assertErrorNotFound(endpointReflection);
    }

    @Test
    public void testWrongPostMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/test/post");
        assertErrorNotFound(endpointReflection);
    }

    @Test
    public void testCorrectGetMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("org.hygge.mvc.core.mock.controllers", "/test/get");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "get");
    }
}