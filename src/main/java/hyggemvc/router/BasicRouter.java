package hyggemvc.router;

import hyggemvc.router.exceptions.TooManyArgumentsException;

/**
 * Created by adam on 21/02/2017.
 */
public class BasicRouter implements Router {
    private UrlParser urlParser = new UrlParser();

    @Override
    public void inflateRoute(Route route, String url) {
        String[] urlParts = url.split("/");
        if (urlParts.length > 0) {
            route.setControllerName(urlParser.toCamelCaseWithFirstUpperCase(urlParts[0]));
            if (urlParts.length > 1) {
                route.setMethodName(urlParser.toCamelCase(urlParts[1]));
                if (urlParts.length == 3) {
                    inflateRouteWithArgument(urlParts, route);
                } else if (urlParts.length > 3) {
                    route.setErrorRoute(new TooManyArgumentsException(), "notFound");
                }
            }
        }
    }

    private void inflateRouteWithArgument(String[] urlParts, Route route) {
        try {
            route.setArgument(Integer.parseInt(urlParts[2]));
        } catch (NumberFormatException e) {
            route.setErrorRoute(e, "notFound");
        }
    }
}
