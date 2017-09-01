package org.hygge.mvc.core.router;

import org.hygge.mvc.core.controller.annotation.Get;
import org.hygge.mvc.core.controller.annotation.Post;
import org.hygge.mvc.core.router.exceptions.DefaultNameOfCallableInUrlException;
import org.hygge.mvc.core.router.exceptions.IncorrectRequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adam on 21/02/2017.
 */
public class Router {
    private Class<? extends Annotation> requestMethod;
    private List<Route> routes;

    public Router(String requestMethod, Route firstRoute) {
        prepareRequestMethod(requestMethod);
        routes = new ArrayList<>();
        routes.add(firstRoute);
    }

    public Router(String requestMethod, Route... routes) {
        prepareRequestMethod(requestMethod);
        this.routes = new ArrayList<>(Arrays.asList(routes));
    }

    private void prepareRequestMethod(String requestMethod) {
        if (requestMethod.equals("POST")) {
            this.requestMethod = Post.class;
        } else {
            this.requestMethod = Get.class;
        }
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public EndpointReflection getControllerReflection(String packageName, String url) {
        url = url.substring(1);
        UrlMatcher urlMatcher;
        Exception lastExceptionAfterMatch = null;
        for (Route route : routes) {
            urlMatcher = new UrlMatcher(url, route);
            if (urlMatcher.matches()) {
                try {
                    if (!url.isEmpty()) {
                        urlMatcher.extractCallableElements();
                    }
                    EndpointReflection endpointReflection = new EndpointReflection(
                            packageName,
                            route.getCallableElementsHolder(),
                            urlMatcher.getParameterTypes(),
                            urlMatcher.getParameters()
                    );
                    checkRequestMethodAnnotation(endpointReflection.getMethod());
                    return endpointReflection;
                } catch (DefaultNameOfCallableInUrlException | IncorrectRequestMethod e) {
                    System.out.println(e.getMessage());
                    lastExceptionAfterMatch = e;
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    System.out.println("No match for route: " + route.getPattern());
                    lastExceptionAfterMatch = e;
                }
            }
        }
        return createNotFoundEndpoint(packageName, lastExceptionAfterMatch);
    }

    private void checkRequestMethodAnnotation(Method method) throws IncorrectRequestMethod {
        if (method.isAnnotationPresent(Post.class) && !requestMethod.equals(Post.class)) {
            throw new IncorrectRequestMethod(method,Post.class);
        } else if (method.isAnnotationPresent(Get.class) && !requestMethod.equals(Get.class)){
            throw new IncorrectRequestMethod(method,Get.class);
        }
    }

    private EndpointReflection createNotFoundEndpoint(String packageName, Exception exception) {
        try {
            return new EndpointReflection(
                    packageName,
                    "Error",
                    "notFound",
                    new Class<?>[]{Exception.class},
                    new Object[]{exception}
            );
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            // If 404 is not present, frameworks ErrorController is called
            try {
                return new EndpointReflection(
                        "org.hygge.mvc.core.controller",
                        "Error",
                        "notFound",
                        new Class<?>[]{Exception.class},
                        new Object[]{e}
                );
            } catch (ClassNotFoundException | NoSuchMethodException e1) {
                e1.printStackTrace(); // This will not happen until frameworks ErrorController is deleted
            }
            return null;
        }
    }
}
