package hyggemvc.router;

import hyggemvc.router.exceptions.DefaultNameOfCallableInUrlException;
import hyggemvc.router.exceptions.NoRouteMatchedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        UrlMatcher urlMatcher;
        for (Route route : routes) {
            urlMatcher = new UrlMatcher(url, route);
            if (urlMatcher.matches()) {
                try {
                    Map<String,RouteElement> callableElements;
                    if (url.isEmpty()) {
                        callableElements = route.getCallableElements();
                    } else {
                        callableElements = urlMatcher.extractCallableElements();
                    }
                    return new RouteCallable(
                            packageName,
                            callableElements,
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
        return RouteCallable.notFoundCallable(packageName, new NoRouteMatchedException());
    }
}
