package org.netbeans.modules.bamboo.model;

/**
 * This enum contains possible events fired during an update of the model.
 * 
 * @author spindizzy
 */
public enum ChangeEvents {
    
    /**
     * This value indicates that sychronizing just started or stoped.
     */
    Synchronizing("synchronizing"), 
    /**
     * This value is associated with a change of all projects.
     */
    Projects("projects"), 
    /**
     * This value is associated with a change of all plans.
     */
    Plans("plans"), 
    /**
     * This value is associated with a change of one plan.
     */
    Plan("plan"), 
    /**
     * This value is associated with a change of the result of one plan.
     */
    Result("result");

    private ChangeEvents(String value){
        this.value = value;
                
    }
    
    private final String value;

    @Override
    public String toString() {
        return value;
    }
    
}
