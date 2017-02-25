package mvc.router;


import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import java.io.IOException;
import java.net.URL;

/**
 * Created by adam on 25/02/2017.
 */
public class RequestMocUp implements HttpRequest{
    @Override
    public HttpResponse doGetRequestEX(URL url, long l) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doGetRequestEX(URL url, String[] strings, String[] strings1, long l) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doHeadRequest(URL url) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doGetRequest(URL url) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doHeadRequest(URL url, boolean b) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doGetRequest(URL url, boolean b) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doHeadRequest(URL url, String[] strings, String[] strings1) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doGetRequest(URL url, String[] strings, String[] strings1) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doHeadRequest(URL url, String[] strings, String[] strings1, boolean b) throws IOException {
        return null;
    }

    @Override
    public HttpResponse doGetRequest(URL url, String[] strings, String[] strings1, boolean b) throws IOException {
        return null;
    }
}