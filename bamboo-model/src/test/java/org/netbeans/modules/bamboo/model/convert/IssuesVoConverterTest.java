package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;

import static java.util.Collections.singletonList;

import org.junit.Before;
import org.junit.Test;
import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Change;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.netbeans.modules.bamboo.model.rcp.IssueVo;

import org.netbeans.modules.bamboo.model.rest.Changes;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.JiraIssues;

/**
 *
 * @author spindizzy
 */
public class IssuesVoConverterTest {

    private static final String FOO = "foo";

    private IssuesVoConverter classUnderTest;

    private JiraIssues source;

    @Before
    public void setUp() {
        classUnderTest = new IssuesVoConverter();
        source = new JiraIssues();
    }

    @Test
    public void testConvert_WithChanges_ExpectChangesVo() {
        Issue issue = new Issue();
        source.setIssues(singletonList(issue));
        Collection<IssueVo> result = classUnderTest.convert(source);
        assertThat(result.isEmpty(), is(false));
    }
    
     @Test
    public void testConvert_NullChanges_ExpectEmpty() {
        Collection<IssueVo> result = classUnderTest.convert(null);
        assertThat(result.isEmpty(), is(true));
    }
}
