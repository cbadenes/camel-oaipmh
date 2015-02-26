package es.upm.oeg.camel;

import es.upm.oeg.camel.dataformat.oaipmh.OAIPMHConverter;
import es.upm.oeg.camel.oaipmh.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.util.List;

public class UnmarshallTest {


    @Test
    public void errorHandlingWhenillegalVerbArgument() throws JAXBException, IOException {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OAI-PMH xmlns=\"http://www.openarchives.org/OAI/2.0/\" \n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/\n" +
                "         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd\">\n" +
                "  <responseDate>2002-05-01T09:18:29Z</responseDate>\n" +
                "  <request>http://arXiv.org/oai2</request>\n" +
                "  <error code=\"badVerb\">Illegal OAI verb</error>\n" +
                "</OAI-PMH>";

        OAIPMHtype message = OAIPMHConverter.xmlToOaipmh(xml);
        OAIPMHerrorType error = message.getError().get(0);
        OAIPMHerrorcodeType code = error.getCode();

        Assert.assertEquals(OAIPMHerrorcodeType.BAD_VERB,code);
        Assert.assertEquals("Illegal OAI verb",error.getValue());
    }

    @Test
    public void errorHandlingWhenListSets() throws JAXBException, IOException {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OAI-PMH xmlns=\"http://www.openarchives.org/OAI/2.0/\" \n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/\n" +
                "         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd\">\n" +
                "  <responseDate>2002-05-01T09:18:29Z</responseDate>\n" +
                "  <request verb=\"ListSets\">http://arXiv.org/oai2</request>\n" +
                "  <error code=\"noSetHierarchy\">This repository does not support sets</error>\n" +
                "</OAI-PMH>";

        OAIPMHtype message = OAIPMHConverter.xmlToOaipmh(xml);
        OAIPMHerrorType error = message.getError().get(0);
        OAIPMHerrorcodeType code = error.getCode();

        Assert.assertEquals(OAIPMHerrorcodeType.NO_SET_HIERARCHY,code);
        Assert.assertEquals("This repository does not support sets",error.getValue());
    }


    @Test
    public void listRecordsRequest() throws JAXBException, IOException {

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OAI-PMH xmlns=\"http://www.openarchives.org/OAI/2.0/\" \n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/\n" +
                "         http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd\">\n" +
                " <responseDate>2002-06-01T19:20:30Z</responseDate> \n" +
                " <request verb=\"ListRecords\" from=\"2002-05-01T14:15:00Z\"\n" +
                "          until=\"2002-05-01T14:20:00Z\" metadataPrefix=\"oai_dc\">\n" +
                "          http://www.perseus.tufts.edu/cgi-bin/pdataprov</request>\n" +
                " <ListRecords>\n" +
                "  <record>\n" +
                "    <header>\n" +
                "      <identifier>oai:perseus:Perseus:text:1999.02.0084</identifier>\n" +
                "      <datestamp>2002-05-01T14:16:12Z</datestamp>\n" +
                "      <setSpec>physics:hep</setSpec>\n" +
                "      <setSpec>math</setSpec>\n" +
                "    </header>\n" +
                "    <metadata>\n" +
                "      <oai_dc:dc \n" +
                "          xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" \n" +
                "          xmlns:dc=\"http://purl.org/dc/elements/1.1/\" \n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                "          xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ \n" +
                "          http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">\n" +
                "        <dc:title>Opera Minora</dc:title>\n" +
                "        <dc:creator>Cornelius Tacitus</dc:creator>\n" +
                "        <dc:type>text</dc:type>\n" +
                "        <dc:source>Opera Minora. Cornelius Tacitus. Henry Furneaux. \n" +
                "         Clarendon Press. Oxford. 1900.</dc:source>\n" +
                "        <dc:language>latin</dc:language>\n" +
                "        <dc:identifier>http://www.perseus.tufts.edu/cgi-bin/ptext?\n" +
                "          doc=Perseus:text:1999.02.0084</dc:identifier>\n" +
                "      </oai_dc:dc>\n" +
                "    </metadata>\n" +
                "  </record>\n" +
                "  <record>\n" +
                "    <header>\n" +
                "      <identifier>oai:perseus:Perseus:text:1999.02.0083</identifier>\n" +
                "      <datestamp>2002-05-01T14:20:55Z</datestamp>\n" +
                "    </header>\n" +
                "    <metadata>\n" +
                "      <oai_dc:dc \n" +
                "          xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" \n" +
                "          xmlns:dc=\"http://purl.org/dc/elements/1.1/\" \n" +
                "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                "          xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai_dc/ \n" +
                "          http://www.openarchives.org/OAI/2.0/oai_dc.xsd\">\n" +
                "        <dc:title>Germany and its Tribes</dc:title>\n" +
                "        <dc:creator>Tacitus</dc:creator>\n" +
                "        <dc:type>text</dc:type>\n" +
                "        <dc:source>Complete Works of Tacitus. Tacitus. Alfred John Church. \n" +
                "         William Jackson Brodribb. Lisa Cerrato. edited for Perseus. \n" +
                "         New York: Random House, Inc. Random House, Inc. reprinted 1942.\n" +
                "          </dc:source>\n" +
                "        <dc:language>english</dc:language>\n" +
                "        <dc:identifier>http://www.perseus.tufts.edu/cgi-bin/ptext?\n" +
                "         doc=Perseus:text:1999.02.0083</dc:identifier>\n" +
                "      </oai_dc:dc>\n" +
                "      </metadata>\n" +
                "    <about>\n" +
                "      <provenance\n" +
                "       xmlns=\"http://www.openarchives.org/OAI/2.0/provenance\"\n" +
                "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                "       xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/provenance  \n" +
                "       http://www.openarchives.org/OAI/2.0/provenance.xsd\">\n" +
                "       <originDescription harvestDate=\"2002-01-01T11:10:01Z\" altered=\"true\">\n" +
                "        <baseURL>http://some.oa.org</baseURL>\n" +
                "        <identifier>oai:r2.org:klik001</identifier>\n" +
                "        <datestamp>2001-01-01</datestamp>\n" +
                "        <metadataNamespace>http://www.openarchives.org/OAI/2.0/oai_dc/</metadataNamespace>\n" +
                "        </originDescription>\n" +
                "      </provenance>\n" +
                "    </about>\n" +
                "  </record>\n" +
                " </ListRecords>\n" +
                "</OAI-PMH>";

        OAIPMHtype message = OAIPMHConverter.xmlToOaipmh(xml);

        // responseDate 2002-06-01T19:20:30Z
        XMLGregorianCalendar date = message.getResponseDate();
        Assert.assertEquals(2002,date.getYear());
        Assert.assertEquals(6,date.getMonth());
        Assert.assertEquals(1,date.getDay());
        Assert.assertEquals(19,date.getHour());
        Assert.assertEquals(20,date.getMinute());
        Assert.assertEquals(30,date.getSecond());

        // request
        RequestType request = message.getRequest();
        Assert.assertEquals(VerbType.LIST_RECORDS, request.getVerb());
        Assert.assertEquals("2002-05-01T14:15:00Z", request.getFrom());
        Assert.assertEquals("2002-05-01T14:20:00Z", request.getUntil());
        Assert.assertEquals("oai_dc", request.getMetadataPrefix());

        // ListRecords
        ListRecordsType listRecords = message.getListRecords();
        Assert.assertEquals(2, listRecords.getRecord().size());


        // Record (first)
        RecordType firstRecord = listRecords.getRecord().get(0);

        // -> Header (first)
        HeaderType headerMatch = firstRecord.getHeader();
        Assert.assertEquals("oai:perseus:Perseus:text:1999.02.0084",headerMatch.getIdentifier());
        Assert.assertEquals("2002-05-01T14:16:12Z",headerMatch.getDatestamp());
        Assert.assertEquals(2,headerMatch.getSetSpec().size());
        Assert.assertEquals("physics:hep",headerMatch.getSetSpec().get(0));
        Assert.assertEquals("math",headerMatch.getSetSpec().get(1));

        // -> Metadata (first)
        MetadataType metadataMath = firstRecord.getMetadata();

        OaiDcType metadataDC = metadataMath.getDc();
        List<JAXBElement<ElementType>> dcElements = metadataDC.getTitleOrCreatorOrSubject();
        Assert.assertEquals(6,dcElements.size());

        for( JAXBElement<ElementType> element: dcElements){

            String type  = element.getName().getLocalPart();
            String value = element.getValue().getValue();
            String lang  = element.getValue().getLang();

            switch(type){
                case "title": Assert.assertEquals("Opera Minora", value);
                    break;
                case "creator": Assert.assertEquals("Cornelius Tacitus", value);
                    break;
                case "type": Assert.assertEquals("text", value);
                    break;
                case "source": Assert.assertEquals("Opera Minora. Cornelius Tacitus. Henry Furneaux. \n" +
                        "         Clarendon Press. Oxford. 1900.", value);
                    break;
                case "language": Assert.assertEquals("latin", value);
                    break;
                case "identifier": Assert.assertEquals("http://www.perseus.tufts.edu/cgi-bin/ptext?\n" +
                        "          doc=Perseus:text:1999.02.0084", value);
                    break;
            }
        }

        // Record (second)
        RecordType secondRecord = listRecords.getRecord().get(1);

        // -> Header (second)
        HeaderType secondHeader = secondRecord.getHeader();

        Assert.assertEquals("oai:perseus:Perseus:text:1999.02.0083",secondHeader.getIdentifier());
        Assert.assertEquals("2002-05-01T14:20:55Z",secondHeader.getDatestamp());

        // -> Metadata (second)
        MetadataType secondMetadata = secondRecord.getMetadata();
        OaiDcType secondDc = secondMetadata.getDc();
        List<JAXBElement<ElementType>> secondElements = secondDc.getTitleOrCreatorOrSubject();

        Assert.assertEquals(6, secondElements.size());

        for(JAXBElement<ElementType> element: secondElements) {
            String type = element.getName().getLocalPart();
            String value = element.getValue().getValue();
            String lang = element.getValue().getLang();

            switch (type) {
                case "title":
                    Assert.assertEquals("Germany and its Tribes", value);
                    break;
                case "creator":
                    Assert.assertEquals("Tacitus", value);
                    break;
                case "type":
                    Assert.assertEquals("text", value);
                    break;
                case "source":
                    Assert.assertEquals("Complete Works of Tacitus. Tacitus. Alfred John Church. \n" +
                            "         William Jackson Brodribb. Lisa Cerrato. edited for Perseus. \n" +
                            "         New York: Random House, Inc. Random House, Inc. reprinted 1942.\n" +
                            "          ", value);
                    break;
                case "language":
                    Assert.assertEquals("english", value);
                    break;
                case "identifier":
                    Assert.assertEquals("http://www.perseus.tufts.edu/cgi-bin/ptext?\n" +
                            "         doc=Perseus:text:1999.02.0083", value);
                    break;
            }

        }

        // -> About (second)
        List<AboutType> abouts = secondRecord.getAbout();
        Assert.assertEquals(1,abouts.size());

        ProvenanceType provenance = abouts.get(0).getProvenance();
        OriginDescriptionType originDescription = provenance.getOriginDescription();

        Assert.assertEquals("http://some.oa.org",originDescription.getBaseURL());
        Assert.assertEquals("oai:r2.org:klik001",originDescription.getIdentifier());
        Assert.assertEquals("2001-01-01",originDescription.getDatestamp());
        Assert.assertEquals("http://www.openarchives.org/OAI/2.0/oai_dc/", originDescription.getMetadataNamespace());

    }
}
