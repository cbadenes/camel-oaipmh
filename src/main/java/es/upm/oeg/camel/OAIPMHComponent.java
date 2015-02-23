package es.upm.oeg.camel;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the component that manages {@link OAIPMHEndpoint}.
 */
public class OAIPMHComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new OAIPMHEndpoint(uri, remaining, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }

}
