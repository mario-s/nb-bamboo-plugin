/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.client.rest.call;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.netbeans.modules.bamboo.client.glue.HttpUtility;

import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter;
import org.netbeans.modules.bamboo.model.rest.*;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.EXPAND;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.RESULT_COMMENTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULT;

/**
 *
 * @author Mario Schroeder
 */
class BambooCallerXmlIT {

    private static final String FOO = "foo";

    private static final String URL = "url";
    
    private static final String PROPERTIES = "bamboo.properties";

    private WebTargetFactory factory;

    private static Properties props;
    
    private static boolean existsUrl;

    @BeforeAll
    static void prepare() throws IOException {
        InputStream input = BambooCallerXmlIT.class.getResourceAsStream(PROPERTIES);
        props = new Properties();
        props.load(input);
        
        existsUrl = new HttpUtility().exists(props.getProperty(URL));
    }

    @BeforeEach
    void setUp() {
        assumeTrue(existsUrl);

        DefaultInstanceValues values = new DefaultInstanceValues();
        values.setName(FOO);
        values.setUrl(props.getProperty(URL));
        values.setToken(props.getProperty("token").toCharArray());
        values.setUsername(props.getProperty("user"));
        values.setPassword(props.getProperty("password").toCharArray());

        factory = new WebTargetFactory(values, Level.FINE);
    }

    @Test
    public void testGetResults_SizeGtZero() {
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        ResultsResponse response = request(ResultsResponse.class, params);
        final int size = response.getResults().getSize();
        assumeTrue(size > 0);
    }

    @Test
    void testGetResults_ResultsNotEmpty() {
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        ResultsResponse response = request(ResultsResponse.class, params);
        Collection<Result> results = response.asCollection();
        assumeFalse(results.isEmpty());
    }

    @Test
    void testGetChanges_FilesNotEmpty() {
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.CHANGES.toString());
        ResultsResponse response = request(ResultsResponse.class, params);
        assumeFalse(response.getResults().getResult().isEmpty());
        Collection<Change> changes = response.getResults().getResult().get(0).getChanges().asCollection();
        assumeFalse(changes.isEmpty());
        Files files = changes.iterator().next().getFiles();
        assertFalse(files.asCollection().isEmpty());
    }

    @Test
    void testGetChanges_ChangeSetIdNotEmpty() {
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.CHANGES.toString());
        ResultsResponse response = request(ResultsResponse.class, params);
        assumeFalse(response.getResults().getResult().isEmpty());
        Collection<Change> changes = response.getResults().getResult().get(0).getChanges().asCollection();
        assumeFalse(changes.isEmpty());
        Change first = changes.iterator().next();
        assertFalse(first.getChangesetId().isEmpty());
    }

    @Test
    void testGetJiraIssues_ResultNotEmpty() {
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.JIRA.toString());
        ResultsResponse response = request(ResultsResponse.class, params);
        assumeFalse(response.getResults().getResult().isEmpty());
        Result result = response.getResults().getResult().get(0);
        Collection<Issue> issues = result.getJiraIssues().asCollection();
        assertFalse(issues.isEmpty());
    }
    
    private <T> T request(Class<T> clazz, Map<String, String> params) {
        WebTarget webTarget = factory.create(newResultPath(), params);
        
        return webTarget.request().accept(MediaType.APPLICATION_XML).get(clazz);
    }
    
    private String newResultPath() {
        String key = props.getProperty("result.key");
        return String.format(RESULT, key);
    }

}
