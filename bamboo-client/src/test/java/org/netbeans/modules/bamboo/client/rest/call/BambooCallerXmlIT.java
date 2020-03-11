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
import org.netbeans.modules.bamboo.model.rest.Change;
import org.netbeans.modules.bamboo.model.rest.Files;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.EXPAND;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.RESULT_COMMENTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULT;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULTS;

/**
 *
 * @author Mario Schroeder
 */
class BambooCallerXmlIT {

    private static final String FOO = "foo";

    private static final String URL = "url";

    private WebTargetFactory factory;

    private static Properties props;

    private final HttpUtility httpUtility;

    BambooCallerXmlIT() {
        this.httpUtility = new HttpUtility();
    }

    @BeforeAll
    static void prepare() throws IOException {
        props = new Properties();
        
        InputStream input = BambooCallerXmlIT.class.getResourceAsStream("bamboo.properties");
        props.load(input);
    }

    @BeforeEach
    void setUp() {

        DefaultInstanceValues values = new DefaultInstanceValues();
        values.setName(FOO);
        values.setUrl(props.getProperty(URL));
        values.setUsername(props.getProperty("user"));
        values.setPassword(props.getProperty("password").toCharArray());

        factory = new WebTargetFactory(values, Level.FINE);
    }

    private boolean existsUrl() {
        return httpUtility.exists(props.getProperty(URL));
    }

    private String newResultPath() {
        String key = props.getProperty("result.key");
        return String.format(RESULT, key);
    }

    @Test
    public void testGetResults_SizeGtZero() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        WebTarget webTarget = factory.create(RESULTS, params);
        ResultsResponse response = webTarget.request().accept(MediaType.APPLICATION_XML).get(ResultsResponse.class);
        final int size = response.getResults().getSize();
        assertTrue(size > 0);
    }

    @Test
    void testGetResults_ResultsNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        WebTarget webTarget = factory.create(RESULTS, params);
        ResultsResponse response = webTarget.request().accept(MediaType.APPLICATION_XML).get(ResultsResponse.class);
        Collection<Result> results = response.asCollection();
        assertFalse(results.isEmpty());
    }

    @Test
    void testGetChanges_FilesNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.Changes.toString());
        WebTarget webTarget = factory.create(newResultPath(), params);
        Result response = webTarget.request().accept(MediaType.APPLICATION_XML).get(Result.class);
        Collection<Change> changes = response.getChanges().asCollection();
        assumeFalse(changes.isEmpty());
        Files files = changes.iterator().next().getFiles();
        assertFalse(files.asCollection().isEmpty());
    }

    @Test
    void testGetChanges_ChangeSetIdNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.Changes.toString());
        WebTarget webTarget = factory.create(newResultPath(), params);
        Result response = webTarget.request().accept(MediaType.APPLICATION_XML).get(Result.class);
        Collection<Change> changes = response.getChanges().asCollection();
        assumeFalse(changes.isEmpty());
        Change first = changes.iterator().next();
        assertFalse(first.getChangesetId().isEmpty());
    }

    @Test
    void testGetJiraIssues_ResultNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.Jira.toString());
        WebTarget webTarget = factory.create(newResultPath(), params);
        Result response = webTarget.request().accept(MediaType.APPLICATION_XML).get(Result.class);
        Collection<Issue> issues = response.getJiraIssues().asCollection();
        assertFalse(issues.isEmpty());
    }

}
