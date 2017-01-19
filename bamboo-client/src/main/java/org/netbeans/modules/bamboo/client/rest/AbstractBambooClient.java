package org.netbeans.modules.bamboo.client.rest;

import org.netbeans.modules.bamboo.client.glue.HttpUtility;
import java.util.Collection;
import javax.ws.rs.core.Response;
import org.netbeans.modules.bamboo.client.glue.BambooClient;
import org.netbeans.modules.bamboo.model.rcp.InstanceValues;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;
import org.netbeans.modules.bamboo.model.rcp.ResultExpandParameter;
import org.netbeans.modules.bamboo.model.rcp.ResultVo;

/**
 * Abstract parent for bamboo client which extends the interface for not public exposed methods.
 *
 * @author Mario Schroeder
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
    
    /**
     * Attaches a property to the given result.
     * @param vo the result to attach the property
     * @param expandParameter the expand paramter for the server call
     */
    abstract void attach(ResultVo vo, ResultExpandParameter expandParameter);

    InstanceValues getValues() {
        return values;
    }
}
