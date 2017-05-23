package hyggemvc.router;

/**
 * Created by adam on 31/03/2017.
 */
public class CallableElement {
    private String urlValue;
    private final String defaultValue;
    private String regexGroup;

    public CallableElement(String defaultValue, String regexGroup) {
        this.defaultValue = defaultValue;
        urlValue = defaultValue;
        this.regexGroup = regexGroup;
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

    public String getRegexGroup() {
        return regexGroup;
    }
}
