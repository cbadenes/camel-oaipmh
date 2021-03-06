package es.upm.oeg.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class InvalidContextTest extends CamelTestSupport {

    @Override
    public void setUp() throws Exception {
        // nothing to do
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFromTime() throws Exception {
        super.setUp();
        assertTrue(false);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        return new RouteBuilder() {
            public void configure() {
                from("oaipmh://aprendeenlinea.udea.edu.co/revistas/index.php/ingenieria/oai?" +
                        "from=2015-02-0114:00:00Z")
                  .to("mock:result");
            }
        };
    }
}
