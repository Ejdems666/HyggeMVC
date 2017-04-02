package hyggemvc.router;

import hyggemvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by adam on 21/02/2017.
 */
public class AppContainer {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public AppContainer(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    //TODO: master try catch block would come here
    public void run(RouteCallable routeCallable) {
        try {
            Controller controller = routeCallable.callRoute(request,response);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
