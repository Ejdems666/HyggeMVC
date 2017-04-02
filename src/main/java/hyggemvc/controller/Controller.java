package hyggemvc.controller;

import hyggemvc.component.Alerts;
import hyggemvc.component.Component;

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

    protected final HttpServletRequest request;
    protected final HttpServletResponse response;

    public Controller(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected void renderTemplate(String template) {
        response.setContentType("text/html");
        request.setAttribute("template", template);
        request.setAttribute("alerts", getAlerts());

        if (request.getAttribute("title") == null) {
            request.setAttribute("title", template);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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
}
