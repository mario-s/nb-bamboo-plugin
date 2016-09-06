package org.netbeans.modules.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author spindizzy
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectsResponse extends AbstractResponse<Project> {
    private Projects projects;

    @Override
    public Collection<Project> asCollection() {
        Set<Project> coll = new HashSet<>();
        if(projects != null){
            coll.addAll(projects.getProject());
        }
        return coll;
    }

    @Override
    protected Metrics getMetrics() {
        return projects;
    }
}
