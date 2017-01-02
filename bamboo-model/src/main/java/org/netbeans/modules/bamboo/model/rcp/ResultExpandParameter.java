package org.netbeans.modules.bamboo.model.rcp;

/**
 * This enum contains parameters to expand the content of a build result.
 *
 * @author spindizzy
 */
public enum ResultExpandParameter {

    /**
     * Expands the changed files for the result.
     */
    Changes("changes.change.files"),
    /**
     * Expands the JIRA issues for the result
     */
    Jira("jiraIssues");

    private final String value;

    private ResultExpandParameter(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
