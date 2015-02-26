package es.upm.oeg.camel.oaipmh.handler;


import es.upm.oeg.camel.component.OAIPMHConsumer;

public class ResponseHandlerFactory {


    public static ResponseHandler newInstance(OAIPMHConsumer consumer, String verb){
        switch (verb){
            case "ListRecords":
                return new ListRecordsHandler(consumer);
            default:
                return new DefaultHandler(consumer);
        }
    }

}
