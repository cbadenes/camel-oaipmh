package es.upm.oeg.camel.oaipmh.handler;

import es.upm.oeg.camel.oaipmh.model.OAIPMHtype;

import javax.xml.bind.JAXBException;

public interface ResponseHandler {

    void process(OAIPMHtype message) throws JAXBException;

}
