package es.upm.oeg.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Ignore;
import org.junit.Test;

public class RemoteTest extends CamelTestSupport {

    @Test
    public void testOAIPMH() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
//        mock.expectedMinimumMessageCount(727);
        Thread.currentThread().sleep(60000);
        assertMockEndpointsSatisfied();


    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {

        return new RouteBuilder() {
            public void configure() {
                from("oaipmh://eprints.ucm.es/cgi/oai2?" +
                        "delay=10000&" +
                        "from=2015-02-27T13:00:00Z&"+
                        "initialDelay=2000")
//                        .unmarshal().jaxb("es.upm.oeg.camel.oaipmh.model")
                        //.to("stream:out");
                        .to("mock:result");
            }
        };
    }
}
