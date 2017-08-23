package org.hygge.mvc.core.router;

import org.hygge.mvc.core.utilities.Notator;

/**
 * Created by adam on 23/05/2017.
 */
public class CallableElementsHolder {
    private CallableElement module;
    private CallableElement controller;
    private CallableElement method;

    public CallableElementsHolder(String controller, String method) {
        this.controller = new CallableElement(Notator.lcFirst(controller), "controller");
        this.method = new CallableElement(method, "method");
        module = null;
    }

    public CallableElementsHolder(String controller, String method, String module) {
        this.controller = new CallableElement(Notator.lcFirst(controller), "controller");
        this.method = new CallableElement(method, "method");
        this.module = new CallableElement(module, "module");
    }

    public CallableElement getModule() {
        return module;
    }

    public CallableElement getController() {
        return controller;
    }

    public CallableElement getMethod() {
        return method;
    }
}
