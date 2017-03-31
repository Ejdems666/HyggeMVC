package hyggemvc.router;

/**
 * Created by adam on 31/03/2017.
 */
public class RouteElement {
    private String urlValue;
    private final String defaultValue;

    public RouteElement(String defaultValue) {
        this.defaultValue = defaultValue;
        urlValue = defaultValue;
    }

    public String getUrlValue() {
        return urlValue;
    }

    public void setUrlValue(String urlValue) {
        this.urlValue = urlValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
