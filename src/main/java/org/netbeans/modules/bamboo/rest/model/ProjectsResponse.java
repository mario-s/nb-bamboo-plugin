package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
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
        Collection<Project> coll = new ArrayList<>();
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
