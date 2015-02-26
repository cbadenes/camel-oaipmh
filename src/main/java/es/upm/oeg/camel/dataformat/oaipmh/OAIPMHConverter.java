package es.upm.oeg.camel.dataformat.oaipmh;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import es.upm.oeg.camel.oaipmh.message.OAIPMHtype;
import es.upm.oeg.camel.oaipmh.message.ObjectFactory;
import org.apache.camel.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Converter
public final class OAIPMHConverter {

    private static final Logger LOG = LoggerFactory.getLogger(OAIPMHConverter.class);

    private static final ObjectFactory factory = new ObjectFactory();

    private OAIPMHConverter() {
    }

    @Converter
    public static String oaipmhToXml(OAIPMHtype oaipmh) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("es.upm.oeg.camel.oaipmh.message");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        NamespacePrefixMapper mapper = new NamespacePrefixMapper() {
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

                switch(namespaceUri){
                    case "http://www.openarchives.org/OAI/2.0/": return (!requirePrefix)? "" : "ns";
                    case "http://purl.org/dc/elements/1.1/": return "dc";
                    case "http://www.openarchives.org/OAI/2.0/oai_dc/": return "oai_dc";
                    case "http://www.openarchives.org/OAI/2.0/provenance": return "provenance";
                    default: return "ns";
                }
            }
        };
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
        StringWriter out = new StringWriter();

        JAXBElement<OAIPMHtype> element = factory.createOAIPMH(oaipmh);
        marshaller.marshal(element, out);
        String xml =  out.toString();
        LOG.trace("Marshalled message: {}", xml);
        return xml;
    }

    @Converter
    public static OAIPMHtype xmlToOaipmh(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("es.upm.oeg.camel.oaipmh.message");
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        JAXBElement data = (JAXBElement) context.createUnmarshaller().unmarshal(stream);
        OAIPMHtype element =  OAIPMHtype.class.cast(data.getValue());
        LOG.trace("Unmarshalled message: {}", element);
        return element;
    }

}
