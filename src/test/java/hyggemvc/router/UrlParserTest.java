package hyggemvc.router;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by adam on 21/02/2017.
 */
class UrlParserTest {
    UrlParser urlParser;

    @BeforeEach
    void setUp() {
        urlParser = new UrlParser();
    }

    @org.junit.jupiter.api.Test
    void testToCamelCase() {
        String url = "controller-name";
        Assertions.assertEquals("controllerName",urlParser.toCamelCase(url));
    }

    @Test
    void testToCamelCaseWithDashAtEnd() {
        String url = "controller-name-";
        Assertions.assertEquals("controllerName-",urlParser.toCamelCase(url));
    }

    @Test
    void testToCamelCaseWithDashAtStart() {
        String url = "-controller-name";
        Assertions.assertEquals("ControllerName",urlParser.toCamelCase(url));
    }

    @Test
    void testToCamelCaseWithFirstUpperCase() {
        String url = "controller-name";
        Assertions.assertEquals("ControllerName",urlParser.toCamelCaseWithFirstUpperCase(url));
    }
}