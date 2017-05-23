package hyggemvc.router;

import controller.ApiController;
import controller.DefaultController;
import controller.module.TestController;
import hyggemvc.controller.Controller;
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
        EndpointReflection endpointReflection = new EndpointReflection("controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Controller controller = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertTrue(controller instanceof DefaultController);
        Assert.assertEquals(((DefaultController) controller).called,"index");
    }

    @Test
    public void testCallBasicEndpointWithModule() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Test","index", "module");
        EndpointReflection endpointReflection = new EndpointReflection("controller", callableElementsHolder, new Class<?>[]{}, new Object[]{});
        Controller controller = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertTrue(controller instanceof TestController);
        Assert.assertEquals(((TestController) controller).called,"index");
    }

    @Test
    public void testCallBasicEndpointWithParameter() throws Exception {
        CallableElementsHolder callableElementsHolder = new CallableElementsHolder("Api","index");
        EndpointReflection endpointReflection = new EndpointReflection("controller", callableElementsHolder, new Class<?>[]{Integer.class}, new Object[]{1});
        Controller controller = factory.callEndpoint(endpointReflection, request, response);
        Assert.assertTrue(controller instanceof ApiController);
        Assert.assertEquals(((ApiController) controller).called,"index1");
    }
}