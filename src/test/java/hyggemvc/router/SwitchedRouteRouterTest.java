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
public class SwitchedRouteRouterTest {

    private Route firstRoute;
    private Router router;
    private RequestMocUp request;
    private ResponseMockUp response;

    @BeforeMethod
    public void setUp() {
        firstRoute = new Route("(?<method>[a-z\\-]+)?(?<controller>/[a-z\\-]+)?", "Default", "index");
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
    public void testRouteOfUrlWithFullControllerAndMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/test/test");
        assertEquals(controllerReflection.getControllerName(), "Test");
        assertEquals(controllerReflection.getMethodName(), "test");
    }

    @Test
    public void testRouteOfUrlWithEmptyControllerAndFullMethod() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ControllerReflection controllerReflection = router.getControllerReflection("controller", "/default-test");
        assertEquals(controllerReflection.getControllerName(), "Default");
        assertEquals(controllerReflection.getMethodName(), "defaultTest");
    }
}