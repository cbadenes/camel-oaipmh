package es.upm.oeg.camel.oaipmh.handler;

import es.upm.oeg.camel.component.OAIPMHConsumer;
import es.upm.oeg.camel.oaipmh.model.OAIPMHtype;

import javax.xml.bind.JAXBException;


public class DefaultHandler extends AbstractHandler implements ResponseHandler {


    public DefaultHandler(OAIPMHConsumer consumer) {
        super(consumer);
    }

    @Override
    public void process(OAIPMHtype message) throws JAXBException {
        // send message to camel route
        send(message);
    }
}
