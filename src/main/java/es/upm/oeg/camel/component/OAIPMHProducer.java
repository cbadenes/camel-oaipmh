package es.upm.oeg.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The OAIPMH producer.
 */
public class OAIPMHProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(OAIPMHProducer.class);
    private OAIPMHEndpoint endpoint;

    public OAIPMHProducer(OAIPMHEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());    
    }

}
