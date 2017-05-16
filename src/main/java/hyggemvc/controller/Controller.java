package hyggemvc.controller;

import hyggemvc.component.Alerts;
import hyggemvc.component.Component;
import hyggemvc.router.Notator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class Controller {
    private Alerts alerts = null;

    private String controllerName;
    private String methodName;
    private String moduleName;

    protected final HttpServletRequest request;
    protected final HttpServletResponse response;

    public Controller(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected void renderTemplate(String template) {
        renderTemplate(template,"index");
    }

    protected void renderTemplate(String template, String layout) {
        response.setContentType("text/html");
        request.setAttribute("template", template);
        request.setAttribute("alerts", getAlerts());

        if (request.getAttribute("title") == null) {
            request.setAttribute("title", template);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/"+layout+".jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
    private Component getAlerts() {
        HttpSession session = request.getSession();
        if (alerts == null && session.getAttribute("alerts") != null) {
            return ((Component) session.getAttribute("alerts"));
        }
        return alerts;
    }

    protected void renderTemplate() {
        String template = "";
        if (moduleName != null) {
            template += moduleName+"/";
        }
        template += Notator.lcFirst(controllerName)+"/"+methodName;
        renderTemplate(template);
    }

    protected void redirect(String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addAlert(Alerts.Type type, String message) {
        if (alerts == null) {
            alerts = new Alerts(request.getSession());
        }
        alerts.addAlert(type,message);
    }
    protected void alertSuccess(String message) {
        addAlert(Alerts.Type.SUCCESS,message);
    }
    protected void alertError(String message) {
        addAlert(Alerts.Type.ERROR,message);
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
