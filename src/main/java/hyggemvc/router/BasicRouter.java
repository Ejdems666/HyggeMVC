package hyggemvc.router;

import hyggemvc.router.exceptions.DefaultElementValueInUrlException;
import hyggemvc.router.exceptions.NoRouteMatchedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 21/02/2017.
 */
public class BasicRouter implements Router {
    private List<Route> routes = new ArrayList<>();

    public BasicRouter(Route firstRoute) {
        routes.add(firstRoute);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    @Override
    public RouteCallable getRouteCallable(String packageName, String url) {
        url = url.substring(1);
        String potentialController;
        String potentialMethod;
        UrlParser urlParser;
        RouteCallable routeCallable;
        for (Route route : routes) {
            routeCallable = null;
            urlParser = new UrlParser(url,route);
            if (urlParser.matches()) {
                try {
                    potentialController = urlParser.extractControllerName();
                    potentialMethod = urlParser.extractMethodName();
                    if (onlyOneCallableIsPresent(potentialController, potentialMethod, route)) {
                        try {
                            routeCallable = new RouteCallable(
                                    packageName,
                                    route.getDefaultController(),
                                    Notator.toCamelCase(potentialController),
                                    urlParser.getParameterTypes(),
                                    urlParser.getParameters()
                            );
                        } catch (NoSuchMethodException | ClassNotFoundException e) {
                            routeCallable =  new RouteCallable(
                                    packageName,
                                    Notator.toCamelCaseWithFirstUpperCase(potentialController),
                                    Notator.toCamelCase(potentialMethod),
                                    urlParser.getParameterTypes(),
                                    urlParser.getParameters()
                            );
                        }
                    } else {
                        routeCallable =  new RouteCallable(
                                packageName,
                                Notator.toCamelCaseWithFirstUpperCase(potentialController),
                                Notator.toCamelCase(potentialMethod),
                                urlParser.getParameterTypes(),
                                urlParser.getParameters()
                        );
                    }

                } catch (DefaultElementValueInUrlException | NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (routeCallable != null) {
                    return routeCallable;
                }
            }
        }
        return RouteCallable.notFoundCallable(packageName,new NoRouteMatchedException());
    }

    private boolean onlyOneCallableIsPresent(String potentialController, String potentialMethod, Route route) {
        return potentialMethod.equals(route.getDefaultMethod()) && !potentialController.equals(route.getDefaultController());
    }
}
