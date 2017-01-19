package org.netbeans.modules.bamboo.client.glue;

/**
 * This interface contains parameters which can be added to a rest call to extend the content of entities.
 * 
 * @author Mario Schroeder
 */
public final class ExpandParameter {
    /**expand parameter key*/
    public static final String EXPAND = "expand";

    /**expand the plans of each project*/
    public static final String PROJECT_PLANS = "projects.project.plans.plan";
    
    /**expand the comments for a build result*/
    public static final String RESULT_COMMENTS = "results.result.comments";
    
}
