package org.hygge.mvc.core.run.resulter;

import org.hygge.mvc.core.run.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by adam on 31/08/2017.
 */
public class JsonResulter implements Resulter {
    private Result jsonResult;
    private HttpServletResponse response;

    public JsonResulter(Result jsonResult, HttpServletResponse response) {
        this.jsonResult = jsonResult;
        this.response = response;
    }

    @Override
    public void returnResultInResponse() {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(jsonResult.getResult());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
