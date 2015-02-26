package es.upm.oeg.camel.oaipmh.handler;

import es.upm.oeg.camel.component.OAIPMHConsumer;
import es.upm.oeg.camel.oaipmh.model.ListRecordsType;
import es.upm.oeg.camel.oaipmh.model.OAIPMHtype;
import es.upm.oeg.camel.oaipmh.model.RecordType;

import javax.xml.bind.JAXBException;

public class ListRecordsHandler extends AbstractHandler implements ResponseHandler {


    public ListRecordsHandler(OAIPMHConsumer consumer) {
        super(consumer);
    }

    @Override
    public void process(OAIPMHtype listRecordsMessage) throws JAXBException {
        // split message for each record
        for(RecordType record: listRecordsMessage.getListRecords().getRecord()){

            // create a new message for each record
            OAIPMHtype message = factory.createOAIPMHtype();

            // use date and request of original message
            message.setResponseDate(listRecordsMessage.getResponseDate());
            message.setRequest(listRecordsMessage.getRequest());

            // create a new list of records with only this record
            ListRecordsType listRecords = factory.createListRecordsType();
            listRecords.getRecord().add(record);
            message.setListRecords(listRecords);

            // send message to next processor in the route
            send(message);
        }
    }
}
