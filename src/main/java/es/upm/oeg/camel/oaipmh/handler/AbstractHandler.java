package es.upm.oeg.camel.oaipmh.handler;


import es.upm.oeg.camel.component.OAIPMHConsumer;
import es.upm.oeg.camel.dataformat.oaipmh.OAIPMHConverter;
import es.upm.oeg.camel.oaipmh.message.OAIPMHtype;
import es.upm.oeg.camel.oaipmh.message.ObjectFactory;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;

public abstract class AbstractHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractHandler.class);

    private final OAIPMHConsumer consumer;
    protected final Endpoint endpoint;
    protected final Processor processor;
    protected final ExceptionHandler exceptionHandler;
    protected final ObjectFactory factory;

    public AbstractHandler(OAIPMHConsumer consumer){
        this.consumer = consumer;
        this.endpoint = consumer.getEndpoint();
        this.processor = consumer.getAsyncProcessor();//Asynchronous processing
        this.exceptionHandler = consumer.getExceptionHandler();
        this.factory = new ObjectFactory();
    }

    protected void send(OAIPMHtype message) throws JAXBException {
        Exchange exchange = endpoint.createExchange();
        String xml = OAIPMHConverter.oaipmhToXml(message);

        // create a message body
        exchange.getIn().setBody(xml);

        try {
            // send message to next processor in the route
            LOG.info("sending exchange: {}", exchange);
            processor.process(exchange);
        }catch (Exception e){
            throw new RuntimeCamelException("Error sending exchange: "+exchange, e);
        } finally {
            // log exception if an exception occurred and was not handled
            if (exchange.getException() != null) {
                exceptionHandler.handleException("Error processing exchange", exchange, exchange.getException());
            }
        }
    }

}
