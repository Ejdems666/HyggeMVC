package hyggemvc.run;

import hyggemvc.router.EndpointFactory;
import hyggemvc.router.EndpointReflection;
import hyggemvc.run.result.Result;
import hyggemvc.run.result.jsp.JspResult;
import hyggemvc.run.resulter.JspResulter;
import hyggemvc.run.resulter.Resulter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by adam on 21/02/2017.
 */
public class AppContainer {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public AppContainer(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    //TODO: master try catch block would come here
    public void run(EndpointReflection endpointReflection) {
        try {
            EndpointFactory endpointFactory = new EndpointFactory();
            Result result = endpointFactory.callEndpoint(endpointReflection, request, response);
            Resulter resulter;
            if (result instanceof JspResult) {
                resulter = new JspResulter((JspResult) result, request, response);
            } else {
                return;
            }
            resulter.result();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace(); // should not happen
        } catch (ClassCastException e) {
            System.out.println(
                    "Method " + endpointReflection.getMethod() +
                            " in controller " + endpointReflection.getControllerClass() +
                            " did not return object of type: " + Result.class
            );
        }
    }
}
