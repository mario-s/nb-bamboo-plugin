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
import lombok.extern.java.Log;

import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.netbeans.modules.bamboo.client.glue.HttpUtility;

import org.netbeans.modules.bamboo.model.rcp.DefaultInstanceValues;
import org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter;
import org.netbeans.modules.bamboo.model.rest.Change;
import org.netbeans.modules.bamboo.model.rest.Files;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Result;
import org.netbeans.modules.bamboo.model.rest.ResultsResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeTrue;

import static java.util.Collections.singletonMap;
import static org.junit.Assume.assumeFalse;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.EXPAND;
import static org.netbeans.modules.bamboo.client.glue.ExpandParameter.RESULT_COMMENTS;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULT;
import static org.netbeans.modules.bamboo.client.glue.RestResources.RESULTS;


/**
 *
 * @author Mario Schroeder
 */
@Log
public class BambooCallerXmlIT {

    private static final String FOO = "foo";

    private static final String URL = "url";

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
        values.setUrl(props.getProperty(URL));
        values.setUsername(props.getProperty("user"));
        values.setPassword(props.getProperty("password").toCharArray());

        factory = new WebTargetFactory(values, Level.FINE);
    }

    private boolean existsUrl() {
        return httpUtility.exists(props.getProperty(URL));
    }
    
    private  String newResultPath() {
        String key = props.getProperty("result.key");
        return String.format(RESULT, key);
    }

    @Test
    public void testGetResults_SizeGtZero() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        WebTarget webTarget = factory.newTarget(RESULTS, params);
        ResultsResponse response = webTarget.request().accept(MediaType.APPLICATION_XML).get(ResultsResponse.class);
        final int size = response.getResults().getSize();
        assertThat(size, not(0));
    }

    @Test
    public void testGetResults_ResultsNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, RESULT_COMMENTS);
        WebTarget webTarget = factory.newTarget(RESULTS, params);
        ResultsResponse response = webTarget.request().accept(MediaType.APPLICATION_XML).get(ResultsResponse.class);
        Collection<Result> results = response.asCollection();
        assertThat(results.isEmpty(), is(false));
    }

    @Test
    public void testGetChanges_FilesNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.Changes.toString());
        WebTarget webTarget = factory.newTarget(newResultPath(), params);
        Result response = webTarget.request().accept(MediaType.APPLICATION_XML).get(Result.class);
        Collection<Change> changes = response.getChanges().asCollection();
        assumeFalse(changes.isEmpty());
        Files files = changes.iterator().next().getFiles();
        assertThat(files.asCollection().isEmpty(), is(false));
    }

    @Test
    public void testGetChanges_ChangeSetIdNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.Changes.toString());
        WebTarget webTarget = factory.newTarget(newResultPath(), params);
        Result response = webTarget.request().accept(MediaType.APPLICATION_XML).get(Result.class);
        Collection<Change> changes = response.getChanges().asCollection();
        assumeFalse(changes.isEmpty());
        Change first = changes.iterator().next();
        assertThat(first.getChangesetId().isEmpty(), is(false));
    }

    @Test
    public void testGetJiraIssues_ResultNotEmpty() {
        assumeTrue(existsUrl());
        Map<String, String> params = singletonMap(EXPAND, ResultExpandParameter.Jira.toString());
        WebTarget webTarget = factory.newTarget(newResultPath(), params);
        Result response = webTarget.request().accept(MediaType.APPLICATION_XML).get(Result.class);
        Collection<Issue> issues = response.getJiraIssues().asCollection();
        assertThat(issues.isEmpty(), is(false));
    }

}
