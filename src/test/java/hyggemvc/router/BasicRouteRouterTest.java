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
public class BasicRouteRouterTest {

    private Route firstRoute;
    private Router router;
    private RequestMocUp request;
    private ResponseMockUp response;

    @BeforeMethod
    public void setUp() {
        firstRoute = new Route("(?<controller>[a-z\\-]+)?(?<method>/[a-z\\-]+)?", "Default", "index");
        router = new Router(firstRoute);
        request = new RequestMocUp();
        response = new ResponseMockUp();
    }

    @Test
    public void testRouteOfEmptyUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithEmptyMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "index");
    }

    @Test
    public void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/test");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "test");
    }

    @Test
    public void testWrongUrl() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/23");
        assertErrorNotFound(controllerReflection);
    }

    private void assertErrorNotFound(ControllerReflection controllerReflection) {
        assertEquals(controllerReflection.getControllerName(), "Error");
        assertEquals(controllerReflection.getMethodName(), "notFound");
    }

    @Test
    public void testUrlWithDefaultValue() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/default");
        assertErrorNotFound(controllerReflection);
    }

    @Test
    public void testUrlWithDefaultValueInMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/index");
        assertErrorNotFound(controllerReflection);
    }
}