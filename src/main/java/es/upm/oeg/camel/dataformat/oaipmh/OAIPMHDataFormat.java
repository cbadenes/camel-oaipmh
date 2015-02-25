package es.upm.oeg.camel.dataformat.oaipmh;

import es.upm.oeg.camel.oaipmh.message.OAIPMHtype;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.util.ExchangeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * OAI-PMH DataFormat
 * <p/>
 * This data format supports two operations:
 * <ul>
 *   <li>marshal = from OAI-PMH to XML String </li>
 *   <li>unmarshal = from XML String to OAI-PMH </li>
 * </ul>
 * <p/>
 * Uses <a href="http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">OAI-PMH.xsd</a> for parsing.
 * <p/>
 */
public class OAIPMHDataFormat implements DataFormat{


    protected static final Logger LOG = LoggerFactory.getLogger(OAIPMHDataFormat.class);

    @Override
    public void marshal(Exchange exchange, Object body, OutputStream out) throws Exception {
        OAIPMHtype oaipmh = ExchangeHelper.convertToMandatoryType(exchange, OAIPMHtype.class, body);
        String xml = OAIPMHConverter.oaipmhToXml(oaipmh);
        if (xml != null) {
            out.write(xml.getBytes());
        } else {
            LOG.debug("Cannot marshal OAI-PMH to XML.");
        }
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream in) throws Exception {
        String xml = ExchangeHelper.convertToMandatoryType(exchange, String.class, in);
        return OAIPMHConverter.xmlToOaipmh(xml);
    }
}
