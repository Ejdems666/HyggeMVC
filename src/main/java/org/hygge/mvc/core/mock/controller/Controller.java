package org.hygge.mvc.core.mock.controller;

import org.hygge.mvc.core.component.Alerts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class Controller {
    private Alerts alerts = null;

    private String controllerName;
    private String methodName;
    private String moduleName;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

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
        alerts.addAlert(type, message);
    }

    protected void alertSuccess(String message) {
        addAlert(Alerts.Type.SUCCESS, message);
    }

    protected void alertError(String message) {
        addAlert(Alerts.Type.ERROR, message);
    }

    public void beforeEndpointCall() {

    }

    public void afterEndpointCall() {

    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
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

    public String getControllerName() {
        return controllerName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getModuleName() {
        return moduleName;
    }
}
