package hyggemvc.run.resulter;

import hyggemvc.run.result.jsp.Jsp;
import hyggemvc.run.result.jsp.JspResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 8/22/2017.
 */
public class JspResulter implements Resulter {

    private Jsp jsp;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public JspResulter(JspResult jspResult, HttpServletRequest request, HttpServletResponse response) {
        this.jsp = jspResult.getResult();
        this.request = request;
        this.response = response;
    }

    @Override
    public void result() {
        response.setContentType("text/html");
        request.setAttribute("template", jsp.getTemplateName());
        if (request.getAttribute("title") == null) {
            request.setAttribute("title", jsp.getTemplateName());
        }
        try {
            request.getRequestDispatcher("/WEB-INF/" + jsp.getLayoutName() + ".jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
