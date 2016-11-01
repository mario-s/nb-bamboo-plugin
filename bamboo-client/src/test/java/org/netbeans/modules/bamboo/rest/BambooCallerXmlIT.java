package org.netbeans.modules.bamboo.rest;

import java.util.logging.Level;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.glassfish.jersey.logging.LoggingFeature;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.netbeans.modules.bamboo.model.rest.Results;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

/**
 *
 * @author spindizzy
 */
@Log
public class BambooCallerXmlIT {

    private Client client;
    private static final String BASE_URI = "http://bamboo:8085/rest/api/latest";
    static final String AUTH_TYPE = "os_authType";
    static final String BASIC = "basic";
    static final String USER = "os_username";
    static final String PASS = "os_password";

    @Before
    public void setUp() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        client = client.register(new LoggingFeature(log, Level.INFO, null, null));
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    @Ignore
    public void testGetResults() throws ClientErrorException {
        WebTarget webTarget = client.target(BASE_URI).path("result").queryParam(AUTH_TYPE, BASIC).queryParam(USER, "schroeder").queryParam(
                PASS,
                "schroeder");
        ResultsResponse response = webTarget.queryParam("expand", "results.result.comments").request().accept(MediaType.APPLICATION_XML).get(ResultsResponse.class);
        final int size = response.getResults().getSize();
        assertThat(size, not(0));
    }

}
