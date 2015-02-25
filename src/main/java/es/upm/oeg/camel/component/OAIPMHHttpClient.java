package es.upm.oeg.camel.component;

import es.upm.oeg.camel.oaipmh.message.ResumptionTokenType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * Created by cbadenes on 23/02/15.
 */
public class OAIPMHHttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(OAIPMHHttpClient.class);

    public String doRequest(OAIPMHEndpoint endpoint) throws IOException, URISyntaxException {

        return doRequest(endpoint,null);
    }

    public String doRequest(OAIPMHEndpoint endpoint, ResumptionTokenType token) throws IOException, URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

            URI baseURI = URI.create("http://"+endpoint.getUrl());

            URIBuilder builder = new URIBuilder();
            builder.setScheme(baseURI.getScheme())
                    .setHost(baseURI.getHost())
                    .setPath(baseURI.getPath())
                    .setParameter("verb", endpoint.getVerb())
                    .setParameter("metadataPrefix", endpoint.getMetadataPrefix())
                    .setParameter("from", endpoint.getFrom());

            if (token != null){
                builder.addParameter("resumptionToken", String.valueOf(token.getCursor()));
            }


            HttpGet httpget = new HttpGet(builder.build());

            LOG.info("Executing request: {} ", httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        if (entity == null) throw new IOException("No response received");
                        return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            LOG.info("Response: {}",responseBody);
            return responseBody;
        } finally {
            httpclient.close();
        }

    }

}
