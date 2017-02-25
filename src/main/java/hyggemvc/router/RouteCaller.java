package hyggemvc.router;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by adam on 21/02/2017.
 */
public class RouteCaller {

    private final Route route;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private boolean looping = false;

    public RouteCaller(Route route, HttpServletRequest request, HttpServletResponse response) {
        this.route = route;
        this.request = request;
        this.response = response;
    }

    public Controller callRoute() {
        Controller controller = null;
        try {
            controller = createController();
            callMethod(controller);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException e) {
            if (!looping) {
                route.setErrorRoute(e, "notFound");
                callRoute();
                looping = true;
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return controller;
        }
    }
    private Controller createController() throws Exception {
        Class<?> controllerClass = Class.forName(route.getAssembledControllerClass());
        Constructor<?> constructor = controllerClass.getConstructor(HttpServletRequest.class,HttpServletResponse.class);
        return ((Controller) constructor.newInstance(request,response));
    }
    private void callMethod(Controller controller) throws Exception {
        Object argument = route.getArgument();
        Method method;
        if (argument != null) {
            method = controller.getClass().getMethod(route.getMethodName(), Object.class);
            method.invoke(controller,argument);
        } else {
            method = controller.getClass().getMethod(route.getMethodName());
            method.invoke(controller);
        }
    }
}
