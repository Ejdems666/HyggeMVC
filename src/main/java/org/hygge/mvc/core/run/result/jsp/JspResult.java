package org.hygge.mvc.core.run.result.jsp;

import org.hygge.mvc.core.mock.controller.Controller;
import org.hygge.mvc.core.run.result.Result;
import org.hygge.mvc.core.utilities.Notator;

/**
 * Created by adam on 8/22/2017.
 */
public class JspResult implements Result<Jsp> {

    private Jsp jsp;

    public JspResult(String templateName) {
        this.jsp = new Jsp(templateName);
    }

    public JspResult(String templateName, String layoutName) {
        this.jsp = new Jsp(templateName,layoutName);
    }

    /**
     * Takes module, controller and method name as parameters for template name as follows:
     * templateName = "[module>/]<controller>/<method>"
     * @param controller object with needed values (use this to get default JSP returnResultInResponse)
     */
    public JspResult(Controller controller) {
        String template = "";
        if (controller.getModuleName() != null) {
            template += controller.getModuleName()+"/";
        }
        template += Notator.lcFirst(controller.getControllerName())+"/"+controller.getMethodName();
    }

    @Override
    public Jsp getResult() {
        return jsp;
    }
}
