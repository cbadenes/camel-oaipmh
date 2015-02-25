package es.upm.oeg.camel.dataformat.oaipmh;

import es.upm.oeg.camel.oaipmh.message.OAIPMHtype;
import org.apache.camel.Converter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@Converter
public final class OAIPMHConverter {

    private OAIPMHConverter() {
    }

    @Converter
    public static String oaipmhToXml(OAIPMHtype oaipmh) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("es.upm.oeg.camel.oaipmh.message");
        StringWriter out = new StringWriter();
        context.createMarshaller().marshal(oaipmh,out);
        return out.toString();
    }

    @Converter
    public static OAIPMHtype xmlToOaipmh(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("es.upm.oeg.camel.oaipmh.message");
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        JAXBElement data = (JAXBElement) context.createUnmarshaller().unmarshal(stream);
        return OAIPMHtype.class.cast(data.getValue());
    }

}
