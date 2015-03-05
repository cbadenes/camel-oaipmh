package es.upm.oeg.camel;


import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import es.upm.oeg.camel.oaipmh.dataformat.OAIPMHConverter;
import es.upm.oeg.camel.oaipmh.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

public class MarshallTest {

    private ObjectFactory factory;

    @Before
    public void setup() throws JAXBException {
        this.factory = new ObjectFactory();
    }


    @Test
    public void OneRecord() throws JAXBException {


        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<OAI-PMH xmlns=\"http://www.openarchives.org/OAI/2.0/\" xmlns:provenance=\"http://www.openarchives.org/OAI/2.0/provenance\" xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n" +
                "    <responseDate>2002-06-01T19:20:30Z</responseDate>\n" +
                "    <request verb=\"ListRecords\" metadataPrefix=\"oai_rfc1807\" from=\"1998-01-15\" set=\"physics:hep\">http://an.oa.org/OAI-script</request>\n" +
                "    <ListRecords>\n" +
                "        <record>\n" +
                "            <header status=\"deleted\">\n" +
                "                <identifier>oai:arXiv.org:hep-th/9901007</identifier>\n" +
                "                <datestamp>1999-12-21</datestamp>\n" +
                "            </header>\n" +
                "        </record>\n" +
                "    </ListRecords>\n" +
                "</OAI-PMH>";


        OAIPMHtype message = this.factory.createOAIPMHtype();
        XMLGregorianCalendar responseDate = new XMLGregorianCalendarImpl();
        responseDate.setYear(2002);
        responseDate.setMonth(06);
        responseDate.setDay(01);
        responseDate.setHour(19);
        responseDate.setMinute(20);
        responseDate.setSecond(30);
        responseDate.setTimezone(0);
        message.setResponseDate(responseDate);

        RequestType request = factory.createRequestType();
        request.setVerb(VerbType.LIST_RECORDS);
        request.setFrom("1998-01-15");
        request.setSet("physics:hep");
        request.setMetadataPrefix("oai_rfc1807");
        request.setValue("http://an.oa.org/OAI-script");
        message.setRequest(request);


        RecordType record = factory.createRecordType();
        HeaderType header = factory.createHeaderType();
        header.setStatus(StatusType.DELETED);
        header.setIdentifier("oai:arXiv.org:hep-th/9901007");
        header.setDatestamp("1999-12-21");
        record.setHeader(header);


        ListRecordsType listRecords = factory.createListRecordsType();
        listRecords.getRecord().add(record);
        message.setListRecords(listRecords);


        String messageXml = OAIPMHConverter.oaipmhToXml(message);

        Assert.assertEquals(xml.trim(),messageXml.trim());


    }

}
