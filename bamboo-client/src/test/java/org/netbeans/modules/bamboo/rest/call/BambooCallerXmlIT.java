package org.netbeans.modules.bamboo.rest.call;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.DefaultInstanceValues;

import org.netbeans.modules.bamboo.model.rest.ResultsResponse;
import org.netbeans.modules.bamboo.rest.HttpUtility;

import static java.util.Collections.singletonMap;
import static org.junit.Assume.assumeTrue;

/**
 *
 * @author spindizzy
 */
@Log
public class BambooCallerXmlIT {

    private static final String FOO = "foo";

    private WebTargetFactory factory;

    private static Properties props;

    private final HttpUtility httpUtility;

    public BambooCallerXmlIT() {
        this.httpUtility = new HttpUtility();
    }

    @BeforeClass
    public static void prepare() {
        props = new Properties();
        try {
            InputStream input = BambooCallerXmlIT.class.getResourceAsStream("bamboo.properties");
            props.load(input);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    @Before
    public void setUp() {

        DefaultInstanceValues values = new DefaultInstanceValues();
        values.setName(FOO);
        values.setUrl(props.getProperty("url"));
        values.setUsername(props.getProperty("user"));
        values.setPassword(props.getProperty("password").toCharArray());

        factory = new WebTargetFactory(values, Level.INFO);
    }

    private boolean existsUrl() {
        return httpUtility.exists(props.getProperty("url"));
    }

    @Test
    public void testGetResults() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap("expand", "results.result.comments");
        WebTarget webTarget = factory.newTarget("result", params);
        ResultsResponse response = webTarget.request().accept(MediaType.APPLICATION_XML).get(ResultsResponse.class);
        final int size = response.getResults().getSize();
        assertThat(size, not(0));
    }

}
