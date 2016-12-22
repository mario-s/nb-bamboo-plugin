package org.netbeans.modules.bamboo.client.rest;

import java.util.Collection;
import javax.ws.rs.core.Response;
import org.netbeans.modules.bamboo.client.glue.BambooClient;
import org.netbeans.modules.bamboo.model.InstanceValues;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ProjectVo;

/**
 * Abstract parent for bamboo client which extends the interface for not public exposed methods.
 *
 * @author spindizzy
 */
abstract class AbstractBambooClient implements BambooClient {

    private final InstanceValues values;

    private final HttpUtility utility;

    AbstractBambooClient(InstanceValues values, HttpUtility utility) {
        this.values = values;
        this.utility = utility;
    }

    /**
     * This method returns <code>true</code> when the url can be reached, if not it returns <code>false</code>.
     *
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     */
    boolean existsService() {
        return utility.exists(values.getUrl());
    }

    /**
     * This method updated the given projects.It will change the content of the given projects parameter.
     *
     * @param projects projects to be updated
     */
    abstract void updateProjects(Collection<ProjectVo> projects);

    /**
     * Queues a plan for a build. The REST Api requires the key of the project and plan.
     * 
     * @param plan the plan to build.
     * @return the server's response.
     */
    abstract Response queue(PlanVo plan);

    InstanceValues getValues() {
        return values;
    }
}
