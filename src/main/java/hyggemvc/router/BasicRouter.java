package hyggemvc.router;

import hyggemvc.router.exceptions.DefaultNameOfCallableInUrlException;
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
            urlParser = new UrlParser(url,route);
            if (urlParser.matches()) {
                try {
                    potentialController = urlParser.extractControllerName();
                    potentialMethod = urlParser.extractMethodName();
                    // This happens only when <controller>/<method> order is respected in current route
                    // and url looks like "/example" which can be both DefaultController.example or ExampleController.index
                    if (onlyPottentialControllerWasExtracted(potentialController, potentialMethod, route)) {
                        try {
                            routeCallable = new RouteCallable(
                                    packageName,
                                    route.getDefaultController(),
                                    Notator.toCamelCase(potentialController),
                                    urlParser.getParameterTypes(),
                                    urlParser.getParameters()
                            );
                            return routeCallable;
                        } catch (NoSuchMethodException | ClassNotFoundException e) {
                            // TODO: route monitoring would come here and to the other catch block
                        }
                    }
                    routeCallable =  new RouteCallable(
                            packageName,
                            Notator.toCamelCaseWithFirstUpperCase(potentialController),
                            Notator.toCamelCase(potentialMethod),
                            urlParser.getParameterTypes(),
                            urlParser.getParameters()
                    );
                    return routeCallable;

                } catch (DefaultNameOfCallableInUrlException | NoSuchMethodException | ClassNotFoundException e) {}
            }
        }
        return RouteCallable.notFoundCallable(packageName,new NoRouteMatchedException());
    }

    private boolean onlyPottentialControllerWasExtracted(String potentialController, String potentialMethod, Route route) {
        return potentialMethod.equals(route.getDefaultMethod()) && !potentialController.equals(route.getDefaultController());
    }
}
