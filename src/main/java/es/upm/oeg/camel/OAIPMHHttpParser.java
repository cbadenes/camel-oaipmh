package es.upm.oeg.camel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class OAIPMHHttpParser {

    private Unmarshaller unmarshaller;

    public OAIPMHHttpParser() throws JAXBException {
        this.unmarshaller = JAXBContext.newInstance("es.upm.oeg.camel.oaipmh.message").createUnmarshaller();
    }

    public <T>T unmarshall (String xml, Class<T> type) throws JAXBException, IOException {

        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        JAXBElement data = (JAXBElement) this.unmarshaller.unmarshal(stream);

        return type.cast(data.getValue());
    }

}
