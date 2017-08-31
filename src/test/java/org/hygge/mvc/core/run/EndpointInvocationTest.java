package org.hygge.mvc.core.run;

import org.hygge.mvc.core.mock.controllers.ApiController;
import org.hygge.mvc.core.controller.Controller;
import org.hygge.mvc.core.mock.controllers.DefaultController;
import org.hygge.mvc.core.mock.controllers.module.TestController;
import org.hygge.mvc.core.router.CallableElementsHolder;
import org.hygge.mvc.core.router.EndpointReflection;
import org.hygge.mvc.core.run.result.Result;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.easymock.EasyMock.createMock;

/**
 * Created by adam on 23/05/2017.
 */
public class EndpointInvocationTest {

    private ControllerFactory factory;
    private EndpointInvoker invoker;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeMethod
    public void setUp() throws Exception {
        factory = new ControllerFactory();
        invoker = new EndpointInvoker();
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
    }

    @Test
    public void testCallBasicEndpoint() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Default","index");
        EndpointReflection endpointReflection = new EndpointReflection("org.hygge.mvc.core.mock.controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Controller controller = factory.setupControllerObject(endpointReflection,request,response);
        Result result = invoker.invokeEndpoint(controller, endpointReflection);
        Assert.assertEquals(controller.getClass(), DefaultController.class);
        Assert.assertEquals(result.getResult(),"index");
    }

    @Test
    public void testCallBasicVoidEndpoint() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Default","defaultTest");
        EndpointReflection endpointReflection = new EndpointReflection("org.hygge.mvc.core.mock.controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Controller controller = factory.setupControllerObject(endpointReflection,request,response);
        Result result = invoker.invokeEndpoint(controller, endpointReflection);
        Assert.assertEquals(controller.getClass(), DefaultController.class);
        Assert.assertEquals(result, null);
    }

    @Test
    public void testCallBasicEndpointWithModule() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Test","index", "module");
        EndpointReflection endpointReflection = new EndpointReflection("org.hygge.mvc.core.mock.controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Controller controller = factory.setupControllerObject(endpointReflection,request,response);
        Result result = invoker.invokeEndpoint(controller, endpointReflection);
        Assert.assertEquals(controller.getClass(), TestController.class);
        Assert.assertEquals(result.getResult(),"index");
    }

    @Test
    public void testCallBasicEndpointWithParameter() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Api","index");
        EndpointReflection endpointReflection = new EndpointReflection("org.hygge.mvc.core.mock.controller", callableElementsHolder, new Class<?>[]{Integer.class}, new Object[]{1});
        Controller controller = factory.setupControllerObject(endpointReflection,request,response);
        Result result = invoker.invokeEndpoint(controller, endpointReflection);
        Assert.assertEquals(controller.getClass(),ApiController.class);
        Assert.assertEquals(result.getResult(),"index1");
    }
}