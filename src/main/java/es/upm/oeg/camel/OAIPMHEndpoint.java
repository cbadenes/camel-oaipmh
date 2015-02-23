package es.upm.oeg.camel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.camel.*;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultPollingEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Represents a OAIPMH endpoint.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OAIPMHEndpoint extends DefaultPollingEndpoint {

    private final String url;

    @UriParam
    private String from = ISODateTimeFormat.dateTimeNoMillis().print(System.currentTimeMillis());
    @UriParam
    private String verb = "ListRecords";
    @UriParam
    private String metadataPrefix = "oai_dc";


    public OAIPMHEndpoint(String uri, String url, OAIPMHComponent component) {
        super(uri, component);
        this.url = url;
    }

    public Producer createProducer() throws Exception {
        return new OAIPMHProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        OAIPMHConsumer consumer = new OAIPMHConsumer(this, processor);
        validateParameters();
        configureConsumer(consumer);
        return consumer;
    }


    private void validateParameters(){
        // From parameter in ISO 8601 format
        ISODateTimeFormat.dateTimeNoMillis().parseDateTime(from);
    }

    public boolean isSingleton() {
        return true;
    }
}
