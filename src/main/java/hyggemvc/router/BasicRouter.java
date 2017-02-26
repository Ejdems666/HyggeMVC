package hyggemvc.router;

import hyggemvc.router.exceptions.TooManyArgumentsException;

/**
 * Created by adam on 21/02/2017.
 */
public class BasicRouter implements Router {
    private UrlParser urlParser = new UrlParser();

    @Override
    public void inflateRoute(Route route, String url) {
        if (url.equals("/")) return;
        String[] urlParts = url.substring(1).split("/");
        switch (urlParts.length) {
            case 1:
                String potentialMethod = urlParser.toCamelCase(urlParts[0]);
                String potentialController = route.getControllerClass();
                if (methodExists(potentialController, potentialMethod)) {
                    route.setMethodName(potentialMethod);
                } else {
                    route.setControllerName(urlParser.toCamelCaseWithFirstUpperCase(urlParts[0]));
                    if (methodExists(route.getControllerClass(), "index")) {
                        route.setMethodName("index");
                    } else {
                        route.setErrorRoute(new ClassNotFoundException(),"notFound");
                    }
                }
                break;
            case 2:
                route.setControllerName(urlParser.toCamelCaseWithFirstUpperCase(urlParts[0]));
                route.setMethodName(urlParser.toCamelCase(urlParts[1]));
                break;
            default:
                route.setErrorRoute(new TooManyArgumentsException(), "notFound");
        }
    }

    private boolean methodExists(String controller, String method) {
        try {
            Class<?> controllerClass = Class.forName(controller);
            controllerClass.getDeclaredMethod(method);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
