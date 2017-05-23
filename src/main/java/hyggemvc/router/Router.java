package hyggemvc.router;

import hyggemvc.router.exceptions.DefaultNameOfCallableInUrlException;
import hyggemvc.router.exceptions.NoRouteMatchedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adam on 21/02/2017.
 */
public class Router {
    private List<Route> routes;

    public Router(Route firstRoute) {
        routes = new ArrayList<>();
        routes.add(firstRoute);
    }

    public Router(Route... routes) {
        this.routes = new ArrayList<>(Arrays.asList(routes));
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public EndpointReflection getControllerReflection(String packageName, String url) {
        url = url.substring(1);
        UrlMatcher urlMatcher;
        for (Route route : routes) {
            urlMatcher = new UrlMatcher(url, route);
            if (urlMatcher.matches()) {
                try {
                    if (!url.isEmpty()) {
                        urlMatcher.extractCallableElements();
                    }
                    return new EndpointReflection(
                            packageName,
                            route.getCallableElementsHolder(),
                            urlMatcher.getParameterTypes(),
                            urlMatcher.getParameters()
                    );
                } catch (DefaultNameOfCallableInUrlException e) {
                    // TODO: again route monitoring here
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    // TODO: route monitoring would come here
                }
            }
        }
        return createNotFoundController(packageName, new NoRouteMatchedException());
    }

    private EndpointReflection createNotFoundController(String packageName, Exception exception) {
        try {
            return new EndpointReflection(
                    packageName,
                    "Error",
                    "notFound",
                    new Class<?>[]{Exception.class},
                    new Object[]{exception}
            );
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            // If 404 is not present, frameworks ErrorController is called
            try {
                return new EndpointReflection(
                        "hyggemvc.controller",
                        "Error",
                        "notFound",
                        new Class<?>[]{Exception.class},
                        new Object[]{e}
                );
            } catch (ClassNotFoundException | NoSuchMethodException e1) {
                e1.printStackTrace(); // This will not happen until frameworks ErrorController is deleted
            }
            return null;
        }
    }
}
