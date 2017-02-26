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
    void testDefaultRoute() {
        router.inflateRoute(route,"/");
        Assertions.assertEquals("hyggemvc.controller.DefaultController",route.getControllerClass());
        Assertions.assertEquals("index",route.getMethodName());
        Assertions.assertEquals(null,route.getArgument());
    }

    @Test
    void testOnlyControllerRoute() {
        router.inflateRoute(route,"/test");
        Assertions.assertEquals("hyggemvc.controller.DefaultController",route.getControllerClass());
        Assertions.assertEquals("test",route.getMethodName());
        Assertions.assertEquals(null,route.getArgument());
    }

    @Test
    void testControllerAndMethodRoute() {
        router.inflateRoute(route,"/test/method");
        Assertions.assertEquals("hyggemvc.controller.TestController",route.getControllerClass());
        Assertions.assertEquals("method",route.getMethodName());
        Assertions.assertEquals(null,route.getArgument());
    }

    @Test
    void testTooManyArgumentsInRoute() {
        router.inflateRoute(route,"/test/method/with/just/too/many/arguments");
        Assertions.assertEquals("hyggemvc.controller.ErrorController",route.getControllerClass());
        Assertions.assertEquals("notFound",route.getMethodName());
        Assertions.assertTrue(route.getArgument() instanceof TooManyArgumentsException);
    }
}