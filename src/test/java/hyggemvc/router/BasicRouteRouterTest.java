package hyggemvc.router;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertEquals;


/**
 * Created by adam on 25/02/2017.
 */
public class BasicRouteRouterTest {

    private Route firstRoute;
    private Router router;

    @BeforeMethod
    public void setUp() {
        firstRoute = new Route("(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?", "Default", "index");
        router = new Router(firstRoute);
    }

    @Test
    public void testRouteOfEmptyUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/");
        assertEquals(endpointReflection.getControllerName(), "Default");
        assertEquals(endpointReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/test");
        assertEquals(endpointReflection.getControllerName(), "Test");
        assertEquals(endpointReflection.getMethodName(), "test");
    }

    @Test
    public void testWrongUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/23");
        assertErrorNotFound(endpointReflection);
    }

    private void assertErrorNotFound(EndpointReflection endpointReflection) {
        assertEquals(endpointReflection.getControllerName(), "Error");
        assertEquals(endpointReflection.getMethodName(), "notFound");
    }

    @Test
    public void testUrlWithDefaultValue() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/default");
        assertErrorNotFound(endpointReflection);
    }

    @Test
    public void testUrlWithDefaultValueInMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EndpointReflection endpointReflection = router.getControllerReflection("mock.controller", "/test/index");
        assertErrorNotFound(endpointReflection);
    }
}