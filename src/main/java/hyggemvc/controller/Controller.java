package hyggemvc.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class Controller {
    protected final HttpServletRequest request;
    protected final HttpServletResponse response;

    public Controller(HttpServletRequest request,HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected void renderTemplate(String template) throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("template",template);

        request.setAttribute("root","/");
        request.setAttribute("assets","/assets/");

        if(request.getAttribute("title") == null) {
            request.setAttribute("title",template);
        }

        RequestDispatcher view = request.getRequestDispatcher("index.jsp");
        view.forward(request, response);
    }
}
