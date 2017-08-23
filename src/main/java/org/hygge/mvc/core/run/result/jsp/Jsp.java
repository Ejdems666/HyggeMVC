package org.hygge.mvc.core.run.result.jsp;

/**
 * Created by adam on 8/22/2017.
 */
public class Jsp {
    private String templateName;
    private String layoutName;

    public Jsp(String templateName, String layoutName) {
        this.templateName = templateName;
        this.layoutName = layoutName;
    }

    public Jsp(String templateName) {
        this.templateName = templateName;
        layoutName = "index.jsp";
    }

    public String getLayoutName() {
        return layoutName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
