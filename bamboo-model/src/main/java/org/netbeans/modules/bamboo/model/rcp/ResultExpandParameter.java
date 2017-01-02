package org.netbeans.modules.bamboo.model.rcp;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * This enum contains parameters to expand the conten of a build result.
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

    public static Optional<ResultExpandParameter> getByValue(String val) {
        for (ResultExpandParameter param : ResultExpandParameter.values()) {
            if (param.toString().equals(val)) {
                return of(param);
            }
        }
        return empty();
    }

}
