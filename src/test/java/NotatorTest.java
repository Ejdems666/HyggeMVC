import hyggemvc.router.Notator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by adam on 21/02/2017.
 */
class NotatorTest {
    Notator notator;

    @BeforeEach
    void setUp() {
        notator = new Notator();
    }

    @org.junit.jupiter.api.Test
    void testToCamelCase() {
        String url = "controller-name";
        Assertions.assertEquals("controllerName", notator.toCamelCase(url));
    }

    @Test
    void testToCamelCaseWithDashAtEnd() {
        String url = "controller-name-";
        Assertions.assertEquals("controllerName-", notator.toCamelCase(url));
    }

    @Test
    void testToCamelCaseWithDashAtStart() {
        String url = "-controller-name";
        Assertions.assertEquals("ControllerName", notator.toCamelCase(url));
    }

    @Test
    void testToCamelCaseWithFirstUpperCase() {
        String url = "controller-name";
        Assertions.assertEquals("ControllerName", notator.toCamelCaseWithFirstUpperCase(url));
    }
}