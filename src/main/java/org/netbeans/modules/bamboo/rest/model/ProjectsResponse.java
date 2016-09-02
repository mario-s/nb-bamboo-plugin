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
    public int getMaxResult() {
        return (projects != null) ? projects.getMaxResult() : 0;
    }

    @Override
    public int getSize() {
        return (projects != null) ? projects.getSize() : 0;
    }

}
