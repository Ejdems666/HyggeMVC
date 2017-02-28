package hyggemvc.controller;

import hyggemvc.component.BootstrapAlerts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class Controller {
    private boolean wasRedirected = false;
    private BootstrapAlerts alerts = null;

    protected final HttpServletRequest request;
    protected final HttpServletResponse response;

    public Controller(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected void setTemplateOutput(String template) {
        response.setContentType("text/html");
        request.setAttribute("template", template);
        request.setAttribute("alerts", alerts);

        if (request.getAttribute("title") == null) {
            request.setAttribute("title", template);
        }
    }

    protected void redirect(String url) {
        try {
            response.sendRedirect(url);
            wasRedirected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean wasRedirected() {
        return wasRedirected;
    }

    public boolean templateWasOutputed() {
        return request.getAttribute("template") != null;
    }

    protected void setAlert(BootstrapAlerts.Type type, String message) {
        if (alerts == null) {
            alerts = new BootstrapAlerts(request.getSession());
        }
        alerts.addAlert(type,message);
    }
}
