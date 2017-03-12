package hyggemvc.router;

/**
 * Created by adam on 21/02/2017.
 */
public interface Router {
    RouteCallable getRouteCallable(String packageName, String url);
}
