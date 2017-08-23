package org.hygge.hyggemvc.router;

import mock.controller.ApiController;
import mock.controller.DefaultController;
import mock.controller.module.TestController;
import org.hygge.hyggemvc.run.result.Result;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.easymock.EasyMock.createMock;

/**
 * Created by adam on 23/05/2017.
 */
public class EndpointFactoryTest {

    private EndpointFactory factory;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeMethod
    public void setUp() throws Exception {
        factory = new EndpointFactory();
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
    }

    @Test
    public void testCallBasicEndpoint() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Default","index");
        EndpointReflection endpointReflection = new EndpointReflection("mock.controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Result result = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertEquals(endpointReflection.getControllerClass(), DefaultController.class);
        Assert.assertEquals(result.getResult(),"index");
    }

    @Test
    public void testCallBasicVoidEndpoint() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Default","defaultTest");
        EndpointReflection endpointReflection = new EndpointReflection("mock.controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Result result = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertEquals(endpointReflection.getControllerClass(), DefaultController.class);
        Assert.assertEquals(result, null);
    }

    @Test
    public void testCallBasicEndpointWithModule() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Test","index", "module");
        EndpointReflection endpointReflection = new EndpointReflection("mock.controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Result result = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertEquals(endpointReflection.getControllerClass(), TestController.class);
        Assert.assertEquals(result.getResult(),"index");
    }

    @Test
    public void testCallBasicEndpointWithParameter() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Api","index");
        EndpointReflection endpointReflection = new EndpointReflection("mock.controller", callableElementsHolder, new Class<?>[]{Integer.class}, new Object[]{1});
        Result result = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertEquals(endpointReflection.getControllerClass(),ApiController.class);
        Assert.assertEquals(result.getResult(),"index1");
    }
}