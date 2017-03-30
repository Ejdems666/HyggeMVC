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
    private String packageName;

    public BasicRouter(Route firstRoute) {
        routes.add(firstRoute);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    @Override
    public RouteCallable getRouteCallable(String packageName, String url) {
        this.packageName = packageName;
        url = url.substring(1);
        String potentialController;
        String potentialMethod;
        UrlMatcher urlMatcher;
        for (Route route : routes) {
            urlMatcher = new UrlMatcher(url, route);
            if (urlMatcher.matches()) {
                try {
                    potentialController = urlMatcher.extractControllerName();
                    potentialMethod = urlMatcher.extractMethodName();
                    // This happens only when <controller>/<method> order is respected in current route
                    // and url looks like "/example" which can be both DefaultController.example or ExampleController.index
                    if (onlyPotentialControllerWasInUrl(potentialController, potentialMethod, route)) {
                        try {
                            return new RouteCallable(
                                    packageName,
                                    route.getDefaultController(),
                                    potentialController,
                                    urlMatcher.getParameterTypes(),
                                    urlMatcher.getParameters()
                            );
                        } catch (NoSuchMethodException | ClassNotFoundException e) {
                            // TODO: route monitoring would come here
                        }
                    }
                    try {
                        return new RouteCallable(
                                packageName,
                                Notator.ucFirst(potentialController),
                                potentialMethod,
                                urlMatcher.getParameterTypes(),
                                urlMatcher.getParameters()
                        );
                    } catch (NoSuchMethodException | ClassNotFoundException e) {
                        // TODO: route monitoring would come here
                    }
                } catch (DefaultNameOfCallableInUrlException e) {
                    // TODO: again route monitoring here
                }
            }
        }
        return RouteCallable.notFoundCallable(packageName, new NoRouteMatchedException());
    }

    private boolean onlyPotentialControllerWasInUrl(String potentialController, String potentialMethod, Route route) {
        return potentialMethod.equals(route.getDefaultMethod()) && !potentialController.equals(route.getDefaultController());
    }

    private RouteCallable getRouteCallable(String controllerName, String methodName, UrlMatcher urlMatcher) {
        try {
            return new RouteCallable(
                    packageName,
                    controllerName,
                    methodName,
                    urlMatcher.getParameterTypes(),
                    urlMatcher.getParameters()
            );
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            // TODO: route monitoring would come here
        }
        return null;
    }


}
