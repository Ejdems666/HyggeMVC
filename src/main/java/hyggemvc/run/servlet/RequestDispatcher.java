package hyggemvc.run.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by adam on 25/02/2017.
 */
public abstract class RequestDispatcher implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String path = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length());

        if (path.startsWith("/assets")) {
            chain.doFilter(request, response); // Goes to default servlet for assets
        } else {
            routeToController((HttpServletRequest) request, (HttpServletResponse) response, path);
        }
    }

    protected abstract void routeToController(HttpServletRequest request, HttpServletResponse response, String path);

    public void init(FilterConfig config) throws ServletException {}
}
