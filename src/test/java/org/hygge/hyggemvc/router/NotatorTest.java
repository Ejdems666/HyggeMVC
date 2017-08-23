package org.hygge.hyggemvc.router;

import org.hygge.hyggemvc.utilities.Notator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by adam on 21/02/2017.
 */
public class NotatorTest {
    Notator notator;

    @BeforeMethod
    public void setUp() {
        notator = new Notator();
    }

    @Test
    public void testToCamelCase() {
        String url = "controller-name";
        Assert.assertEquals("controllerName", notator.toCamelCase(url));
    }

    @Test
    public void testToCamelCaseWithDashAtEnd() {
        String url = "controller-name-";
        Assert.assertEquals("controllerName-", notator.toCamelCase(url));
    }

    @Test
    public void testToCamelCaseWithDashAtStart() {
        String url = "-controller-name";
        Assert.assertEquals("ControllerName", notator.toCamelCase(url));
    }

    @Test
    public void testToCamelCaseWithFirstUpperCase() {
        String url = "controller-name";
        Assert.assertEquals("ControllerName", notator.toCamelCaseWithFirstUpperCase(url));
    }
}