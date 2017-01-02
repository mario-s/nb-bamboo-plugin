package org.netbeans.modules.bamboo.client.glue;

/**
 * This interface contains parameters which can be added to a rest call to extend the content of entities.
 * 
 * @author spindizzy
 */
public final class ExpandParameter {
    
    public static final String EXPAND = "expand";

    public static final String PROJECT_PLANS = "projects.project.plans.plan";
    public static final String RESULT_COMMENTS = "results.result.comments";
    
    /**
     * Expands the changed files for the result.
     */
    public static final String RESULT_CHANGED_FILES = "changes.change.files";
    /**
     * Expands the JIRA issues for the result
     */
    public static final String RESULT_JIRA_ISSUES = "jiraIssues";
    
}
