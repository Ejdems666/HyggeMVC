package hyggemvc.router;

import hyggemvc.router.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by adam on 25/02/2017.
 */
class BasicRouterTest {
    Route route;
    Router router = new BasicRouter();

    @BeforeEach
    void setUp() {
        route = new BasicRoute("hyggemvc.controller");
    }

    @Test
    void testOnlyControllerRoute() {
        router.inflateRoute(route,"/test");
        Assertions.assertEquals("hyggemvc.controller.TestController",route.getAssembledControllerClass());
        Assertions.assertEquals("index",route.getMethodName());
        Assertions.assertEquals(null,route.getArgument());
    }

    @Test
    void testControllerAndMethodRoute() {
        router.inflateRoute(route,"/test/method");
        Assertions.assertEquals("hyggemvc.controller.TestController",route.getAssembledControllerClass());
        Assertions.assertEquals("method",route.getMethodName());
        Assertions.assertEquals(null,route.getArgument());
    }

    @Test
    void testFullRouteWithValidArgument() {
        router.inflateRoute(route,"/test/method/1");
        Assertions.assertEquals("hyggemvc.controller.TestController",route.getAssembledControllerClass());
        Assertions.assertEquals("method",route.getMethodName());
        Assertions.assertEquals(Integer.valueOf(1),route.getArgument());
    }

    @Test
    void testFullRouteWithInvalidArgument() {
        router.inflateRoute(route,"/test/method/numberShouldBeHere");
        Assertions.assertEquals("hyggemvc.controller.ErrorController",route.getAssembledControllerClass());
        Assertions.assertEquals("notFound",route.getMethodName());
        Assertions.assertTrue(route.getArgument() instanceof NumberFormatException);
    }

    @Test
    void testTooManyArgumentsInRoute() {
        router.inflateRoute(route,"/test/method/with/just/too/many/arguments");
        Assertions.assertEquals("hyggemvc.controller.ErrorController",route.getAssembledControllerClass());
        Assertions.assertEquals("notFound",route.getMethodName());
        Assertions.assertTrue(route.getArgument() instanceof TooManyArgumentsException);
    }
}