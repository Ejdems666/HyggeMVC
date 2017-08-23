package org.hygge.hyggemvc.utilities.parameter;

import org.hygge.hyggemvc.utilities.parameter.exception.ParameterParserException;
import org.hygge.hyggemvc.utilities.parameter.exception.RequiredParameterNonExistentException;
import org.hygge.hyggemvc.utilities.parameter.type.ParameterMask;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;

/**
 * Created by adam on 19/04/2017.
 */
public class ParameterFilterTest {
    private ParameterFilter parameterFilter;
    private ParsedParameters parsedParameters;
    HttpServletRequest request;

    @BeforeMethod
    public void setUp() throws Exception {
        parameterFilter = new ParameterFilter();
        parsedParameters = null;
        request = createMock(HttpServletRequest.class);
    }

    private void parseParameters() {
        replay(request);
        try {
            parsedParameters = parameterFilter.parseParameters(request);
        } catch (ParameterParserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStringParameter() {
        parameterFilter.addString("key");
        expect(request.getParameter("key")).andReturn("value");
        parseParameters();
        Assert.assertEquals(parsedParameters.getString("key"), "value");
    }

    @Test
    public void testIntegerParameter() {
        parameterFilter.addInteger("key");
        expect(request.getParameter("key")).andReturn("1");
        parseParameters();
        Assert.assertEquals(parsedParameters.getInteger("key"), Integer.valueOf(1));
    }

    @Test
    public void testBooleanParameter() {
        parameterFilter.addBoolean("key");
        expect(request.getParameter("key")).andReturn("1");
        parseParameters();
        Assert.assertTrue(parsedParameters.getBoolean("key"));
    }

    @Test(expectedExceptions = RequiredParameterNonExistentException.class)
    public void testRequiredNonExistentParameter() throws ParameterParserException {
        parameterFilter.addString("key").setRequired();
        parsedParameters = parameterFilter.parseParameters(request);
    }

    @Test(expectedExceptions = RequiredParameterNonExistentException.class)
    public void testRequiredEmptyParameter() throws ParameterParserException {
        parameterFilter.addString("key").setRequired();
        expect(request.getParameter("key")).andReturn("");
        parsedParameters = parameterFilter.parseParameters(request);
    }

    @Test
    public void testCancelRequire() {
        ParameterMask mask = parameterFilter.addString("key");
        mask.setRequired();
        mask.setRequired(false);
        expect(request.getParameter("key")).andReturn("");
        parseParameters();
        Assert.assertEquals(parsedParameters.getString("key"), null);
    }

    @Test
    public void testDefaultValue() {
        parameterFilter.addString("key").setRequired().setDefaultValue("default");
        expect(request.getParameter("key")).andReturn(null);
        parseParameters();
        Assert.assertEquals(parsedParameters.getString("key"), "default");
    }
}
