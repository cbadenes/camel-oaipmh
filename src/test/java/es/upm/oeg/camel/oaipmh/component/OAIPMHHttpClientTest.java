package es.upm.oeg.camel.oaipmh.component;

import es.upm.oeg.camel.oaipmh.handler.ResponseHandler;
import es.upm.oeg.camel.oaipmh.model.ResumptionTokenType;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.net.URI;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OAIPMHHttpClientTest {

    private CloseableHttpClient httpClient;
    private OAIPMHHttpClient subject;

    @Before
    public void setup() {
        httpClient = mock(CloseableHttpClient.class);

        subject = new OAIPMHHttpClient() {
            @Override
            protected CloseableHttpClient getCloseableHttpClient() {
                return httpClient;
            }
        };
    }

    @Test
    public void Issued_HTTP_request_URL_specifies_port() throws Exception {
        ArgumentCaptor<HttpGet> argumentCaptor = ArgumentCaptor.forClass(HttpGet.class);

        URI expectedRequestUri = URI.create("http://example.org:8888/oai");
        subject.doRequest(expectedRequestUri, "", "", "", "", "", new ResumptionTokenType());

        verify(httpClient).execute(
                argumentCaptor.capture(),
                (org.apache.http.client.ResponseHandler<?>) any(ResponseHandler.class));

        String actualRequestUri = argumentCaptor.getValue().getRequestLine().getUri();
        assertTrue(actualRequestUri.startsWith(expectedRequestUri.toASCIIString()));
    }

}
