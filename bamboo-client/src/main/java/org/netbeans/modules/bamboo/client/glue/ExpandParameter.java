package org.netbeans.modules.bamboo.client.glue;

/**
 * This interface contains parameters which can be added to a rest call to extend the content of entities.
 * 
 * @author Mario Schroeder
 */
public final class ExpandParameter {
    
    public static final String EXPAND = "expand";

    public static final String PROJECT_PLANS = "projects.project.plans.plan";
    public static final String RESULT_COMMENTS = "results.result.comments";
    
}
